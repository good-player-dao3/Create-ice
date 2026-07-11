package create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge;

import com.zurrtum.create.api.behaviour.movement.MovementBehaviour;
import com.zurrtum.create.content.contraptions.behaviour.MovementContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;

public class Industrial_Sponge_Behaviour extends MovementBehaviour
{
    //data
    /**吸水半径，切比雪夫距离*/
    public static final int R = 3;
    //func
    @Override
    public void visitNewPosition(MovementContext context,@NonNull BlockPos pos)
    {
        if(context.position == null || context.blockEntityData == null || context.world.isClientSide())
            return;
        long amount = context.blockEntityData.getLong("amount").orElse(0L);
        if(amount < Entity_Industrial_Sponge.MAX_FLUID_AMOUNT)
        {
            boolean flag = true;
            for(int x = pos.getX()-R;x <= pos.getX()+R && flag;x++)
            {
                for(int y = pos.getY()-R;y <= pos.getY()+R && flag;y++)
                {
                    for(int z = pos.getZ()-R;z <= pos.getZ()+R && flag;z++)
                    {
                       BlockPos p = new BlockPos(x,y,z);
                       if(context.world.getFluidState(p).is(Fluids.WATER) && context.world.getBlockState(p).is(Blocks.WATER))
                       {
                           context.world.setBlock(
                                   p,
                                   Blocks.AIR.defaultBlockState(),
                                   3
                           );
                           amount = Math.min(amount+FluidConstants.BLOCK,Entity_Industrial_Sponge.MAX_FLUID_AMOUNT);

//                           if(!context.state.getValue(Industrial_Sponge.WET))
//                           {
//                               StructureTemplate.StructureBlockInfo info = contraption.getBlocks().get(context.localPos);
//                               if(info != null)
//                               {
//                                   context.state = context.state.setValue(Industrial_Sponge.WET,true);
//
//                                   contraption.getBlocks().put(
//                                           context.localPos,
//                                           new StructureTemplate.StructureBlockInfo(
//                                                   info.pos(),
//                                                   info.state().setValue(Industrial_Sponge.WET,true),
//                                                   info.nbt()
//                                           )
//                                   );
//
//                                   CreateIce.LOGGER.info("Set"+contraption.getBlocks().get(context.localPos));
//                               }
//                           }

                           if(amount == Entity_Industrial_Sponge.MAX_FLUID_AMOUNT)
                               flag = false;
                       }
                    }
                }
            }
            //
            context.blockEntityData.putLong("amount",amount);
        }
    }
}
