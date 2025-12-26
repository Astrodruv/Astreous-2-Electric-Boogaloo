package engine.kdtree;

import components.Component;
import objects.GameObject;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;

import java.util.ArrayList;

public class KDTree<T extends GameObject> {
    private Node<T> root;

    public KDTree() {
        root = null;
    }

    private class TreePoint extends GameObject {
        public TreePoint(float x, float y) {
            super(x, y);
        }
    }

    private TreePoint getTreePoint(Point p) {
        return new TreePoint(p.getX(), p.getY());
    }

    public void insert(T l) {
        root = insert(root, l, true);
    }

    private Node<T> insert(Node<T> n, T l, boolean evenLevel) {
        if (n == null) {
            return new Node<T>(l);
        }

        float comparison = compareLocations(n, l, evenLevel);

        if (comparison < 0) {
            n.left = insert(n.left, l, !evenLevel);
        } else if (comparison > 0 || !n.l.equals(l)) {
            n.right = insert(n.right, l, !evenLevel);
        }

        return n;
    }

    public T nearest(GameObject l) {
        if (root == null) return null;
        return nearest(root, l, root.l, true);
    }

    public T nearest(Point p) {
        if (root == null) return null;
        return nearest(root, getTreePoint(p), root.l, true);
    }

    private T nearest(Node<T> n, GameObject l, T champion, boolean evenLevel) {
        if (n == null) return champion;

        if (getDistanceSquared(n.l, l) < getDistanceSquared(champion, l) && n.l != l) {
            champion = n.l;
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            champion = nearest(n.left, l, champion, !evenLevel);

            if (getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearest(n.right, l, champion, !evenLevel);
            }
        } else {
            champion = nearest(n.right, l, champion, !evenLevel);

            if (getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearest(n.left, l, champion, !evenLevel);
            }
        }

        return champion;
    }

    public Unit nearestIncludeClass(GameObject l, Class<? extends Unit> clazz) {
        if (root == null) return null;
        Unit u = nearestIncludeClass(root, l, (Unit) root.l, true, clazz);
        return clazz.isInstance(u) ? u : null;
    }

    public Unit nearestIncludeClass(Point p, Class<? extends Unit> clazz) {
        if (root == null) return null;
        Unit u = nearestIncludeClass(root, getTreePoint(p), (Unit) root.l, true, clazz);
        return clazz.isInstance(u) ? u : null;
    }

    private Unit nearestIncludeClass(Node<T> n, GameObject l, Unit champion, boolean evenLevel, Class<? extends Unit> clazz) {
        if (n == null) return champion;

        if ((!clazz.isInstance(champion) && n.l != l) || (getDistanceSquared(n.l, l) < getDistanceSquared(champion, l) && n.l != l && clazz.isInstance(n.l))) {
            champion = (Unit) n.l;
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            champion = nearestIncludeClass(n.left, l, champion, !evenLevel, clazz);

            if (!clazz.isInstance(champion) || getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearestIncludeClass(n.right, l, champion, !evenLevel, clazz);
            }
        } else {
            champion = nearestIncludeClass(n.right, l, champion, !evenLevel, clazz);

            if (!clazz.isInstance(champion) || getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearestIncludeClass(n.left, l, champion, !evenLevel, clazz);
            }
        }

        return champion;
    }

    public Unit nearestExcludeClass(GameObject l, Class<? extends Unit> clazz) {
        if (root == null) return null;
        Unit u = nearestExcludeClass(root, l, (Unit) root.l, true, clazz);
        return !clazz.isInstance(u) ? u : null;
    }

    public Unit nearestExcludeClass(Point p, Class<? extends Unit> clazz) {
        if (root == null) return null;
        Unit u = nearestExcludeClass(root, getTreePoint(p), (Unit) root.l, true, clazz);
        return !clazz.isInstance(u) ? u : null;
    }

