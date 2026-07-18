package create_ice.Nodes.Fluid.Fluids;

import com.zurrtum.create.infrastructure.fluids.FlowableFluid;
import com.zurrtum.create.infrastructure.fluids.FluidEntry;
import create_ice.Nodes.Blocks.AllBlocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jspecify.annotations.NonNull;

public class Powder_Snow_Fluid {
    public static class Still extends FlowableFluid.Still {
        public Still(FluidEntry entry)
        {
            super(entry);
        }

        @Override
        public @NonNull BlockState createLegacyBlock(@NonNull FluidState state)
        {
            return AllBlocks.POWDER_SNOW_FB.defaultBlockState().setValue(LiquidBlock.LEVEL,getLegacyLevel(state));
        }
    }
}
