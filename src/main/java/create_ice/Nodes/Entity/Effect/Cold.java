package create_ice.Nodes.Entity.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Cold extends MobEffect {
    protected Cold()
    {
        super(MobEffectCategory.HARMFUL,0xe8f8f8);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public void onEffectStarted(final LivingEntity mob, final int amplifier)
    {
        mob.setIsInPowderSnow(true);
        mob.setTicksFrozen(20*20*2);
    }
}
