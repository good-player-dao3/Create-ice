package create_ice.client.Nodes;

import create_ice.client.Nodes.Fluid.Powder_Snow_Fluid;
import create_ice.client.Nodes.Visual.Index;

public class Nodes
{
    public static void initialize()
    {
        Index.initialize();
        Powder_Snow_Fluid.Registry();
    }
}
