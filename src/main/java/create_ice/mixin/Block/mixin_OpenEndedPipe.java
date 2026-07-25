package create_ice.mixin.Block;

import com.zurrtum.create.content.fluids.OpenEndedPipe;
import com.zurrtum.create.infrastructure.fluids.FluidStack;
import create_ice.Nodes.Fluid.AllFluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OpenEndedPipe.class)
public class mixin_OpenEndedPipe {
    @Inject(at = @At("HEAD"),method = "provideFluidToSpace", cancellable = true)
    private void provideFluidToSpace(FluidStack fluid, boolean simulate, CallbackInfoReturnable<Boolean> cir)
    {
        OpenEndedPipe e = (OpenEndedPipe)(Object)this;
        Level world = e.getWorld();
        if(world == null || !world.isLoaded(e.getOutputPos()))
            return;
        //
        if(fluid.isOf(AllFluid.POWDER_SNOW_FLUID) && world.getBlockState(e.getOutputPos()).is(Blocks.AIR))
        {
            if(!simulate)
            {
                world.setBlock(
                        e.getOutputPos(),
                        AllFluid.POWDER_SNOW_FLUID.defaultFluidState().createLegacyBlock(),
                        Block.UPDATE_ALL
                );
            }
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
