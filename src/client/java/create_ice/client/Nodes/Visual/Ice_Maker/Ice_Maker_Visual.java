package create_ice.client.Nodes.Visual.Ice_Maker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zurrtum.create.catnip.data.Couple;
import com.zurrtum.create.catnip.data.Iterate;
import com.zurrtum.create.client.AllPartialModels;
import com.zurrtum.create.client.content.kinetics.base.ShaftVisual;
import com.zurrtum.create.client.flywheel.api.instance.Instance;
import com.zurrtum.create.client.flywheel.api.instance.Instancer;
import com.zurrtum.create.client.flywheel.api.visual.DynamicVisual;
import com.zurrtum.create.client.flywheel.api.visualization.VisualizationContext;
import com.zurrtum.create.client.flywheel.lib.instance.FlatLit;
import com.zurrtum.create.client.flywheel.lib.instance.InstanceTypes;
import com.zurrtum.create.client.flywheel.lib.instance.TransformedInstance;
import com.zurrtum.create.client.flywheel.lib.model.Models;
import com.zurrtum.create.client.flywheel.lib.transform.TransformStack;
import com.zurrtum.create.client.flywheel.lib.visual.SimpleDynamicVisual;
import create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker.Entity_Ice_Maker;
import create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker.Ice_Maker;
import net.minecraft.core.Direction;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Ice_Maker_Visual extends ShaftVisual<Entity_Ice_Maker> implements SimpleDynamicVisual
{
    protected final ArrayList<DialFace> faces = new ArrayList<>(2);

    protected final PoseStack ms = new PoseStack();

    public Ice_Maker_Visual(VisualizationContext context,Entity_Ice_Maker blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        Ice_Maker gaugeBlock = (Ice_Maker) blockState.getBlock();

        Instancer<TransformedInstance> dialModel = instancerProvider().instancer(
                InstanceTypes.TRANSFORMED,
                Models.chunkPartial(AllPartialModels.GAUGE_DIAL)
        );
        Instancer<TransformedInstance> headModel = getHeadModel();

        var msr = TransformStack.of(ms);
        msr.translate(getVisualPosition());

        float progress = blockEntity.getDialTarget();

        System.out.println("Super Progress "+progress);

        for (Direction facing : Iterate.directions) {
            if (!gaugeBlock.shouldRenderHeadOnFace(level, pos, blockState, facing)) {
                continue;
            }

            DialFace face = makeFace(facing, dialModel, headModel);

            faces.add(face);

            face.setupTransform(msr, progress);
        }
    }

    @Override
    public void setSectionCollector(@NonNull SectionCollector sectionCollector) {
        int minX = 0;
        int minZ = 0;
        int maxX = 0;
        int maxZ = 0;
        for (DialFace face : faces) {
            switch (face.face) {
                case WEST -> minX = -1;
                case NORTH -> minZ = -1;
                case EAST -> maxX = 1;
                case SOUTH -> maxZ = 1;
            }
        }
        setSectionCollector(sectionCollector, minX, 0, minZ, maxX, 0, maxZ);
    }

    private DialFace makeFace(
            Direction face,
            Instancer<TransformedInstance> dialModel,
            Instancer<TransformedInstance> headModel
    ) {
        return new DialFace(face, dialModel.createInstance(), headModel.createInstance());
    }

    @Override
    public void beginFrame(DynamicVisual.@NonNull Context ctx) {
//        if (Mth.equal(blockEntity.prevDialState, blockEntity.dialState)) {
//            return;
//        }
//        float progress = blockEntity.getDialTarget();
//
//        //System.out.println("beginFrame Progress "+progress);
//
//        var msr = TransformStack.of(ms);
//
//        for (DialFace faceEntry : faces) {
//            faceEntry.updateTransform(msr, progress);
//        }
    }

    @Override
    public void update(float partialTicks)
    {
        System.out.println("Update "+blockEntity.time);

        float progress = blockEntity.getDialTarget();

        var msr = TransformStack.of(ms);

        for (DialFace faceEntry : faces) {
            faceEntry.updateTransform(msr, progress);
        }
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);

        relight(faces.stream().flatMap(Couple::stream).toArray(FlatLit[]::new));
    }

    @Override
    protected void _delete() {
        super._delete();

        faces.forEach(DialFace::delete);
    }

    @Override
    public void collectCrumblingInstances(@NonNull Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        for (DialFace face : faces) {
            face.forEach(consumer);
        }
    }

    protected Instancer<TransformedInstance> getHeadModel()
    {
        return instancerProvider().instancer(
                InstanceTypes.TRANSFORMED,
                Models.chunkPartial(AllPartialModels.GAUGE_HEAD_SPEED)
        );
    }

    protected class DialFace extends Couple<TransformedInstance> {

        Direction face;

        public DialFace(Direction face, TransformedInstance first, TransformedInstance second) {
            super(first, second);
            this.face = face;
        }

        private void setupTransform(TransformStack<?> msr, float progress) {
            float dialPivot = 5.75f / 16;

            msr.pushPose();
            rotateToFace(msr);

            getSecond().setTransform(ms).setChanged();

            msr.translate(0, dialPivot, dialPivot).rotate((float) (Math.PI / 2 * -progress), Direction.EAST)
                    .translate(0, -dialPivot, -dialPivot);

            getFirst().setTransform(ms).setChanged();

            msr.popPose();
        }

        private void updateTransform(TransformStack<?> msr, float progress) {
            float dialPivot = 5.75f / 16;

            msr.pushPose();

            rotateToFace(msr).translate(0, dialPivot, dialPivot)
                    .rotate((float) (Math.PI / 2 * -progress), Direction.EAST).translate(0, -dialPivot, -dialPivot);

            getFirst().setTransform(ms).setChanged();

            msr.popPose();
        }

        protected TransformStack<?> rotateToFace(TransformStack<?> msr) {
            return msr.center().rotate((float) ((-face.toYRot() - 90) / 180 * Math.PI), Direction.UP).uncenter();
        }

        private void delete() {
            getFirst().delete();
            getSecond().delete();
        }
    }
}