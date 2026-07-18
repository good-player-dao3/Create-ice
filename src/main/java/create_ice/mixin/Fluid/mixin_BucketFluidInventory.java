package create_ice.mixin.Fluid;

import com.zurrtum.create.infrastructure.fluids.BucketFluidInventory;
import com.zurrtum.create.infrastructure.fluids.FluidItemInventoryWrapper;
import com.zurrtum.create.infrastructure.fluids.FluidStack;
import create_ice.Nodes.Fluid.AllFluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketFluidInventory.class)
public abstract class mixin_BucketFluidInventory extends FluidItemInventoryWrapper {
    @Inject(at = @At("HEAD"), method = "toFluid", cancellable = true)
    public void toFluid(CallbackInfoReturnable<net.minecraft.world.level.material.Fluid> cir)
    {
        if(this.stack.is(Items.POWDER_SNOW_BUCKET))
        {
            cir.setReturnValue(AllFluid.POWDER_SNOW_FLUID);
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "toFillBucket", cancellable = true)
    public void toFillBucket(FluidStack stack,CallbackInfoReturnable<ItemStack> cir)
    {
        if(stack.isOf(AllFluid.POWDER_SNOW_FLUID))
        {
            cir.setReturnValue(Items.POWDER_SNOW_BUCKET.getDefaultInstance());
            cir.cancel();
        }
    }
}
