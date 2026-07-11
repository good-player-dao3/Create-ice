package create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge;

import com.zurrtum.create.client.foundation.ponder.CreateSceneBuilder;
import com.zurrtum.create.client.ponder.api.element.ElementLink;
import com.zurrtum.create.client.ponder.api.element.WorldSectionElement;
import com.zurrtum.create.client.ponder.api.scene.SceneBuilder;
import com.zurrtum.create.client.ponder.api.scene.SceneBuildingUtil;
import com.zurrtum.create.client.ponder.api.scene.Selection;
import create_ice.Nodes.Blocks.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class Industrial_Sponge_PonderAnimation
{
    public static void PonderAnimation_1(SceneBuilder scene, SceneBuildingUtil util)
    {
        scene.configureBasePlate(0,0,5);
        scene.world().showSection(util.select().everywhere(),Direction.UP);
        scene.title("industrial_sponge_1","industrial_sponge_1");
        //
        scene.overlay()
                .showText(40)
                .placeNearTarget()
                .sharedText("industrial_sponge_1.1")
                .pointAt(util.vector().of(1.5,1.5,1.5));
        scene.idle(60);

        scene.world().setBlock(
                new BlockPos(1,1,1),
                AllBlocks.INDUSTRIAL_SPONGE.defaultBlockState().setValue(
                        Industrial_Sponge.WET,
                        true
                ),
                false
        );
        scene.overlay()
                .showText(40)
                .placeNearTarget()
                .sharedText("industrial_sponge_1.2")
                .pointAt(util.vector().of(1.5,1.5,1.5));
        scene.idle(60);
        //
        scene.markAsFinished();
    }
    public static void PonderAnimation_3(SceneBuilder Builder, SceneBuildingUtil util)
    {
        CreateSceneBuilder scene = new CreateSceneBuilder(Builder);
        scene.configureBasePlate(0,0,5);
        scene.world().showSection(
                util.select().everywhere()
                        .substract(util.select().fromTo(2,1,2,2,1,4)),
                Direction.UP
        );
        scene.title("industrial_sponge_2","industrial_sponge_2");

        Selection section = util.select().fromTo(2,1,2,2,1,4);
        //

        ElementLink<WorldSectionElement> E = scene.world().showIndependentSection(section,Direction.DOWN);
        scene.world().moveSection(E,new Vec3(-1,0,0),0);
        scene.idle(10);

        scene.overlay()
                .showText(15)
                .placeNearTarget()
                .sharedText("industrial_sponge_3.1")
                .pointAt(util.vector().of(1.5,1.5,2.5));
        scene.idle(20);
        scene.overlay()
                .showText(20)
                .placeNearTarget()
                .sharedText("industrial_sponge_3.2")
                .pointAt(util.vector().of(1.5,1.5,2.5));
        scene.idle(25);
        //
        scene.world().moveSection(E,new Vec3(0,0,-1),20);

        scene.world().modifyKineticSpeed(
                util.select().fromTo(0,1,3,1,1,3),
                f -> -32f
        );
        scene.idle(10);
        scene.world().setBlock(
                new BlockPos(3,1,1),
                Blocks.AIR.defaultBlockState(),
                true
        );
        scene.idle(10);
        //
        scene.world().modifyKineticSpeed(
                util.select().fromTo(0,1,3,1,1,3),
                f -> 0f
        );

        scene.overlay()
                .showText(20)
                .placeNearTarget()
                .sharedText("industrial_sponge_3.3")
                .pointAt(util.vector().of(1.5,1.5,2.5));
        scene.idle(30);
        scene.overlay()
                .showText(20)
                .placeNearTarget()
                .sharedText("industrial_sponge_3.4")
                .pointAt(util.vector().of(1.5,1.5,2.5));
        scene.idle(25);

        scene.markAsFinished();
    }
}