    private Unit nearestExcludeClass(Node<T> n, GameObject l, Unit champion, boolean evenLevel, Class<? extends Unit> clazz) {
        if (n == null) return champion;

        if (clazz.isInstance(champion) || (getDistanceSquared(n.l, l) < getDistanceSquared(champion, l) && n.l != l /*&& n.l instanceof Unit*/ && !clazz.isInstance(n.l))) {
            champion = (Unit) n.l;
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            champion = nearestExcludeClass(n.left, l, champion, !evenLevel, clazz);

            if (clazz.isInstance(champion) || getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearestExcludeClass(n.right, l, champion, !evenLevel, clazz);
            }
        } else {
            champion = nearestExcludeClass(n.right, l, champion, !evenLevel, clazz);

            if (clazz.isInstance(champion) || getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearestExcludeClass(n.left, l, champion, !evenLevel, clazz);
            }
        }

        return champion;
    }

    public Unit nearestWithComponent(GameObject l, Class<? extends Component> clazz) {
        if (root == null) return null;
        Unit u = nearestWithComponent(root, l, (Unit) root.l, true, clazz);
        return u.hasComponent(clazz) ? u : null;
    }

    public Unit nearestWithComponent(Point p, Class<? extends Component> clazz) {
        if (root == null) return null;
        Unit u = nearestWithComponent(root, getTreePoint(p), (Unit) root.l, true, clazz);
        return u.hasComponent(clazz) ? u : null;
    }

    private Unit nearestWithComponent(Node<T> n, GameObject l, Unit champion, boolean evenLevel, Class<? extends Component> clazz) {
        if (n == null) return champion;

        if (!champion.hasComponent(clazz) || (getDistanceSquared(n.l, l) < getDistanceSquared(champion, l) && n.l != l && n.l instanceof Unit && ((Unit) n.l).hasComponent(clazz))) {
            champion = (Unit) n.l;
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            champion = nearestWithComponent(n.left, l, champion, !evenLevel, clazz);

            if (!champion.hasComponent(clazz) || getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearestWithComponent(n.right, l, champion, !evenLevel, clazz);
            }
        } else {
            champion = nearestWithComponent(n.right, l, champion, !evenLevel, clazz);

            if (!champion.hasComponent(clazz) || getDistanceSquared(champion, l) >= distanceToPartition * distanceToPartition) {
                champion = nearestWithComponent(n.left, l, champion, !evenLevel, clazz);
            }
        }

        return champion;
    }

    public ArrayList<T> range(GameObject l, int range) {
        if (root == null) return new ArrayList<T>();
        return range(root, l, new ArrayList<T>(), true, range*range);
    }

    public ArrayList<T> range(Point p, int range) {
        if (root == null) return new ArrayList<T>();
        return range(root, getTreePoint(p), new ArrayList<T>(), true, range*range);
    }

    private ArrayList<T> range(Node<T> n, GameObject l, ArrayList<T> list, boolean evenLevel, int range) {
        if (n == null) return list;

        if (getDistanceSquared(n.l, l) < range && n.l != l) {
            list.add(n.l);
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            list = range(n.left, l, list, !evenLevel, range);

            if (range >= distanceToPartition * distanceToPartition) {
                list = range(n.right, l, list, !evenLevel, range);
            }
        } else {
            list = range(n.right, l, list, !evenLevel, range);

            if (range >= distanceToPartition * distanceToPartition) {
                list = range(n.left, l, list, !evenLevel, range);
            }
        }

        return list;
    }

    public ArrayList<T> rangeIncludeClass(GameObject l, int range, Class<? extends Unit> clazz) {
        if (root == null) return new ArrayList<T>();
        return rangeIncludeClass(root, l, new ArrayList<T>(), true, range*range, clazz);
    }

    public ArrayList<T> rangeIncludeClass(Point p, int range, Class<? extends Unit> clazz) {
        if (root == null) return new ArrayList<T>();
        return rangeIncludeClass(root, getTreePoint(p), new ArrayList<T>(), true, range*range, clazz);
    }

