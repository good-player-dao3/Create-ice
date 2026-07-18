package create_ice.Nodes.Blocks.Block_Class;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class Powder_Snow_Fluid_ extends LiquidBlock
{
    public Powder_Snow_Fluid_(FlowingFluid fluid, Properties properties)
    {
        super(fluid, properties);
    }

    @Override
    protected void onPlace(BlockState state,Level level,BlockPos pos,BlockState oldState,boolean movedByPiston) {
        if(state.getValue(LEVEL) == 0)
        {
            level.setBlock(
                    pos,
                    Blocks.POWDER_SNOW.defaultBlockState(),
                    Block.UPDATE_ALL
            );
        }
    }
}
