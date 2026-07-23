package create_ice.Nodes.Groups;

import create_ice.CreateIce;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Comparator;
import java.util.function.Predicate;

public class Groups {
    public static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(),
            CreateIce.id("tab")
    );

    public static final CreativeModeTab CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.ICE))
            .title(Component.translatable("creativeTab.create-ice"))
            .displayItems((parameters,context) ->
                parameters.holders()
                        .lookup(Registries.PAINTING_VARIANT)
                        .ifPresent(
                                paintings -> generatePresetPaintings(
                                        context,
                                        parameters.holders(),
                                        paintings,
                                        variant ->
                                                variant.is(PaintingVariantTags.PLACEABLE) &&
                                                variant.getRegisteredName().startsWith(CreateIce.MOD_ID),
                                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                                )
                        )
            )
            .build();
    //Paintings
    private static final Comparator<Holder<PaintingVariant>> PAINTING_COMPARATOR = Comparator.comparing(
            Holder::value, Comparator.comparingInt(PaintingVariant::area).thenComparing(PaintingVariant::width)
    );
    private static void generatePresetPaintings(
            final CreativeModeTab.Output output,
            final HolderLookup.Provider context,
            final HolderLookup.RegistryLookup<PaintingVariant> paintings,
            final Predicate<Holder<PaintingVariant>> filter,
            final CreativeModeTab.TabVisibility tabVisibility
    ) {
        paintings.listElements().filter(filter).sorted(PAINTING_COMPARATOR).forEach(painting -> {
            ItemStack stack = new ItemStack(Items.PAINTING);
            stack.set(DataComponents.PAINTING_VARIANT,painting);
            output.accept(stack, tabVisibility);
        });
    }
    //Other
    public static void addItem(ItemLike... item)
    {
        CreativeModeTabEvents.modifyOutputEvent(TAB_KEY).register(
                tab -> {
                    for(ItemLike i : item)
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
