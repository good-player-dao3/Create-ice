package create_ice.Nodes.Blocks.EntityBlock_Class.Pouder;

import com.zurrtum.create.client.ponder.api.registration.PonderSceneRegistrationHelper;
import create_ice.CreateIce;
import create_ice.Nodes.Blocks.EntityBlock_Class.Ice_Maker.Ice_Maker_PonderAnimation;
import create_ice.Nodes.Blocks.EntityBlock_Class.Industrial_Sponge.Industrial_Sponge_PonderAnimation;
import net.minecraft.resources.Identifier;

public class Ponder_Screen
{
    public static void register(PonderSceneRegistrationHelper<Identifier> helper)
    {
        helper.forComponents(CreateIce.id("industrial_sponge"))
                .addStoryBoard(
                        "industrial_sponge/industrial_sponge_1",
                        Industrial_Sponge_PonderAnimation::PonderAnimation_1
                )
                .addStoryBoard(
                        "industrial_sponge/industrial_sponge_3",
                        Industrial_Sponge_PonderAnimation::PonderAnimation_3
                );
        helper.forComponents(CreateIce.id("ice_maker"))
                .addStoryBoard(
                        "ice_maker/ice_maker_1",
                        Ice_Maker_PonderAnimation::PonderAnimation_1
                );
    }
}
