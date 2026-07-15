package create_ice.client;

import com.zurrtum.create.client.ponder.foundation.PonderIndex;
import create_ice.Nodes.Blocks.EntityBlock_Class.Pouder.Ponder_Plugin;
import create_ice.client.Nodes.Nodes;
import net.fabricmc.api.ClientModInitializer;

public class CreateIceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient()
	{
		//Ponder
		PonderIndex.addPlugin(new Ponder_Plugin());

		Nodes.initialize();
	}
}