package create_ice.Nodes.Fluid.Powder_Snow;

import create_ice.Nodes.Fluid.AllFluid;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jspecify.annotations.NonNull;

public class Powder_Snow extends FlowingFluid {
    @Override
    public @NonNull Fluid getFlowing() {
        return AllFluid.POWDER_SNOW_FLUID;
    }

    @Override
    public @NonNull Fluid getSource() {
        return AllFluid.POWDER_SNOW_FLUID;
    }

    @Override
    public boolean isSame(Fluid fluid)
    {
        return fluid == AllFluid.POWDER_SNOW_FLUID;
    }

    @Override
    protected boolean canConvertToSource(@NonNull ServerLevel level) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {

    }

    @Override
    protected int getSlopeFindDistance(LevelReader level) {
        return 0;
    }

    @Override
    protected int getDropOff(LevelReader level) {
        return 0;
    }

    @Override
    public @NonNull Item getBucket() {
        return Items.POWDER_SNOW_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid other, Direction direction) {
        return false;
    }

    @Override
    public int getTickDelay(@NonNull LevelReader level) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    protected @NonNull BlockState createLegacyBlock(@NonNull FluidState fluidState) {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSource(@NonNull FluidState fluidState) {
        return true;
    }

    @Override
    public int getAmount(@NonNull FluidState fluidState) {
        return (int)FluidConstants.BUCKET;
    }
}
