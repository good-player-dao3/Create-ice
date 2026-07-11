package create_ice.Nodes.Blocks.EntityBlock_Class.Pouder;

import com.zurrtum.create.client.ponder.api.registration.PonderPlugin;
import com.zurrtum.create.client.ponder.api.registration.PonderSceneRegistrationHelper;
import create_ice.CreateIce;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class Ponder_Plugin implements PonderPlugin
{
    @Override
    public @NonNull String getModId()
    {
        return CreateIce.MOD_ID;
    }

    @Override
    public void registerScenes(@NonNull PonderSceneRegistrationHelper<Identifier> helper)
    {
        Ponder_Screen.register(helper);
    }
}