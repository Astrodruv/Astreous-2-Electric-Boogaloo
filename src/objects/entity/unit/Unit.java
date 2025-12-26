package objects.entity.unit;

import animations.AnimationManager;
import animations.effects.Afterimage;
import animations.effects.Boom;
import animations.effects.Smoke;
import components.Component;
import components.ComponentSystem;
import components.DamageType;
import components.mod.Mod;
import components.weapon.Weapon;
import components.weapon.WeaponType;
import components.weapon.economy.Collector;
import conditions.buffs.Fast;
import conditions.buffs.Fortified;
import conditions.buffs.Glory;
import conditions.buffs.Revelry;
import conditions.debuffs.Stun;
import engine.Settings;
import engine.Utility;
import engine.Values;
import engine.states.Game;
import engine.kdtree.OptimizationToggler;
import objects.entity.Entity;
import objects.entity.missile.MissileEntity;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.resource.Minerals;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import objects.resource.Scrap;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import player.Player;
import ui.display.Images;
import ui.display.healthbar.Healthbar;
import ui.display.healthbar.UnitHealthbar;
import ui.sound.Sounds;

import java.util.ArrayList;

public abstract class Unit extends Entity implements Values
{

    /***************************** DATA ***************************************/

    // Public Constants
    public final static int BASE_SPIN_RATE = 5;
    public final static int SPAWN_BORDER = 60;
    public final static float RESOURCE_DROP_CHANCE = .1f;
    public static final int TRANSFER_RANGE = 150;
    public final static float BASE_MIN_RANGE_MULTIPLIER = .1f;

    public final static float BASIC_SPEED = 100 * Values.SPEED;
    public final static float BASIC_ACCELERATION = 100 * Values.ACC;

    protected ArrayList<MissileEntity> missilesLocked;

    protected Image imageSecondary;
    protected Image imageAccent;
    protected Image imageMove;

    // Private Data
    private int cost;
    private boolean drawFlash;
    private boolean drawFlashInvuln;
    private final ComponentSystem components;
    private int minerals;
    private int scrap;
    private float capacity;
    private Frame frame;
    private Model model;
    private final Healthbar healthbar;

    private float useMultiplier = 1;
    private float cooldownMultiplier = 1;
    private float maxRangeBonus = 0;
    private float accuracyBonus = 0;

    private float minRangeMultiplier = 0.1f;
    private int mass;
    private boolean modelApplied;

    public int pullTimer = 0;
    public int pushTimer = 0;

    private Color revelryColor;


    public Unit()
    {
        super(0, 0);
        components = new ComponentSystem(this);
        healthbar = new UnitHealthbar(this);
        model = Model.CRUISER;
        missilesLocked = new ArrayList<>();
    }

    public Unit(Player p)
    {
        this();
        setPlayer(p);
    }

    public void applyDifficultyToHealth()
    {
        if (Settings.difficultyAffectsHealth)
        {
            float diffMod = getPlayer().getDifficultyRating() - 1;
            int shieldBonus = Math.round((getMaxShield() * diffMod));
            int platingBonus = Math.round((getMaxPlating() * diffMod));
            int structureBonus = Math.round((getMaxStructure() * diffMod));

            increaseMaxShield(shieldBonus);
            increaseMaxPlating(platingBonus);
            increaseMaxStructure(structureBonus);


        }
    }

