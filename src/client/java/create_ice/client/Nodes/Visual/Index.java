package create_ice.client.Nodes.Visual;

import com.zurrtum.create.client.AllBlockEntityRenders;
import create_ice.client.Nodes.Visual.Ice_Maker.Ice_Maker_Render;
import create_ice.client.Nodes.Visual.Ice_Maker.Ice_Maker_Visual;

import static create_ice.Nodes.Blocks.EntityBlock_Class.EntityTypes.ICE_MAKER_BLOCK_ENTITY_TYPE;

public class Index
{
    public static void initialize()
    {
        AllBlockEntityRenders.visual(
                ICE_MAKER_BLOCK_ENTITY_TYPE,
                Ice_Maker_Render::new,
                Ice_Maker_Visual::new
        );
    }
}
