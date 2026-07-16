package create_ice.Nodes.Blocks;


import com.zurrtum.create.api.behaviour.movement.MovementBehaviour;
import com.zurrtum.create.api.stress.BlockStressValues;
import create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker.Ice_Maker;
import create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge.Industrial_Sponge;
import create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge.Industrial_Sponge_Behaviour;
import create_ice.Nodes.Groups.GroupBlock;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class AllBlocks {

    public static final Industrial_Sponge INDUSTRIAL_SPONGE = BaseBlock.register(
            "industrial_sponge",
            Industrial_Sponge::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(0.6F)
                    .sound(SoundType.SPONGE),
            true
    ).addGroup();

    public static final Ice_Maker ICE_MAKER = BaseBlock.register(
            "ice_maker",
            Ice_Maker::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE)
                    .mapColor(MapColor.PODZOL)
                    .noOcclusion(),
            true
    ).addGroup();

    public static final Block METHANE_ICE = BaseBlock.register(
            "methane_ice",
            GroupBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE),
            true
    ).addGroup();

    public static void initialize()
    {
        //industrial sponge
        MovementBehaviour.REGISTRY.register(
                INDUSTRIAL_SPONGE,
                new Industrial_Sponge_Behaviour()
        );
        //ice maker
        BlockStressValues.IMPACTS.register(
                ICE_MAKER,
                () -> 8f
        );
        //methane ice
        FuelValueEvents.BUILD.register(
                (builder,context) -> builder.add(METHANE_ICE.asItem(),32*10*20)
        );
    }
}
