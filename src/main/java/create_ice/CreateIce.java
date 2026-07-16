package create_ice;

import create_ice.Nodes.index;
import create_ice.Provider.Placed_Feature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateIce implements ModInitializer {
	public static final String MOD_ID = "create-ice";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello world!");
		index.initialize();
		BiomeModifications.addFeature(
				BiomeSelectors.tag(BiomeTags.IS_DEEP_OCEAN),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				Placed_Feature.PLACED_KEY
		);
		LOGGER.info("Worldgen feature added to biome modifications.");
		LOGGER.info("Over!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static ResourceKey<Block> blockId(String path)
	{
		return ResourceKey.create(Registries.BLOCK,id(path));
	}

	public static ResourceKey<Item> itemId(String path)
	{
		return ResourceKey.create(Registries.ITEM,id(path));
	}
}
