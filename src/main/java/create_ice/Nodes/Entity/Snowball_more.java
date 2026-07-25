package create_ice.Nodes.Entity;

import create_ice.Nodes.Items.Item_Class.SnowballItem_more;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Snowball_more extends Snowball
{
    ItemStack stack;

    public Snowball_more(final Level level, final LivingEntity mob, final ItemStack itemStack)
    {
        super(level,mob,itemStack);
        this.stack = itemStack.copy();
    }

    public Snowball_more(final Level level, final double x, final double y, final double z, final ItemStack itemStack)
    {
        super(level,x,y,z,itemStack);
        this.stack = itemStack.copy();
    }

    @Override
    protected void onHitEntity(final @NonNull EntityHitResult hitResult)
    {
        boolean hurt = true;
        if(this.stack != null && (hitResult.getEntity() instanceof LivingEntity entity))
        {
            List<MobEffectInstance> effects = new ArrayList<>(this.stack.getOrDefault(SnowballItem_more.ADD_TYPE,List.of()));
            for(MobEffectInstance e:effects)
            {
                entity.addEffect(new MobEffectInstance(e));
                if(e.is(MobEffects.INSTANT_HEALTH) || e.is(MobEffects.INSTANT_DAMAGE))
                    hurt = false;
            }
        }
        if(hurt)
            super.onHitEntity(hitResult);
    }
}
