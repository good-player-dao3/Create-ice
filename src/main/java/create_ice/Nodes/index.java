package create_ice.Nodes;

import create_ice.Nodes.Blocks.AllBlocks;
import create_ice.Nodes.Blocks.EntityBlock_Class.EntityTypes;
import create_ice.Nodes.Entity.Effect.Effects;
import create_ice.Nodes.Fluid.AllFluid;
import create_ice.Nodes.Groups.Groups;
import create_ice.Nodes.Items.AllItem;

public class index {
    public static void initialize()
    {
        AllBlocks.initialize();
        AllFluid.initialize();
        EntityTypes.initialize();
        AllItem.initialize();
        Groups.initialize();
        Effects.initialize();
    }
}