    private ArrayList<T> rangeIncludeClass(Node<T> n, GameObject l, ArrayList<T> list, boolean evenLevel, int range, Class<? extends Unit> clazz) {
        if (n == null) return list;

        if (getDistanceSquared(n.l, l) < range && n.l != l && clazz.isInstance(n.l)) {
            list.add(n.l);
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            list = rangeIncludeClass(n.left, l, list, !evenLevel, range, clazz);

            if (range >= distanceToPartition * distanceToPartition) {
                list = rangeIncludeClass(n.right, l, list, !evenLevel, range, clazz);
            }
        } else {
            list = rangeIncludeClass(n.right, l, list, !evenLevel, range, clazz);

            if (range >= distanceToPartition * distanceToPartition) {
                list = rangeIncludeClass(n.left, l, list, !evenLevel, range, clazz);
            }
        }

        return list;
    }

    public ArrayList<T> rangeExcludeClass(GameObject l, int range, Class<? extends Unit> clazz) {
        if (root == null) return new ArrayList<T>();
        return rangeExcludeClass(root, l, new ArrayList<T>(), true, range*range, clazz);
    }

    public ArrayList<T> rangeExcludeClass(Point p, int range, Class<? extends Unit> clazz) {
        if (root == null) return new ArrayList<T>();
        return rangeExcludeClass(root, getTreePoint(p), new ArrayList<T>(), true, range*range, clazz);
    }

    private ArrayList<T> rangeExcludeClass(Node<T> n, GameObject l, ArrayList<T> list, boolean evenLevel, int range, Class<? extends Unit> clazz) {
        if (n == null) return list;

        if (getDistanceSquared(n.l, l) < range && n.l != l && n.l instanceof Unit && !clazz.isInstance(n.l)) {
            list.add(n.l);
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            list = rangeExcludeClass(n.left, l, list, !evenLevel, range, clazz);

            if (range >= distanceToPartition * distanceToPartition) {
                list = rangeExcludeClass(n.right, l, list, !evenLevel, range, clazz);
            }
        } else {
            list = rangeExcludeClass(n.right, l, list, !evenLevel, range, clazz);

            if (range >= distanceToPartition * distanceToPartition) {
                list = rangeExcludeClass(n.left, l, list, !evenLevel, range, clazz);
            }
        }

        return list;
    }

    public ArrayList<T> rangeWithComponent(GameObject l, int range, Class<? extends Component> clazz) {
        if (root == null) return new ArrayList<T>();
        return rangeWithComponent(root, l, new ArrayList<T>(), true, range*range, clazz);
    }

    public ArrayList<T> rangeWithComponent(Point p, int range, Class<? extends Component> clazz) {
        if (root == null) return new ArrayList<T>();
        return rangeWithComponent(root, getTreePoint(p), new ArrayList<T>(), true, range*range, clazz);
    }

    private ArrayList<T> rangeWithComponent(Node<T> n, GameObject l, ArrayList<T> list, boolean evenLevel, int range, Class<? extends Component> clazz) {
        if (n == null) return list;

        if (getDistanceSquared(n.l, l) < range && n.l != l && n.l instanceof Unit && ((Unit) n.l).hasComponent(clazz)) {
            list.add(n.l);
        }

        double distanceToPartition = compareLocations(n, l, evenLevel);

        if (distanceToPartition < 0) {
            list = rangeWithComponent(n.left, l, list, !evenLevel, range, clazz);

            if (range >= distanceToPartition * distanceToPartition) {
                list = rangeWithComponent(n.right, l, list, !evenLevel, range, clazz);
            }
        } else {
            list = rangeWithComponent(n.right, l, list, !evenLevel, range, clazz);

            if (range >= distanceToPartition * distanceToPartition) {
                list = rangeWithComponent(n.left, l, list, !evenLevel, range, clazz);
            }
        }

        return list;
    }

    private float compareLocations(Node<T> n, GameObject l, boolean evenLevel) {
        if (evenLevel) {
            return l.getCenterX() - n.l.getCenterX();
        } else {
            return l.getCenterY() - n.l.getCenterY();
        }
    }

    private double getDistanceSquared(GameObject a, GameObject b) {
        return Math.pow(a.getCenterX() - b.getCenterX(), 2) + Math.pow(a.getCenterY() - b.getCenterY(), 2);
    }
}