    public void setPlayer(Player p)
    {
        super.setPlayer(p);
        Point pos = getSpawn(p);
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public void addMissileLock(MissileEntity m)
    {
        missilesLocked.add(m);
    }

    public void setStyle(Style style)
    {
        int id = style.getID();

        if (getFrame() == Frame.LIGHT)
        {
            sheet = Images.light[id];
        }
        if (getFrame() == Frame.MEDIUM)
        {
            sheet = Images.medium[id];
        }
        if (getFrame() == Frame.HEAVY)
        {
            sheet = Images.heavy[id];
        }
        if (getFrame() == Frame.ASSAULT)
        {
            sheet = Images.assault[id];
        }
        setImage();
    }

    // Abstract Methods

    abstract public void action();

    abstract public void design();

    abstract public void draw(Graphics g);

    /***************************** ACCESSOR METHODS ***************************************/

    public final int getValue()
    {
        return cost;
    }

    public final Frame getFrame()
    {
        return frame;
    }

    public final Model getModel()
    {
        return model;
    }

    public final Weapon getWeaponOne()
    {
        return components.getWeaponOne();
    }

    public final Weapon getWeaponTwo()
    {
        return components.getWeaponTwo();
    }

    public final Mod getMod()
    {
        return components.getMod();
    }

    public final boolean hasWeaponOne()
    {
        return components.hasWeaponOne();
    }

    public final boolean hasWeaponTwo()
    {
        return components.hasWeaponTwo();
    }

    public final boolean hasMod()
    {
        return components.hasMod();
    }

    public final int getCargo()
    {
        return minerals + scrap;
    }

    public final int getMinerals()
    {
        return minerals;
    }

    public final int getScrap()
    {
        return scrap;
    }

    public final int getMass()
    {
        return mass;
    }

    public final int getCapacity()
    {
        return Math.round(capacity);
    }

    public final int getOpenCapacity()
    {
        return getCapacity() - getCargo();
    }

    public final boolean hasCapacity()
    {
        return getCargo() < getCapacity();
    }

    public final boolean isFull()
    {
        return getCargo() == getCapacity();
    }

    public final boolean isEmpty()
    {
        return getCargo() == 0;
    }

    public final float getCargoPercent()
    {
        return (float) getCargo() / (float) getCapacity();
    }

    public final float getStructureSlowPercent()
    {
        return (getPercentStructure() / 2 + .5f);
    }

    // Actions and Movement

    public final boolean canMove()
    {
        return super.canMove() && !isUseLocked();
    }

    public final boolean isUseLocked()
    {
        return (hasWeaponOne() && getWeaponOne().inUse()) || (hasWeaponTwo() && getWeaponTwo().inUse());
    }

    public final float getRotation()
    {
        return theta;
    }

    // Cosmetic
    public final Color getColorPrimary()
    {
        return getPlayer().getColorPrimary();
    }


    public Color getColorSecondary()
    {
        return getPlayer().getColorSecondary();
    }

    public Color getColorAccent()
    {
        return getPlayer().getColorAccent();
    }

    public final Image getImageSecondary()
    {
        return imageSecondary;
    }

    public final Image getImageAccent()
    {
        return imageAccent;
    }

    public final Image getImageMove()
    {
        return imageMove;
    }

    public final boolean hasComponentSlotOpen()
    {
        return getComponentSlotsOpen() >= 1;
    }

    public final boolean hasComponentSlotsOpen(int i)
    {
        return getComponentSlotsOpen() >= i;
    }

    public final boolean canAddComponent(Component c)
    {
        return components.canAdd(c);
    }

    public final int getComponentSlotsUsed()
    {
        return components.getComponentSlotsUsed();
    }

    public final int getComponentSlotsOpen()
    {
        return components.getComponentSlotsOpen();
    }

    public final float getCooldownTimeMultiplier()
    {
        return cooldownMultiplier;
    }

    public final float getUseTimeMultiplier()
    {
        return useMultiplier;
    }

    public final float getAccuracyBonus()
    {
        return accuracyBonus + getConditions().getAccuracy();
    }

    public final float getMaxRangeBonus()
    {
        return maxRangeBonus + getConditions().getRange();
    }

    public final float getMinRangeMultiplier()
    {
        return minRangeMultiplier;
    }

    public final float getDodgeChance(float acc)
    {
        return getDodgeChance() - acc * getDodgeChance();
    }

    public final float getHitChance(float acc)
    {
        return 1 - getDodgeChance(acc);
    }

    public int getPullTimer()
    {
        return pullTimer;
    }

    public int getPushTimer()
    {
        return pushTimer;
    }

    public final void markPushed()
    {
        pushTimer = 0;
    }

    public final void markPulled()
    {
        pullTimer = 0;
    }

    public final boolean rollToHit(Weapon weapon)
    {
        float acc = weapon.getAccuracy();
        double roll = Math.random();
        boolean isHit = roll > getDodgeChance(acc);

        if (!isHit)
        {
            getPlayer().addDamageMitigated(weapon.getDamage());
            getPlayer().addDodge();
            Game.addDodge();

            if (Settings.showFloatTextDodge)
            {
                floatText("Dodge");
            }
        }

        getPlayer().addDodgeAttempt();

        return isHit;
    }

    public final void takeDamage(float amount, DamageType type)
    {
        if (isInvulnerable())
        {
            drawFlashInvuln = true;
        }
        else
        {
            if (amount >= 1)
            {
                drawFlash = true;
            }

            super.takeDamage(amount, type);
        }
    }

    public int getMaxRange()
    {
        if (hasWeaponOne() && hasWeaponTwo())
        {
            return Math.max(getWeaponOne().getMaxRange(), getWeaponTwo().getMaxRange());
        }
        else if (hasWeaponOne())
        {
            return getWeaponOne().getMaxRange();
        }
        else if (hasWeaponTwo())
        {
            return getWeaponTwo().getMaxRange();
        }
        else
        {
            return 0;
        }
    }

    public final int getMinRange()
    {
        if (hasWeaponOne() && hasWeaponTwo())
        {
            return Math.min(getWeaponOne().getMinRange(), getWeaponTwo().getMinRange());
        }
        else if (hasWeaponOne())
        {
            return getWeaponOne().getMinRange();
        }
        else if (hasWeaponTwo())
        {
            return getWeaponTwo().getMinRange();
        }
        else
        {
            return 0;
        }
    }

    public final boolean hasComponent(Class<? extends Component> clazz)
    {
        return components.has(clazz);
    }

    public final boolean hasMod(Class<? extends Component> clazz)
    {
        return hasComponent(clazz);
    }

    public final boolean hasWeapon(Class<? extends Weapon> clazz)
    {
        return hasComponent(clazz);
    }

    public final boolean hasWeapon(WeaponType type)
    {
        return components.has(type);
    }

    public final Component getComponent(Class<? extends Component> clazz)
    {
        return components.get(clazz);
    }


    public final Component getComponent(int i)
    {
        return components.get(i);
    }

    public final ArrayList<Component> getComponents()
    {
        return components.getAll();
    }

    public final int countComponents()
    {
        return components.getAll().size();
    }

    public final Weapon getWeapon(Class<? extends Weapon> clazz)
    {
        return (Weapon) getComponent(clazz);
    }


    public final Point getSpawn(Player p)
    {
        BaseShip b = Game.getBaseShip(p);
        if (b == null)
        {
            return new Point(0, 0);
        }
        Rectangle zone = new Rectangle(b.getX() + SPAWN_BORDER, b.getY() + SPAWN_BORDER, b.getWidth() - SPAWN_BORDER, b.getHeight() - SPAWN_BORDER);
        return new Point(Utility.random(zone.getX(), zone.getX() + zone.getWidth()), Utility.random(zone.getY(), zone.getY() + zone.getHeight()));
    }

    public final BaseShip getHomeBase()
    {
        return getPlayer().getMyBase();
    }

    public final BaseShip getEnemyBase()
    {
        return getPlayer().getEnemyBase();
    }


    public final ArrayList<Unit> getUnits(Player p)
    {
        if (p.getTeam() == Values.TEAM_ONE_ID) return Game.getTeamOneUnits();
        else return Game.getTeamTwoUnits();
    }

    public final ArrayList<Unit> getEnemies()
    {
        return getUnits(getOpponent());
    }

    public final ArrayList<Unit> getEnemiesExcludeBaseShip()
    {
        ArrayList<Unit> enemyUnits = new ArrayList<Unit>();
        ArrayList<Unit> enemies = getEnemies();

        for (Unit u : enemies)
        {
            if (!(u instanceof BaseShip))
            {
                enemyUnits.add(u);
            }
        }

        return enemyUnits;
    }


    public final ArrayList<Unit> getAllies()
    {
        return getUnits(getPlayer());
    }


    public final ArrayList<Unit> getAlliesExcludeBaseShip()
    {
        ArrayList<Unit> alliedUnits = new ArrayList<Unit>();
        ArrayList<Unit> allies = getUnits(getPlayer());

        for (Unit u : allies)
        {
            if (!(u instanceof BaseShip))
            {
                alliedUnits.add(u);
            }
        }

        return alliedUnits;
    }

    // Find the nearest unit owned
    public final Unit getNearestUnit()
    {
        Unit a = Game.getUnitKDTree(getPlayer()).nearest(this);
        Unit b = Game.getUnitKDTree(getOpponent()).nearest(this);
        return getDistance(a) < getDistance(b) ? a : b;
    }

    // Find the nearest unit owned by specified player than is belongs to the specified class
    public final Unit getNearestUnit(Player p, Class<? extends Unit> clazz)
    {
        return Game.getUnitKDTree(p).nearestIncludeClass(this, clazz);
    }

    // Find the nearest unit owned by specified player than is NOT of the specified class
    public final Unit getNearestUnitExclude(Player p, Class<? extends Unit> clazz)
    {
        return Game.getUnitKDTree(p).nearestExcludeClass(this, clazz);
    }

    public final Unit getNearestUnitWithComponent(Player p, Class<? extends Component> clazz)
    {
        return Game.getUnitKDTree(p).nearestWithComponent(this, clazz);
    }

    public final Unit getNearestEnemy()
    {
        Unit u = Game.getUnitKDTree(getOpponent()).nearest(this);

        if (u == null)
        {
            u = getPlayer().getEnemyBase();
        }

        return u;
    }

    public final Unit getNearestEnemyUnit()
    {
        return getNearestUnitExclude(getPlayer().getOpponent(), BaseShip.class);
    }

    public final Unit getNearestAlly()
    {
        Unit u = Game.getUnitKDTree(getPlayer()).nearest(this);

        if (u == null)
        {
            u = getPlayer().getMyBase();
        }

        return u;
    }

    public final Unit getNearestAlly(Class<? extends Unit> clazz)
    {
        return getNearestUnit(getPlayer(), clazz);
    }

    public final Unit getNearestAllyUnit()
    {
        return getNearestUnitExclude(getPlayer(), BaseShip.class);
    }

    public final ArrayList<Unit> getAlliesInRadiusWithComponent(float radius, Class<? extends Component> clazz)
    {
        return Game.getUnitKDTree(getPlayer()).rangeWithComponent(this, (int) radius, clazz);
    }

    public final int countAlliesInRadius(float radius)
    {
        ArrayList<Unit> allies = getAllies();
        if (allies == null || allies.isEmpty())
        {
            return 0;
        }
        return getAlliesInRadius(radius, Unit.class).size();
    }

    public final ArrayList<Unit> getAlliesInRadius(float radius)
    {
        return getAlliesInRadius(radius, Unit.class);
    }

    public final ArrayList<Unit> getAlliesInRadius(float radius, Class<? extends Unit> clazz)
    {
        return getAlliesInRadius(radius, clazz, getPosition());
    }

    public final ArrayList<Unit> getAlliesInRadius(float radius, Class<? extends Unit> clazz, Point p)
    {
        return Game.getUnitKDTree(getPlayer()).rangeIncludeClass(p, (int) radius, clazz);
    }

    public final ArrayList<Unit> getAlliesInRadiusExclude(float radius, Class<? extends Unit> clazz)
    {
        return getAlliesInRadiusExclude(radius, clazz, getPosition());
    }

    public final ArrayList<Unit> getAlliesInRadiusExclude(float radius, Class<? extends Unit> clazz, Point p)
    {
        return Game.getUnitKDTree(getPlayer()).rangeExcludeClass(p, (int) radius, clazz);
    }

    public final int countEnemiesInRadius(float radius)
    {
        ArrayList<Unit> enemies = getEnemies();
        if (enemies == null || enemies.isEmpty())
        {
            return 0;
        }
        return getEnemiesInRadius(radius, Unit.class).size();
    }

    public final ArrayList<Unit> getEnemiesInRadius(float radius)
    {
        return getEnemiesInRadius(radius, Unit.class);
    }

    public final ArrayList<Unit> getEnemiesInRadius(float radius, Class<? extends Unit> clazz)
    {
        return getEnemiesInRadius(radius, clazz, getPosition());
    }

    public final ArrayList<Unit> getEnemiesInRadiusWithComponent(float radius, Class<? extends Component> clazz)
    {
        return Game.getUnitKDTree(getOpponent()).rangeWithComponent(this, (int) radius, clazz);
    }

    public final ArrayList<Unit> getEnemiesInRadius(float radius, Class<? extends Unit> clazz, Point p)
    {
        return Game.getUnitKDTree(getOpponent()).rangeIncludeClass(p, (int) radius, clazz);
    }

    public final ArrayList<Unit> getNearestEnemiesInRadius(int radius, int number)
    {
        ArrayList<Unit> enemyUnits = getEnemiesInRadius(radius);
        ArrayList<Unit> nearestUnits = new ArrayList<Unit>();

        // Loop through all enemy units within radius
        for (Unit u : enemyUnits)
        {
            boolean added = false;
            float distance = getDistance(u);

            // First one always added
            if (nearestUnits.isEmpty())
            {
                nearestUnits.add(u);
            }

            // Otherwise, check all units
            else
            {

                for (int i = 0; i < nearestUnits.size(); i++)
                {
                    // If it's closer than the current unit, put it in front
                    Unit n = nearestUnits.get(i);

                    if (distance <= getDistance(n))
                    {
                        nearestUnits.add(i, u);
                        added = true;
                        break;
                    }
                }

                // If it's under target, put it at the end
                if (nearestUnits.size() < number && !added)
                {
                    nearestUnits.add(u);
                    added = true;
                }

                // If it's too long, remove the last one
                else if (nearestUnits.size() > number)
                {
                    nearestUnits.remove(nearestUnits.size() - 1);
                }

            }

        }

        return nearestUnits;
    }


    public final Node getNearestNode(Class<? extends Node> clazz)
    {
        float nearestDistance = Float.MAX_VALUE;
        Node nearestNode = null;
        ArrayList<Node> nodes = NodeManager.getNodes();

        for (Node n : nodes)
        {
            if (n.isInBounds() && getDistance(n.getPosition()) < nearestDistance && clazz.isInstance(n))
            {
                nearestNode = n;
                nearestDistance = getDistance(n.getPosition());
            }
        }

        return nearestNode;
    }

    public final Node getNearestNode()
    {
        if (Game.getNodeKDTree() != null) return Game.getNodeKDTree().nearest(this);
        return getNearestNode(Node.class);
    }

    public final Resource getNearestResource(Class<? extends Resource> clazz)
    {
        float nearestDistance = Float.MAX_VALUE;
        Resource nearestResource = null;
        ArrayList<Resource> resources = ResourceManager.getResources();

        for (Resource r : resources)
        {
            if (r.isInBounds() && !r.isPickedUp() && getDistance(r.getPosition()) < nearestDistance && clazz.isInstance(r))
            {
                nearestResource = r;
                nearestDistance = getDistance(r.getPosition());
            }
        }

        return nearestResource;
    }

    public final Resource getNearestResource()
    {
        if (Game.getResourceKDTree() != null) return Game.getResourceKDTree().nearest(this);
        return getNearestResource(Resource.class);
    }

    /***************************** MUTATOR METHODS ***************************************/

    public final void setImageSecondary(Image i)
    {
        imageSecondary = i;
    }

    public final void setImageAccent(Image i)
    {
        imageAccent = i;
    }

    public final void setImageMove(Image i)
    {
        imageMove = i;
    }

    public final void setFrame(Frame frame)
    {
        this.frame = frame;
        addCost(frame.getCost());
        addBlock(frame.getBlock());
        setStructure(frame.getStructure());
    }


    public final void setModel(Model model)
    {
        if (modelApplied)
        {
            return;
        }

        this.model = model;
        increaseMaxStructure(model.getStructure());
//        addBlock(model.getBlock());
        increasePower(model.getPower());
        increaseCapacity(model.getCapacity());
        applyAccuracyBonus(model.getAccuracy());
        applyMaxRangeBonus(model.getRange());

        modelApplied = true;

    }

    public void applyMass()
    {
        mass = frame.getMass() + model.getMass() + components.getMass() + minerals * Resource.MASS + scrap * Resource.MASS;

        // Drag effect of mass is a percentage of 100
        // For example:  70 mass means you move at 30% speed
        float slowEffect = 1 - mass / 100f;
        float speed = Unit.BASIC_SPEED * slowEffect;

        // Acceleration applies this twice as strongly
        // For example: 70 mass means you accelerate at 15% normal rate
        float acc = Unit.BASIC_ACCELERATION * (float) Math.pow(slowEffect, 2);

        if (hasWeaponOne() && getWeaponOne().inUse())
        {
            speed *= (1 - getWeaponOne().getUseSlow());
            acc *= (1 - getWeaponOne().getUseSlow());
        }

        if (hasWeaponTwo() && getWeaponTwo().inUse())
        {
            speed *= (1 - getWeaponTwo().getUseSlow());
            acc *= (1 - getWeaponTwo().getUseSlow());
        }

        setSpeed(speed);
        setAcceleration(acc);

    }

    public final void deposit()
    {
        if (getDistance(getHomeBase()) < 100)
        {
            float resources = getMinerals() + getScrap();

            if (Settings.difficultyAffectsIncome)
            {
                resources *= getPlayer().getDifficultyRating();
            }
            getPlayer().addMinerals(resources);

            minerals = 0;
            scrap = 0;
        }

    }

    public final void transfer(Unit u)
    {

        while (getDistance(u) <= TRANSFER_RANGE && u.hasCapacity() && minerals > 0)
        {
            u.collect(new Minerals(x, y));
            minerals--;
        }

        while (getDistance(u) <= TRANSFER_RANGE && u.hasCapacity() && scrap > 0)
        {
            u.collect(new Scrap(x, y));
            scrap--;
        }
    }

    public final void dump()
    {
        for (int i = 0; i < minerals; i++)
        {
            float xPos = x + Utility.random(w);
            float yPos = y + Utility.random(h);
            ResourceManager.add(new Minerals(xPos, yPos, xSpeed, ySpeed));
        }
        minerals = 0;

        for (int i = 0; i < scrap; i++)
        {
            float xPos = x + Utility.random(w);
            float yPos = y + Utility.random(h);
            ResourceManager.add(new Scrap(xPos, yPos, xSpeed, ySpeed));
        }
        scrap = 0;
    }

    public final void collect(Resource r)
    {

        // Safeguard to avoid players from picking up old entities.  Currently disabled, I don't think we need it.
//		if (r.getGameNumber() != Game.getGameNumber()) {
//			DisplayManager.addMessage("Error: " + this + " is trying to pick up an entity from a previous game.", 800);
//			DisplayManager.addMessage("If you have copied the resources array, make sure to clear it every game.", 800);
//			return;
//		}
//        dbgMessage((r != null) + " " + hasWeapon(Collector.class) + " " + (getDistance(r) <= Collector.PICKUP_RADIUS) + " " + hasCapacity());

        if (r != null && hasWeapon(Collector.class) && getDistance(r) <= Collector.PICKUP_RADIUS && hasCapacity())
        {
            if (r instanceof Minerals && !r.isPickedUp())
            {

                r.setForRemovalFromMap();
                minerals++;
            }
            else if (r instanceof Scrap && !r.isPickedUp())
            {
                r.setForRemovalFromMap();
                scrap++;
            }
        }
    }

    public final void setCapacity(float amount)
    {
        capacity = amount;
    }

    public final void increaseCapacity(float amount)
    {
        capacity += amount;
    }


    public final void applyCooldownTimeMultiplier(float amount)
    {
        cooldownMultiplier = Math.max(0, cooldownMultiplier * amount);
    }

    public final void applyUseMultiplier(float amount)
    {
        useMultiplier = Math.max(0, useMultiplier * amount);
    }

    public final void applyAccuracyBonus(float amount)
    {
        accuracyBonus = Math.min(0, accuracyBonus + amount);
    }

    public final void applyMaxRangeBonus(int amount)
    {
        maxRangeBonus = Math.max(0, maxRangeBonus + amount);
    }

    public final void applyMinRangeMultiplier(float amount)
    {
        minRangeMultiplier = Math.max(0, minRangeMultiplier * amount);
    }

    // Retained for backward compatibility for now
    public final void add(Component c)
    {
        components.add(c);
    }

    // Preferred, modern version
    public final void add(Class<? extends Component> clazz)
    {
        components.add(clazz);

    }

    public final float getDodgeChance()
    {
        return getCurSpeed() / Values.SPEED / Values.DODGE_FULL;
    }


    public void update()
    {
        super.update();

        components.update();

        pullTimer++;
        pushTimer++;
        updateSmoke();

        // Clear missiles from list
        for(int i = 0; i < missilesLocked.size(); i++)
        {
            if(missilesLocked.get(i).isDead())
            {
                missilesLocked.remove(i);
                i--;
            }
        }

//		dbgMessage(pullTimer);

//		


        if (Settings.dbgShowUnitCargo)
        {
            dbgMessage(getCargo() + " / " + getCapacity());
        }

        // When stunned, spin out
        if (getConditions().containsType(Stun.class))
        {
            rotate(getTheta() + getCurSpeed() * spinDirection * BASE_SPIN_RATE);
        }

        if (!isInBounds())
        {
            takeDamage(Values.OUT_OF_BOUNDS_DAMAGE_PER_FRAME, DamageType.TRUE);
        }
    }

    private final void updateSmoke()
    {
        //dbgMessage((getStructureMultiplier()));
        int smokeFreq = (int) (50 * Math.pow(getStructureSlowPercent(), 2));
        if (!(this instanceof BaseShip) && getStructureSlowPercent() < 1 && Game.getTime() % smokeFreq == 0)
        {
            float scale = 1 - getStructureSlowPercent();
            float x = getCenterX() + Utility.random(-getSize() * scale, getSize() * scale);
            float y = getCenterY() + Utility.random(-getSize() * scale, getSize() * scale);
            float size = Utility.random(getSize() * .65, getSize() * .9);
            AnimationManager.add(new Smoke(x, y, size));
        }
    }

    protected final void updateSpeed()
    {
        if (this instanceof BaseShip)
        {
            changeSpeed(0, theta, false);
            return;
        }

        applyMass();

        float maxSpeedAfterConditions = getMaxSpeedBase();
        float accAfterConditions = getAccelerationBase();

        if (getConditions().modifiesSpeed())
        {
            maxSpeedAfterConditions = getConditions().getModifiedSpeed(getMaxSpeedBase());
            accAfterConditions = getConditions().getModifiedSpeed(getAccelerationBase());
        }

        setSpeedCurrent(maxSpeedAfterConditions * getStructureSlowPercent());
        setAccelerationCurrent(accAfterConditions * getStructureSlowPercent());

        changeSpeed(0, theta, false);
    }

    public final void addCost(int amount)
    {
        cost += amount;
    }


    public final void setImage()
    {
        setImage(sheet.getSprite(0, 0));
        setImageSecondary(sheet.getSprite(1, 0));
        setImageAccent(sheet.getSprite(2, 0));

        if (this instanceof BaseShip || !isMoving())
        {
            setImageMove(null);
        }
        else if (atMaxSpeed())
        {
            setImageMove(sheet.getSprite(4, 0));
        }
        else
        {
            setImageMove(sheet.getSprite(3, 0));
        }

        updateWidthAndHeightToScale();

    }


    public final void renderUnit(Graphics g)
    {
        renderGlory(g);
        renderPrimary(g);
        renderSecondary(g);
        renderAccent(g);
        renderMove(g);
        renderConditions(g);

    }

    public final void renderConditions(Graphics g)
    {
        // When moving faster than normal, show animation
        if (hasCondition(Fast.class) && timer % Afterimage.FREQUENCY == 0)
        {
            AnimationManager.add(new Afterimage(this, getX(), getY()));
        }

        if (hasCondition(Revelry.class) && Game.getTime() % 2 == 0)
        {
            revelryColor = Utility.getRandomColor(150, 255);
        }


    }

    public final void renderGlory(Graphics g)
    {

        if (image == null || !hasCondition(Glory.class))
        {
            return;
        }

        float scale = 1.3f * getScale();
        int tmpW = (int) (image.getWidth() * scale);
        int tmpH = (int) (image.getHeight() * scale);


        Image tmp = image.getScaledCopy(scale);
        tmp.setCenterOfRotation(tmp.getWidth() * getScale() * .5f, tmp.getHeight() * getScale() * .5f);
        tmp.setRotation(getTheta());
        //g.drawImage(tmp, x, y, p.getColorPrimary());

//		if(drawFlash)
//
        float r = Utility.random(.8f, 1.0f);
        Color c = new Color(1, 1, .7f, r);
        tmp.draw(x - tmpW / 8, y - tmpH / 8, c);

//			drawFlash = false;
//		}
//		else
//		{
//			if(hasCondition(Revelry.class) && revelryColor != null)
//			{
//				tmp.draw(x, y, revelryColor);
//			}
//			else
//			{
//				tmp.draw(x, y, getColorPrimary());
//			}
//		}
    }

    public final void renderPrimary(Graphics g)
    {
        if (image == null)
        {
            return;
        }
        Image tmp = image.getScaledCopy(getScale());
        tmp.setCenterOfRotation(tmp.getWidth() / 2 * getScale(), tmp.getHeight() / 2 * getScale());
        tmp.setRotation(getTheta());
        //g.drawImage(tmp, x, y, p.getColorPrimary());
        if (drawFlash)
        {
            tmp.drawFlash(x, y);
            drawFlash = false;
        }
        else
        {
            if (hasCondition(Revelry.class) && revelryColor != null)
            {
                tmp.draw(x, y, revelryColor);
            }
            else
            {
                tmp.draw(x, y, getColorPrimary());
            }
        }
    }

    public final void renderSecondary(Graphics g)
    {
        if (imageSecondary == null)
        {
            return;
        }

        Image tmp = imageSecondary.getScaledCopy(getScale());
        tmp.setCenterOfRotation(tmp.getWidth() / 2 * getScale(), tmp.getHeight() / 2 * getScale());
        tmp.setRotation(getTheta());
        if (drawFlash)
        {
            tmp.drawFlash(x, y);
            drawFlash = false;
        }
        else
        {
            tmp.draw(x, y, getColorSecondary());
        }
    }

    public final void renderAccent(Graphics g)
    {
        if (imageAccent == null)
        {
            return;
        }

        Image tmp = imageAccent.getScaledCopy(getScale());
        tmp.setCenterOfRotation(tmp.getWidth() / 2 * getScale(), tmp.getHeight() / 2 * getScale());
        tmp.setRotation(getTheta());

        if (drawFlash)
        {
            tmp.drawFlash(x, y);
            drawFlash = false;
        }
        else
        {
            tmp.draw(x, y, getColorAccent());
        }
    }

    public final void renderMove(Graphics g)
    {
        if (imageMove != null)
        {
            Image tmp = imageMove.getScaledCopy(getScale());
            tmp.setCenterOfRotation(tmp.getWidth() / 2 * getScale(), tmp.getHeight() / 2 * getScale());
            tmp.setRotation(getTheta());
            g.drawImage(tmp, x, y);
        }
    }


    public final void renderShield(Graphics g)
    {
        if (getCurShield() > 0 || isInvulnerable() || getConditions().containsType(Fortified.class))
        {
            // Shield Position
            float width = (float) (w * 1.85);
            float height = (float) (h * 1.85);
            float x = getCenterX() - width / 2;
            float y = getCenterY() - height / 2;

            // Shield Colors and Transparency
            int alpha = (int) (50 * (getCurShield() / getMaxShield()));

            // Modify values for aegis  shield
            if (getConditions().containsType(Fortified.class))
            {
                alpha = Utility.random(115, 130);
            }

            // Set normal colors
            Color fill = new Color(getColorPrimary().getRed(), getColorPrimary().getGreen(), getColorPrimary().getBlue(), alpha);
            Color border = new Color(getColorPrimary().getRed() + 50, getColorPrimary().getGreen() + 50, getColorPrimary().getBlue() + 50, alpha + 50);

            // Make the shield flicker when invulnerability blocks damage
            if (drawFlashInvuln)
            {
                fill = new Color(255, 255, 255, alpha);
                drawFlashInvuln = false;
            }

            g.setColor(fill);
            g.fillOval(x + 1, y + 1, width - 2, height - 2);

            if (getConditions().containsType(Fortified.class))
            {
                g.setColor(border);
                g.drawOval(x, y, width, height);
            }


            g.resetLineWidth();

        }
    }

    public void render(Graphics g)
    {
        super.render(g);

        setImage();
        renderUnit(g);
        renderShield(g);

        healthbar.render(g);
        components.render(g);

        if (isSelected())
        {
            g.setColor(Color.white);
            g.drawRect(x - w / 2, y - h / 2 - 4, w * 2, h * 2);
        }

        if (getTeam() == Values.TEAM_ONE_ID && Settings.showPlayerOneInfo)
        {
            draw(g);
        }

        if (getTeam() == Values.TEAM_TWO_ID && Settings.showPlayerTwoInfo)
        {
            draw(g);
        }


    }


    final public void deathTriggers()
    {
        getPlayer().getMyComposition().recordLost(this.getClass());
        onDeath();
    }

    final public void buildTriggers()
    {
        getPlayer().getMyComposition().recordMade(this.getClass());
        onBuild();
    }

    public void onBuild()
    {
        // This method can be overridden by players who wish to have an on build effect
    }

    public void onDeath()
    {
        // This method can be overridden by players who wish to have an on death effect
    }

    public final void die()
    {
        // Avoid double counting deaths
        if (!isAlive())
        {
            return;
        }

        dropResourcesOnDeath();
        deathTriggers();

        super.die();

        if (this instanceof BaseShip)
        {
            AnimationManager.add(new Boom(getCenterX(), getCenterY(), 300 * getScale()));
            getPlayer().loseGame();
        }
        else
        {
            AnimationManager.add(new Boom(getCenterX(), getCenterY(), getFrame().getImageSize() * getScale()));
        }

        float unitScale = this.getFrame().getCost() / 24;        // assumes 8 to 20 costs
        Sounds.boom.play(getPosition(), 1.2f - unitScale, unitScale + .2f);

        // remove self from k-d tree
        Game.regenerateTree(getPlayer());
    }

    public final void dropResourcesOnDeath()
    {
        if (isAlive())
        {
            scrap /= 2;
            minerals /= 2;

            dump();

            for (int i = 0; i < getFrame().getScrapAmountOnDeath(); i++)
            {
                ResourceManager.spawnScrapNear(x, y, xSpeed, ySpeed, getSize());
            }
        }

    }

    public final static Unit getNearestUnit(Point point, Class<? extends Unit> clazz)
    {
        Unit a = Game.getUnitKDTree(Game.getPlayerOne()).nearest(point);
        Unit b = Game.getUnitKDTree(Game.getPlayerTwo()).nearest(point);
        return Utility.distance(point, a.getPosition()) < Utility.distance(point, b.getPosition()) ? a : b;
    }

    public MissileEntity getNearestEnemyMissile()
    {
        ArrayList<MissileEntity> missiles = Game.getMissiles();
        float nearestMissileDistance = Float.MAX_VALUE;
        MissileEntity nearestMissile = null;

        for (MissileEntity m : missiles)
        {
//			// Eventually shadowflight will be cloaked.  This is a temporary solution.
//			if(m instanceof MissileShadowflight)
//			{
//				continue;
//			}

            if (getDistance(m) < nearestMissileDistance && m.getOwner().getPlayer() == getOpponent())
            {
                nearestMissileDistance = getDistance(m);
                nearestMissile = m;
            }
        }

        return nearestMissile;
    }

    public boolean hasMissileLockedOnMe()
    {
        return !missilesLocked.isEmpty();
    }

    public MissileEntity getNearestMissileLockedOnMe()
    {
        ArrayList<MissileEntity> missiles = missilesLocked;
        float nearestMissileDistance = Float.MAX_VALUE;
        MissileEntity nearestMissile = null;

        for (MissileEntity m : missiles)
        {
            if (getDistance(m) < nearestMissileDistance && m.getOwner().getPlayer() == getOpponent())
            {
                nearestMissileDistance = getDistance(m);
                nearestMissile = m;
            }
        }

        return nearestMissile;
    }


}
