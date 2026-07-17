package create_ice.client.Nodes.Fluid;

import create_ice.Nodes.Fluid.AllFluid;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;

public class Powder_Snow_Fluid {
    public static void Registry()
    {
        FluidRenderingRegistry.register(
                AllFluid.POWDER_SNOW_FLUID,
                new FluidModel.Unbaked(
                        new Material(Identifier.withDefaultNamespace("block/powder_snow")),
                        new Material(Identifier.withDefaultNamespace("block/powder_snow")),
                        new Material(Identifier.withDefaultNamespace("block/powder_snow")),
                        state -> 0xFFFFFF
                )
        );
    }
}
