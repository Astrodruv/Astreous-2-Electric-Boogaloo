package components.weapon.energy;

import animations.AnimationManager;
import animations.beams.AnimBeamBurst;
import animations.beams.AnimBeamConstantTransparent;
import components.DamageType;
import components.mod.offense.EosMod;
import components.mod.offense.HadesMod;
import components.mod.offense.PoseidonMod;
import components.mod.offense.ZeusMod;
import components.weapon.WeaponTargetUnit;
import components.weapon.WeaponType;
import conditions.buffs.Fortified;
import conditions.debuffs.Decay;
import conditions.debuffs.Push;
import conditions.debuffs.Stun;
import conditions.instant.Damage;
import objects.entity.unit.Unit;
import ui.sound.Sounds;

public abstract class EnergyWeapon extends WeaponTargetUnit
{
    public static final WeaponType WEAPON_TYPE = WeaponType.ENERGY;
    public static final DamageType DAMAGE_TYPE = DamageType.TRUE;

    protected int animationWidth;
    protected int animationDuration;
    protected float soundPitch;

    public EnergyWeapon()
    {
        super();
        weaponType = WEAPON_TYPE;
        damageType = DAMAGE_TYPE;
        name = "Laser";
    }


    public void applyMod()
    {
        if(getOwner().hasMod(EosMod.class))
        {
            name = EosMod.NAME;
            accuracy += EosMod.LASER_ACCURACY_BONUS;
        }

        if(getOwner().hasMod(PoseidonMod.class))
        {
            name = PoseidonMod.NAME;
            animationDuration *= .5f;
            animationWidth *= 3;
        }

        if(getOwner().hasMod(ZeusMod.class))
        {
          //  System.out.println(maxRange);

//            damage *= ZeusMod.DAMAGE_SCALAR;
            maxRange += ZeusMod.RANGE_BONUS;
            accuracy = accuracy * ZeusMod.ACC_SCALAR;
            name = ZeusMod.NAME;

            //System.out.println(maxRange);
        }

        if(getOwner().hasMod(HadesMod.class))
        {
            name = HadesMod.NAME_ENERGY;
        }




    }

    protected void playAudio()
    {
        Sounds.laser.play(getOwner().getPosition(), soundPitch);
    }

    protected void animation(Unit a, boolean isHit)
    {
        if(getOwner().hasMod(PoseidonMod.class))
        {
            AnimationManager.add(new AnimBeamConstantTransparent(getOwner(), a, Math.round(animationWidth * getOwner().getPower()), animationDuration));
        }
        else
        {
            AnimationManager.add(new AnimBeamBurst(getOwner(), a, Math.round(animationWidth * getOwner().getPower()), animationDuration));
        }
    }

    protected void activation(Unit target, boolean isHit)
    {
//        public static final float CRITICAL_CHANCE = .15f;
//        public static final float CRITICAL_DAMAGE = 2.00f;
//        public static final float STUN_DURATION = 2 * 60;

        if(isHit)
        {
            float damage = getDamage();

            if(getOwner().hasMod(PoseidonMod.class))
            {
                damage += getOwner().getCurShield() * PoseidonMod.DAMAGE_FROM_SHIELD_SCALAR;
            }

            target.addCondition(new Damage(damage, getDamageType()));

            // Knockback
            if(getOwner().hasMod(PoseidonMod.class) && !target.hasCondition(Fortified.class))
            {
                target.addCondition(new Push(PoseidonMod.LASER_PUSH_DURATION, animationDuration, PoseidonMod.LASER_PUSH_EFFECT, getOwner()));
            }

            // Hades Mod Decay Effect
            if(getOwner().hasMod(HadesMod.class))
            {
                target.addCondition(new Decay(HadesMod.DECAY_TIME, getActualTravelTime(target)));
//                target.getConditions().removeBuffs();
            }
        }
    }

}
