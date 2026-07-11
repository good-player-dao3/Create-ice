package create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge;

import create_ice.Nodes.Blocks.EntityBlock_Class.EntityTypes;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class Entity_Industrial_Sponge extends BlockEntity implements SidedStorageBlockEntity {
    public Entity_Industrial_Sponge(BlockPos pos, BlockState state)
    {
        super(EntityTypes.INDUSTRIAL_SPONGE_BLOCK_ENTITY_TYPE,pos,state);
    }
    //Fluid

    /**方块容量上限*/
    public static final long MAX_FLUID_AMOUNT = 64*FluidConstants.BUCKET;

    @Override
    public Storage<FluidVariant> getFluidStorage(Direction side)
    {
        return this.fluidStorage;
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected @NonNull FluidVariant getBlankVariant()
        {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(@NonNull FluidVariant variant)
        {
            return MAX_FLUID_AMOUNT;
        }

        @Override
        protected void onFinalCommit()
        {
            updateState();
        }

        @Override
        protected boolean canInsert(FluidVariant variant)
        {
            return variant.isOf(Fluids.WATER);
        }
    };

    private void updateState()
    {
        Level level = this.getLevel();
        if(level == null || level.isClientSide())
            return;
        //
        BlockPos pos = this.getBlockPos();
        BlockState state = level.getBlockState(pos);
        Industrial_Sponge.updateWet(state,this.fluidStorage.getAmount() > 0,level,pos);
    }

    //NBT
    @Override
    protected void loadAdditional(ValueInput input) {
        this.fluidStorage.amount = Math.min(input.getLong("amount").orElse(0L),MAX_FLUID_AMOUNT);
        this.fluidStorage.variant = this.fluidStorage.amount == 0?
                input.read("variant",FluidVariant.CODEC).orElse(FluidVariant.blank()):
                FluidVariant.of(Fluids.WATER)
        ;
        if(this.getLevel() != null && !this.getLevel().isClientSide())
        {
            Industrial_Sponge.updateWet(
                    this.getLevel().getBlockState(this.getBlockPos()),
                    this.fluidStorage.getAmount() > 0,
                    this.getLevel(),
                    this.getBlockPos()
            );
        }
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putLong("amount",this.fluidStorage.amount);
        output.store("variant",FluidVariant.CODEC,this.fluidStorage.variant);
        super.saveAdditional(output);
    }
}
