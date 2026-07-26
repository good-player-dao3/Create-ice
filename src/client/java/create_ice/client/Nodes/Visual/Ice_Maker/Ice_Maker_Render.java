package create_ice.client.Nodes.Visual.Ice_Maker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zurrtum.create.catnip.data.Iterate;
import com.zurrtum.create.client.AllPartialModels;
import com.zurrtum.create.client.catnip.render.CachedBuffers;
import com.zurrtum.create.client.catnip.render.SuperByteBufferRenderState;
import com.zurrtum.create.client.flywheel.lib.model.baked.PartialModel;
import com.zurrtum.create.client.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker.Entity_Ice_Maker;
import create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker.Ice_Maker;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.UnknownNullability;
import org.joml.Quaternionf;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.zurrtum.create.client.content.kinetics.base.KineticBlockEntityRenderer.*;

public class Ice_Maker_Render<T extends Entity_Ice_Maker> implements BlockEntityRenderer<T, Ice_Maker_Render.RenderState>
{
    private final PartialModel model;

    public Ice_Maker_Render(BlockEntityRendererProvider.Context context)
    {
        this.model = AllPartialModels.GAUGE_HEAD_SPEED;
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void extractRenderState(
            T be,
            RenderState state,
            float tickProgress,
            Vec3 cameraPos,
            ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
    ) {
        Level level = SmartBlockEntityRenderer.extractBase(be, state, crumblingOverlay);
        if(level == null)
            return;
        BlockState blockState = level.getBlockState(state.blockPos);
        Direction.Axis axis = getRotationAxisOf(blockState);
        CardinalLighting cardinalLighting = SmartBlockEntityRenderer.getCardinalLighting(level);
        Ice_Maker block = (Ice_Maker)blockState.getBlock();
        //
        state.model = CachedBuffers.block(KINETIC_BLOCK, shaft(axis)).cardinalLighting(cardinalLighting)
                .light(state.lightCoords).color(getTintColor(be)).extractRenderState();
        state.angle = getRotateAngleWithoutBeOffset(axis, be, state, level);

        List<@Nullable Quaternionf> angles = new ArrayList<>(2);
        for (Direction facing : Iterate.directions) {
            if (block.shouldRenderHeadOnFace(level,state.blockPos,blockState, facing)) {
                angles.add(getUpRotateAngle(-facing.toYRot() - 90));
            }
        }
        if (angles.isEmpty()) {
            return;
        }
        //
        state.angles = angles;
        state.head = CachedBuffers.partial(model,blockState).cardinalLighting(cardinalLighting)
                .light(state.lightCoords).extractRenderState();
        state.dial = CachedBuffers.partial(AllPartialModels.GAUGE_DIAL,blockState).cardinalLighting(cardinalLighting)
                .light(state.lightCoords).extractRenderState();
        float progress = be.getDialTarget();
        state.rotate = new Quaternionf().setAngleAxis(Math.PI / 2 * -progress, 1, 0, 0);
    }

    @Override
    public void submit(
            RenderState state,
            PoseStack matrices,
            SubmitNodeCollector queue,
            CameraRenderState cameraState
    ) {
        if (state.angle != null) {
            matrices.pushPose();
            matrices.rotateAround(state.angle, 0.5f, 0.5f, 0.5f);
            state.model.submit(matrices, queue);
            matrices.popPose();
        } else {
            state.model.submit(matrices, queue);
        }
        if (state.angles != null) {
            if (state.rotate != null) {
                for (Quaternionf angle : state.angles) {
                    matrices.pushPose();
                    if (angle != null) {
                        matrices.rotateAround(angle, 0.5f, 0.5f, 0.5f);
                    }
                    state.head.submit(matrices, queue);
                    matrices.rotateAround(state.rotate, 0, 0.359375f, 0.359375f);
                    state.dial.submit(matrices, queue);
                    matrices.popPose();
                }
            } else {
                for (Quaternionf angle : state.angles) {
                    if (angle != null) {
                        matrices.pushPose();
                        matrices.rotateAround(angle, 0.5f, 0.5f, 0.5f);
                        state.head.submit(matrices, queue);
                        state.dial.submit(matrices, queue);
                        matrices.popPose();
                    } else {
                        state.head.submit(matrices, queue);
                        state.dial.submit(matrices, queue);
                    }
                }
            }
        }
    }

    public static class RenderState extends BlockEntityRenderState {
        public @UnknownNullability SuperByteBufferRenderState model;
        public @UnknownNullability SuperByteBufferRenderState head;
        public @UnknownNullability SuperByteBufferRenderState dial;
        public @Nullable Quaternionf angle;
        public @Nullable List<@Nullable Quaternionf> angles;
        public @Nullable Quaternionf rotate;
    }
}
