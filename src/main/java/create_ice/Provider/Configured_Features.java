package create_ice.Provider;

import create_ice.CreateIce;
import create_ice.Nodes.Blocks.AllBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class Configured_Features
{
    private static final BlockState STATE = AllBlocks.METHANE_ICE.defaultBlockState();

    public static final List<OreConfiguration.TargetBlockState> CONFIG = List.of(
        OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),STATE),
        OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),STATE)
    );

    public static final ResourceKey<ConfiguredFeature<?,?>> CONFIGURED_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    CreateIce.id("methane_ice_vein")
            );

    public static void configure(BootstrapContext<ConfiguredFeature<?,?>> context)
    {
        context.register(
                CONFIGURED_KEY,
                new ConfiguredFeature<>(
                        Feature.ORE,
                        new OreConfiguration(CONFIG,32)
                )
        );
    }
}
