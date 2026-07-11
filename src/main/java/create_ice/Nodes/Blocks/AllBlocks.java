package create_ice.Nodes.Blocks;


import com.zurrtum.create.api.behaviour.movement.MovementBehaviour;
import create_ice.CreateIce;
import create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge.Industrial_Sponge;
import create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge.Industrial_Sponge_Behaviour;
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

    public static void initialize()
    {
        MovementBehaviour.REGISTRY.register(
                INDUSTRIAL_SPONGE,
                new Industrial_Sponge_Behaviour()
        );
    }
}
