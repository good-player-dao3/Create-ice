package create_ice.mixin.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntity.class)
public class mixin_ItemEntity
{
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;"
            )
    )
    public BlockPos tick(ItemEntity instance)
    {
        ItemEntity e = (ItemEntity)(Object)this;
        BlockPos p = e.getBlockPosBelowThatAffectsMyMovement();
        return e.level().getBlockState(p.above()).getBlock().getFriction() > e.level().getBlockState(p).getBlock().getFriction()?p.above():p;
    }
}
