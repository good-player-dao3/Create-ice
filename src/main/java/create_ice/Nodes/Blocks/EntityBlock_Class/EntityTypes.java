package create_ice.Nodes.Blocks.EntityBlock_Class;

import create_ice.CreateIce;
import create_ice.Nodes.Blocks.AllBlocks;
import create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker.Entity_Ice_Maker;
import create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge.Entity_Industrial_Sponge;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;


public class EntityTypes {
    public static final BlockEntityType<Entity_Industrial_Sponge> INDUSTRIAL_SPONGE_BLOCK_ENTITY_TYPE =
            register(
                    "industrial_sponge_block_entity",
                    Entity_Industrial_Sponge::new,
                    AllBlocks.INDUSTRIAL_SPONGE
            );
    public static final BlockEntityType<Entity_Ice_Maker> ICE_MAKER_BLOCK_ENTITY_TYPE =
            register(
                    "ice_maker_block_entity",
                    Entity_Ice_Maker::new,
                    AllBlocks.ICE_MAKER
            );

    //
    public static void initialize()
    {

    }
    //api
    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                CreateIce.id(name),
                FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
