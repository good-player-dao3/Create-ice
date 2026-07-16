package create_ice.Provider;

import create_ice.CreateIce;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class Placed_Feature
{
    public static final ResourceKey<PlacedFeature> PLACED_KEY =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    CreateIce.id("methane_ice_placed")
            );

    public static final List<PlacementModifier> Modifiers = List.of(
            CountPlacement.of(16),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(
                    TrapezoidHeight.of(
                            VerticalAnchor.absolute(-8),
                            VerticalAnchor.absolute(64)
                    )
            )
    );

    public static void configure(BootstrapContext<PlacedFeature> context)
    {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(
                PLACED_KEY,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(Configured_Features.CONFIGURED_KEY),
                        Modifiers
                )
        );
    }
}
