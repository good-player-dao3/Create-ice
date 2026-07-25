package create_ice.Nodes.Items;

import create_ice.CreateIce;
import create_ice.Nodes.Groups.Groups;
import create_ice.Nodes.Items.Item_Class.SnowballItem_more;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.List;
import java.util.function.Function;

public class AllItem {
    public static final Item ICE_BRICK = register(
            CreateIce.itemId("ice_brick"),
            Item::new,
            new Item.Properties()
    );

    public static final Item SNOWBALL_MORE = register(
            CreateIce.itemId("snowball_more"),
            SnowballItem_more::new,
            new Item.Properties()
                    .component(
                            SnowballItem_more.ADD_TYPE,
                            List.of()
                    )
                    .component(
                            DataComponents.MAX_STACK_SIZE,
                            16
                    )
    );

    public static void initialize()
    {
        ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
            //snowball_more
            List<MobEffectInstance> effects = stack.get(SnowballItem_more.ADD_TYPE);
            if(effects != null && !effects.isEmpty())
            {
                tooltip.addLast(Component.translatable("create-ice.tooltip.snowball_more.effects"));
                for(MobEffectInstance e:effects)
                {
                    var effect = e.getEffect().value();
                    tooltip.addLast(
                            effect.getDisplayName().copy()
                                    .append(" ")
                                    .append(Component.translatable("enchantment.level."+(e.getAmplifier()+1)))
                                    .append(" (")
                                    .append(MobEffectUtil.formatDuration(e,1f,20))
                                    .append(")")
                                    .withStyle(
                                            effect.getCategory() == MobEffectCategory.HARMFUL?
                                                    ChatFormatting.RED:
                                                    (
                                                            effect.getCategory() == MobEffectCategory.BENEFICIAL?
                                                                    ChatFormatting.BLUE:
                                                                    ChatFormatting.GRAY
                                                    )
                                    )
                    );
                }
            }
        });
        DispenserBlock.registerProjectileBehavior(SNOWBALL_MORE);
    }

    private static Item register(ResourceKey<Item> itemKey,Function<Item.Properties, Item> itemFactory,Item.Properties settings)
    {
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM,itemKey,item);
        Groups.addItem(item);
        return item;
    }
}
