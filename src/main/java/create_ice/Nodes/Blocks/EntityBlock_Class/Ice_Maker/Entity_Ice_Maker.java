package create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker;

import com.zurrtum.create.client.api.goggles.IHaveGoggleInformation;
import com.zurrtum.create.content.kinetics.base.KineticBlockEntity;
import com.zurrtum.create.content.processing.basin.BasinBlockEntity;
import com.zurrtum.create.infrastructure.fluids.FluidStack;
import create_ice.Nodes.Blocks.EntityBlock_Class.EntityTypes;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class Entity_Ice_Maker extends KineticBlockEntity implements IHaveGoggleInformation
{
    public Entity_Ice_Maker(BlockPos pos,BlockState state)
    {
        super(EntityTypes.ICE_MAKER_BLOCK_ENTITY_TYPE,pos,state);
    }

    //Information
    /**制作时间*/
    public float time;
    public boolean stop = true;

    protected float default_MakeTime()
    {
        return 2*20;
    }

    protected float addTime()
    {
        return Math.abs(this.speed/255f*1);
    }

    public float getDialTarget()
    {
        //System.out.println("L"+this.time+" / "+default_MakeTime());
        return this.time/default_MakeTime();
    }

    @Override
    public void write(ValueOutput view, boolean clientPacket)
    {
        view.putFloat("time",time);
        super.write(view,clientPacket);
    }

    @Override
    public void read(ValueInput view,boolean clientPacket)
    {
        this.time = view.getFloatOr("time",0);
        super.read(view,clientPacket);
    }
    //Ice
    @Override
    public void tick()
    {
        super.tick();
        //
        if(this.level == null || this.level.isClientSide() || addTime() == 0)
            return;
        this.stop = true;
        BlockPos pos = this.getBlockPos();
        BlockPos pos_basin = pos.below();

        BasinBlockEntity basin = (BasinBlockEntity) this.level.getBlockEntity(pos_basin);
        if(basin instanceof BasinBlockEntity)
        {
            int waterSlot = -1;
            FluidStack fluidStack = null;
            for(int i = 0;i < basin.fluidCapability.size();i++)
            {
                fluidStack = basin.fluidCapability.getStack(i);
                if(fluidStack.isOf(Fluids.WATER) && fluidStack.getAmount() >= FluidConstants.BUCKET)
                {
                    waterSlot = i;
                    break;
                }
            }

            if(waterSlot == -1 && this.time > 0)
            {
                this.time = 0;
            }
            else if(waterSlot != -1)
            {
                int itemSlot = -1;
                ItemStack itemStack = null;
                for(int i = 0;i < basin.itemCapability.getContainerSize();i++)
                {
                    itemStack = basin.itemCapability.getItem(i);
                    if(itemStack.is(Items.ICE))
                    {
                        itemSlot = itemStack.getCount() < Items.ICE.getDefaultMaxStackSize()?i:-1;
                        break;
                    }
                    else if(itemStack.isEmpty())
                    {
                        itemSlot = i;
                        break;
                    }
                }

                if(itemSlot != -1)
                {
                    this.time+=addTime();
                    this.stop = false;
                    //Over
                    if(this.time >= default_MakeTime())
                    {
                        //item
                        if(itemStack.isEmpty())
                            itemStack = Items.ICE.getDefaultInstance().copy();
                        else
                            itemStack.setCount(itemStack.getCount()+1);
                        basin.itemCapability.setItem(itemSlot,itemStack);
                        //fluid
                        fluidStack.setAmount(fluidStack.getAmount()-(int)FluidConstants.BUCKET);
                        basin.fluidCapability.setStack(waterSlot,new FluidStack(Fluids.WATER,0));
                        //time
                        this.time-=default_MakeTime();
                    }
                    this.setChanged();
                }
            }
        }
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        this.sendData();
    }
}
