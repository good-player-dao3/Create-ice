package create_ice.Nodes.Groups;

import create_ice.CreateIce;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Groups {
    public static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(),
            CreateIce.id("tab")
    );

    public static final CreativeModeTab CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.ICE))
            .title(Component.translatable("creativeTab.create-ice"))
            .build();

    public static void addItem(Item... item)
    {
        CreativeModeTabEvents.modifyOutputEvent(TAB_KEY).register(
                tab -> {
                    for(Item i : item)
                        tab.accept(i);
                }
        );
    }

    public static void initialize()
    {
        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                TAB_KEY,
                CREATIVE_TAB
        );
    }
}
