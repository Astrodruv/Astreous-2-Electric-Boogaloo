package engine.kdtree;

import objects.GameObject;

public class Node<T extends GameObject> {
    public T l;
    public Node<T> left, right;

    public Node (T l) {
        this.l = l;
    }
}