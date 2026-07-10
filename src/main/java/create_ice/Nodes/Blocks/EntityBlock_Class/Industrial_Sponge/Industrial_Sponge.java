package create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge;

import com.zurrtum.create.foundation.block.IBE;
import create_ice.Nodes.Blocks.EntityBlock_Class.EntityTypes;
import create_ice.Nodes.Groups.GroupBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jspecify.annotations.NonNull;

public class Industrial_Sponge extends Block implements IBE<Entity_Industrial_Sponge>,GroupBlock<Industrial_Sponge>
{
    public Industrial_Sponge(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WET,false));
    }

    //WET
    public static final BooleanProperty WET = BooleanProperty.create("wet");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder) {
        builder.add(WET);
    }

    public static void updateWet(BlockState state,boolean wet,Level level,BlockPos pos)
    {
        if(state.getValue(WET) != wet)
            level.setBlock(pos,state.setValue(Industrial_Sponge.WET,wet),3);
    }

    //EntityBlock
    @Override
    public @NonNull Class<Entity_Industrial_Sponge> getBlockEntityClass()
    {
        return Entity_Industrial_Sponge.class;
    }

    @Override
    public @NonNull BlockEntityType<Entity_Industrial_Sponge> getBlockEntityType()
    {
        return EntityTypes.INDUSTRIAL_SPONGE_BLOCK_ENTITY_TYPE;
    }
}
