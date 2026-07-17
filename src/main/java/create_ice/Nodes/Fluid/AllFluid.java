package create_ice.Nodes.Fluid;

import com.zurrtum.create.AllFluidItemInventory;
import com.zurrtum.create.infrastructure.fluids.BucketFluidInventory;
import com.zurrtum.create.infrastructure.fluids.FlowableFluid;
import com.zurrtum.create.infrastructure.fluids.FluidEntry;
import create_ice.CreateIce;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

public class AllFluid {
    public static final FlowableFluid POWDER_SNOW_FLUID = register("powder_snow_fluid");

    public static void initialize()
    {
        AllFluidItemInventory.ALL.put(
                Items.POWDER_SNOW_BUCKET,
                new AllFluidItemInventory.Entry(BucketFluidInventory::new)
        );
    }

    private static FlowableFluid register(String name) {
        Identifier id = CreateIce.id(name);
        ResourceKey<Fluid> still_key = ResourceKey.create(Registries.FLUID, id);
        ResourceKey<Fluid> flowing_key = ResourceKey.create(Registries.FLUID, id.withPrefix("flowing_"));
        FluidEntry entry = new FluidEntry();
        entry.still = new FlowableFluid.Still(entry);
        entry.flowing = new FlowableFluid.Flowing(entry);
        Registry.register(BuiltInRegistries.FLUID, still_key, entry.still);
        Registry.register(BuiltInRegistries.FLUID, flowing_key, entry.flowing);
        return entry.still;
    }
}
