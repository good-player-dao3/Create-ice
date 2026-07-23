package create_ice.Nodes.Items;

import create_ice.CreateIce;
import create_ice.Nodes.Groups.Groups;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class AllItem {
    public static final Item ICE_BRICK = register(
            CreateIce.itemId("ice_brick"),
            Item::new,
            new Item.Properties()
    );

    public static void initialize()
    {

    }

    private static Item register(ResourceKey<Item> itemKey,Function<Item.Properties, Item> itemFactory,Item.Properties settings)
    {
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM,itemKey,item);
        Groups.addItem(item);
        return item;
    }
}
