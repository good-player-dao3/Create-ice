package create_ice.Nodes.Blocks;

import com.zurrtum.create.infrastructure.fluids.FlowableFluid;
import com.zurrtum.create.infrastructure.fluids.FluidBlock;
import create_ice.CreateIce;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;

import static create_ice.CreateIce.itemId;


public abstract class BaseBlock {
    public static <T extends FluidBlock> T register(
            FlowableFluid fluid,
            BiFunction<FlowableFluid, BlockBehaviour.Properties, T> factory,
            BlockBehaviour.Properties settings
    ) {
        T block = register(
                BuiltInRegistries.FLUID.getKey(fluid).getPath(),
                blockSettings -> factory.apply(fluid, blockSettings),
                settings,
                false
        );
        fluid.getEntry().block = block;
        return block;
    }

    public static <T extends Block> T register(
            String id,
            Function<BlockBehaviour.Properties, T> factory,
            BlockBehaviour.Properties settings,
            boolean useItem
    ) {
        ResourceKey<Block> key = CreateIce.blockId(id);

        T block = factory.apply(settings.setId(key));

        if(useItem)
        {
            ResourceKey<Item> itemKey = itemId(id);
            Registry.register(
                    BuiltInRegistries.ITEM,
                    itemKey,
                    new BlockItem(block,new Item.Properties().setId(itemKey).useBlockDescriptionPrefix())
            );
        }

        return Registry.register(BuiltInRegistries.BLOCK,key,block);
    }
}
