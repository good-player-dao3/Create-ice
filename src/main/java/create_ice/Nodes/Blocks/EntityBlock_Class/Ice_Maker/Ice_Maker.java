package create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker;

import com.zurrtum.create.AllBlocks;
import com.zurrtum.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.zurrtum.create.foundation.block.IBE;
import create_ice.Nodes.Blocks.EntityBlock_Class.EntityTypes;
import create_ice.Nodes.Groups.GroupInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class Ice_Maker extends RotatedPillarKineticBlock implements IBE<Entity_Ice_Maker>, GroupInterface<Ice_Maker>
{
    public Ice_Maker(Properties properties)
    {
        super(properties);
    }
    //Block
    @Override
    protected @NonNull BlockState updateShape(
            final @NonNull BlockState state,
            final @NonNull LevelReader level,
            final @NonNull ScheduledTickAccess ticks,
            final @NonNull BlockPos pos,
            final @NonNull Direction directionToNeighbour,
            final @NonNull BlockPos neighbourPos,
            final @NonNull BlockState neighbourState,
            final @NonNull RandomSource random
    )
    {
        if(directionToNeighbour == Direction.DOWN && !neighbourState.is(AllBlocks.BASIN))
        {
            if(level instanceof ServerLevel serverLevel)
                Block.popResource(serverLevel,pos,this.asItem().getDefaultInstance());
            return Blocks.AIR.defaultBlockState();
        }
        else
            return state;
    }

    @Override
    public BlockState getStateForPlacement(@NonNull BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        if(
                state == null ||
                !context.getLevel().getBlockState(context.getClickedPos().below()).is(AllBlocks.BASIN)
        )
            return null;
        else
            return state
                    .setValue(
                            AXIS,
                            context.getHorizontalDirection().getAxis() == Direction.Axis.X?Direction.Axis.Z:Direction.Axis.X
                    );
    }

    public static final VoxelShape Shape = Shapes.or(
            Block.box(0,0,0,16,2,16),
            Block.box(1,2,1,15,14,15)
    );
    @Override
    public @NonNull VoxelShape getShape(
            @NonNull BlockState state,
            @NonNull BlockGetter world,
            @NonNull BlockPos pos,
            @NonNull CollisionContext context
    )
    {
        return Shape;
    }
    //Kinetic
    @Override
    public boolean hasShaftTowards(@NonNull LevelReader world,@NonNull BlockPos pos,BlockState state,@NonNull Direction face) {
        return face.getAxis() == state.getValue(AXIS);
    }
    @Override
    public Direction.@NonNull Axis getRotationAxis(@NonNull BlockState state)
    {
        return state.getValue(AXIS);
    }
    //Gauge
    public boolean shouldRenderHeadOnFace(Level world, BlockPos pos, BlockState state, Direction face)
    {
        return face.getAxis() != state.getValue(AXIS) && face.getAxis() != Direction.Axis.Y;
    }
    //Entity
    @Override
    public @NonNull Class<Entity_Ice_Maker> getBlockEntityClass()
    {
        return Entity_Ice_Maker.class;
    }

    @Override
    public @NonNull BlockEntityType<Entity_Ice_Maker> getBlockEntityType()
    {
        return EntityTypes.ICE_MAKER_BLOCK_ENTITY_TYPE;
    }
}
