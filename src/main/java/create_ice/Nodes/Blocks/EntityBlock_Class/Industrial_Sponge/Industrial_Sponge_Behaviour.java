package create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge;

import com.zurrtum.create.api.behaviour.movement.MovementBehaviour;
import com.zurrtum.create.content.contraptions.behaviour.MovementContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class Industrial_Sponge_Behaviour extends MovementBehaviour
{
    //data
    /**吸水半径，切比雪夫距离*/
    public static final int R = 3;
    //func
    private BlockPos lastPos = BlockPos.ZERO;

    private static BlockPos toBlockPos(Vec3 pos)
    {
        return new BlockPos((int)pos.x,(int)pos.y,(int)pos.z);
    }

    @Override
    public void tick(MovementContext context)
    {
        if(context.position == null || context.blockEntityData == null || context.world.isClientSide())
            return;
        //
        BlockPos newPos = toBlockPos(context.position);
        long amount = context.blockEntityData.getLong("amount").orElse(0L);
        if(
                !Objects.equals(this.lastPos,newPos) &&
                amount < Entity_Industrial_Sponge.MAX_FLUID_AMOUNT
        )
        {
            //
//            CreateIce.LOGGER.info("Entity data "+context.blockEntityData.toString());
//            CreateIce.LOGGER.info("Entity temporary data "+context.temporaryData);
//            CreateIce.LOGGER.info("Entity temporary data "+context.data);
            //
            boolean flag = true;
            for(int x = newPos.getX()-R;x <= newPos.getX()+R && flag;x++)
            {
                for(int y = newPos.getY()-R;y <= newPos.getY()+R && flag;y++)
                {
                    for(int z = newPos.getZ()-R;z <= newPos.getZ()+R && flag;z++)
                    {
                       BlockPos p = new BlockPos(x,y,z);
                       if(context.world.getFluidState(p).is(Fluids.WATER))
                       {
                           context.world.setBlock(
                                   p,
                                   Blocks.AIR.defaultBlockState(),
                                   3
                           );
                           amount = Math.min(amount+FluidConstants.BLOCK,Entity_Industrial_Sponge.MAX_FLUID_AMOUNT);
                           if(amount == Entity_Industrial_Sponge.MAX_FLUID_AMOUNT)
                               flag = false;
                       }
                    }
                }
            }
            //
            context.blockEntityData.putLong("amount",amount);
        }
        this.lastPos = newPos;
    }
}
