package create_ice.Nodes;

import create_ice.Nodes.Blocks.AllBlocks;
import create_ice.Nodes.Blocks.EntityBlock_Class.EntityTypes;
import create_ice.Nodes.Groups.Groups;

public class index {
    public static void initialize()
    {
        AllBlocks.initialize();
        EntityTypes.initialize();
        Groups.initialize();
    }
}
