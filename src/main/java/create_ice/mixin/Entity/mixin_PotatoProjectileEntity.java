package create_ice.mixin.Entity;

import com.zurrtum.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import create_ice.Nodes.Entity.Snowball_more;
import create_ice.Nodes.Items.AllItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownLingeringPotion;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotatoProjectileEntity.class)
public abstract class mixin_PotatoProjectileEntity {
    @Inject(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/phys/EntityHitResult;getLocation()Lnet/minecraft/world/phys/Vec3;"
            ),
            cancellable = true
    )
    public void onHitEntity(EntityHitResult ray,CallbackInfo ci)
    {
        var e = (PotatoProjectileEntity)(Object)this;
        var stack = e.getItem();
        if(e.level().isClientSide())
            return;
        if(stack.is(AllItem.SNOWBALL_MORE))
        {
            if(!Snowball_more.hitEntity(ray,stack))
            {
                ci.cancel();
            }
        }
        else if(stack.is(Items.POTION) && ray.getEntity() instanceof LivingEntity entity)
        {
            var effect = stack.get(DataComponents.POTION_CONTENTS);
            if(effect == null)
                return;
            for(var ef:effect.getAllEffects())
            {
                entity.addEffect(new MobEffectInstance(ef));
                ci.cancel();
            }
        }
        else if(stack.is(Items.SPLASH_POTION))
        {
            onHit_SPLASH_POTION(ray,e,stack);
        }
        else if(stack.is(Items.LINGERING_POTION))
        {
            onHit_LINGERING_POTION(ray,e,stack);
        }
    }
    @Inject(
            method = "onHitBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/hurtingprojectile/AbstractHurtingProjectile;onHitBlock(Lnet/minecraft/world/phys/BlockHitResult;)V"
            )
    )
    public void onHitBlock(BlockHitResult ray, CallbackInfo ci)
    {
        var e = (PotatoProjectileEntity)(Object)this;
        var stack = e.getItem();
        if(e.level().isClientSide())
            return;

        if(stack.is(Items.SPLASH_POTION))
        {
            onHit_SPLASH_POTION(ray,e,stack);
        }
        else if(stack.is(Items.LINGERING_POTION))
        {
            onHit_LINGERING_POTION(ray,e,stack);
        }
    }

    @Unique
    private static void onHit_SPLASH_POTION(HitResult ray, PotatoProjectileEntity e, ItemStack stack)
    {
        new ThrownSplashPotion(
                e.level(),
                e.position().x(),
                e.position().y(),
                e.position().z(),
                stack.copy()
        ).onHitAsPotion(
                (ServerLevel) e.level(),
                stack.copy(),
                ray
        );
        PotionContents potion = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        int type = potion.potion().isPresent() && potion.potion().get().value().hasInstantEffects() ? 2007 : 2002;
        e.level().levelEvent(type,e.blockPosition(),potion.getColor());
    }

    @Unique
    private static void onHit_LINGERING_POTION(HitResult ray, PotatoProjectileEntity e, ItemStack stack)
    {
        new ThrownLingeringPotion(
                e.level(),
                e.position().x(),
                e.position().y(),
                e.position().z(),
                stack.copy()
        ).onHitAsPotion(
                (ServerLevel) e.level(),
                stack.copy(),
                ray
        );
    }
}
