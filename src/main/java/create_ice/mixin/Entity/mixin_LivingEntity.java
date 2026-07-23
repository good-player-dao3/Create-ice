package create_ice.mixin.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class mixin_LivingEntity
{
    @Redirect(
        method = "travelInAir",
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/entity/LivingEntity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;"
        )
    )
    public BlockPos travelInAir(LivingEntity instance)
    {
        LivingEntity e = (LivingEntity)(Object)this;
        BlockPos p = e.getBlockPosBelowThatAffectsMyMovement();
        return e.level().getBlockState(p.above()).getBlock().getFriction() > e.level().getBlockState(p).getBlock().getFriction()?p.above():p;
    }
}
