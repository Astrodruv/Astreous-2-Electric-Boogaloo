package components.weapon.economy;

import animations.AnimationManager;
import animations.beams.AnimBeamConstantTransparent;
import components.DamageType;
import components.weapon.WeaponTargetResource;
import components.weapon.WeaponType;
import engine.Values;
import objects.resource.Resource;

public class Collector extends WeaponTargetResource {
    public static final int SIZE = 2;
    public static final int MAX_RANGE = 300;
    public static final int DAMAGE = 0;
    public static final int USE_TIME = 1;
    public static final int COOLDOWN = 0;
    public static final int CAPACITY = 2;

    public static final float PULL_ACC = 220 * Values.ACC;
    public static final int PICKUP_RADIUS = 75;

    public static final WeaponType WEAPON_TYPE = WeaponType.RESOURCE;
    public static final DamageType DAMAGE_TYPE = DamageType.NONE;
    public static final int MASS = 8;
    public static final float USE_SLOW = .40f;

    public static final int ANIM_BEAM_WIDTH = 17;
    public static final int ANIM_BEAM_DURATION = USE_TIME + COOLDOWN;
    public static final int ANIM_BEAM_ALPHA = 75;

    public Collector()
    {
        super();
        size = SIZE;
        damage = DAMAGE;
        maxRange = MAX_RANGE;
        cooldown = COOLDOWN;
        useTime = USE_TIME;
        mass = MASS;
        weaponType = WEAPON_TYPE;
        damageType = DAMAGE_TYPE;
        name = "Collector";
        useSlow = USE_SLOW;
    }

    @Override
    public void applyMod() {

    }

    public int getMaxRange() {
        return MAX_RANGE;
    }

    public int getMinRange() 						{	return 0;					}

    protected void playAudio() {
        //AudioManager.playLaser(owner.getPosition(), 1.5f);
    }

//    protected void animation()
//	{
////        if(targetResource == null)
////        {
////            getOwner().dbgMessage("target null");
////            return;
////        }
////
////        if(targetResource.wasPickedUp())
////        {
////            getOwner().dbgMessage("target picked up");
////            return;
////        }
////
////        float distance = getOwner().getDistance(targetResource.getPosition());
////
////        if(distance > getMaxRange())
////        {
////            getOwner().dbgMessage("target out of range");
////            return;
////        }
////
////        getOwner().dbgMessage("target is " + targetResource);
//
//    }

    @Override
    protected void animation(Resource target) {
        AnimationManager.add(new AnimBeamConstantTransparent(getOwner(), target, ANIM_BEAM_WIDTH, ANIM_BEAM_DURATION, ANIM_BEAM_ALPHA - 25, ANIM_BEAM_ALPHA + 25));

    }

    @Override
    protected void activation(Resource target) {

//        float distance = getOwner().getDistance(target.getPosition());
//
//        if(distance > getMaxRange() || getOwner().isFull())
//        {
//            return;
//        }

        target.pull(getOwner(), PULL_ACC);


        //getOwner().dbgMessage(getOwner().getDistance(target));

        if (getOwner().getDistance(target) > PICKUP_RADIUS) {
            return;
        }



        getOwner().collect(target);




    }

    public void onAddition () {
        super.onAddition();
        getOwner().increaseCapacity(CAPACITY);
    }

}