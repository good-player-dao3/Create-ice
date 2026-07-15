package create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker;

import com.zurrtum.create.client.foundation.ponder.CreateSceneBuilder;
import com.zurrtum.create.client.ponder.api.scene.SceneBuilder;
import com.zurrtum.create.client.ponder.api.scene.SceneBuildingUtil;
import com.zurrtum.create.content.kinetics.belt.transport.TransportedItemStack;
import com.zurrtum.create.content.logistics.depot.DepotBlockEntity;
import com.zurrtum.create.content.processing.basin.BasinBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;

public class Ice_Maker_PonderAnimation
{
    public static void PonderAnimation_1(SceneBuilder Builder, SceneBuildingUtil util)
    {
        CreateSceneBuilder scene = new CreateSceneBuilder(Builder);
        scene.configureBasePlate(0,0,5);
        scene.title("ice_maker_1","ice_maker_1");
        //1
        scene.world().showSection(
                util.select().fromTo(0,0,0,4,0,4)
                        .add(util.select().fromTo(0,1,0,4,1,4))
                        .add(util.select().position(2,2,2)),
                Direction.UP
        );
        scene.overlay()
                .showText(40)
                .placeNearTarget()
                .sharedText("ice_maker_1.1")
                .pointAt(util.vector().of(2.5,2.5,2.5));
        scene.idle(60);
        //2
        scene.world().showSection(
                util.select().fromTo(1,2,0,2,2,1),
                Direction.DOWN
        );
        scene.overlay()
                .showText(40)
                .placeNearTarget()
                .sharedText("ice_maker_1.2")
                .pointAt(util.vector().of(2.5,2.5,2.5));
        scene.idle(15);

        scene.world().modifyKineticSpeed(
                util.select().fromTo(1,2,0,1,2,1),
                f -> 32f
        );
        scene.world().modifyKineticSpeed(
                util.select().position(2,2,1),
                f -> -32f
        );

        scene.idle(85);

//        scene.world().modifyKineticSpeed(
//                util.select().fromTo(1,2,0,1,2,1),
//                f -> 0f
//        );
//        scene.world().modifyKineticSpeed(
//                util.select().position(2,2,1),
//                f -> 0f
//        );
        //3
        scene.world().showSection(
                util.select().fromTo(2,3,1,2,3,2),
                Direction.DOWN
        );
        scene.overlay()
                .showText(30)
                .placeNearTarget()
                .sharedText("ice_maker_1.3")
                .pointAt(util.vector().of(2.5,3.5,2.5));
        scene.idle(35);

        scene.world().modifyKineticSpeed(
                util.select().fromTo(2,3,1,2,3,2),
                f -> 32f
        );

        scene.overlay()
                .showText(30)
                .placeNearTarget()
                .sharedText("ice_maker_1.4")
                .pointAt(util.vector().of(2.5,3.5,2.5));

        for(int i = 0;i < 40;i++)
        {
            float finalI = i;
            scene.world().modifyBlockEntityNBT(util.select().position(2,3,2),
                    Entity_Ice_Maker.class,
                    nbt -> nbt.putFloat("time",finalI)
            );
            scene.idle(1);
        }

        scene.world().modifyBlockEntity(
                new BlockPos(2,2,2),
                BasinBlockEntity.class,
                be -> {
                    be.itemCapability.setItem(0,Items.ICE.getDefaultInstance());
                }
        );

        scene.world().modifyBlockEntity(
                new BlockPos(1,1,2),
                DepotBlockEntity.class,
                be -> {
                    be.setHeldItem(new TransportedItemStack(Items.ICE.getDefaultInstance()));
                }
        );
        //
        scene.markAsFinished();
    }
}
