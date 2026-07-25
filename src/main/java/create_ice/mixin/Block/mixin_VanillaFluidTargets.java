package create_ice.mixin.Block;

import com.zurrtum.create.content.fluids.pipes.VanillaFluidTargets;
import com.zurrtum.create.infrastructure.fluids.BucketFluidInventory;
import com.zurrtum.create.infrastructure.fluids.FluidStack;
import create_ice.Nodes.Fluid.AllFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VanillaFluidTargets.class)
public class mixin_VanillaFluidTargets {
    @Inject(at = @At("HEAD"),method = "canProvideFluidWithoutCapability",cancellable = true)
    private static void canProvideFluidWithoutCapability(BlockState state, CallbackInfoReturnable<Boolean> cir)
    {
        if(state.is(Blocks.POWDER_SNOW_CAULDRON) || state.is(Blocks.POWDER_SNOW))
        {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"),method = "drainBlock",cancellable = true)
    private static void drainBlock(Level level, BlockPos pos, BlockState state, boolean simulate, CallbackInfoReturnable<FluidStack> cir)
    {
        if(state.is(Blocks.POWDER_SNOW_CAULDRON))
        {
            if(!simulate)
            {
                level.setBlock(pos,Blocks.CAULDRON.defaultBlockState(),Block.UPDATE_ALL);
            }
            cir.setReturnValue(new FluidStack(AllFluid.POWDER_SNOW_FLUID,BucketFluidInventory.CAPACITY));
            cir.cancel();
        }
        else if(state.is(Blocks.POWDER_SNOW))
        {
            if(!simulate)
            {
                level.setBlock(pos,Blocks.AIR.defaultBlockState(),Block.UPDATE_ALL);
            }
            cir.setReturnValue(new FluidStack(AllFluid.POWDER_SNOW_FLUID,BucketFluidInventory.CAPACITY));
            cir.cancel();
        }
    }
}
