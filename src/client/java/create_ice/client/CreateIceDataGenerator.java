package create_ice.client;

import create_ice.Provider.Configured_Features;
import create_ice.Provider.Placed_Feature;
import create_ice.Provider.WorldgenProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class CreateIceDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(WorldgenProvider::new);
//		BiomeModifications.addFeature(
//				BiomeSelectors.tag(BiomeTags.IS_DEEP_OCEAN),
//				GenerationStep.Decoration.VEGETAL_DECORATION,
//				Placed_Feature.PLACED_KEY
//		);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder)
	{
		registryBuilder.add(Registries.CONFIGURED_FEATURE, Configured_Features::configure);
		registryBuilder.add(Registries.PLACED_FEATURE, Placed_Feature::configure);
	}
}
