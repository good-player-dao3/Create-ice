package create_ice.Nodes.Items.Item_Class;

import com.mojang.serialization.Codec;
import create_ice.CreateIce;
import create_ice.Nodes.Entity.Snowball_more;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class SnowballItem_more extends SnowballItem {
    public static final DataComponentType<List<MobEffectInstance>> ADD_TYPE = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            CreateIce.id("add"),
            DataComponentType
                    .<List<MobEffectInstance>>builder()
                    .persistent(Codec.list(MobEffectInstance.CODEC))
                    .build()
    );

    public SnowballItem_more(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(Snowball_more::new, serverLevel, itemStack, player, 0.0F, 1.5F, 1.0F);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NonNull Projectile asProjectile(final Level level, final Position position, final ItemStack itemStack, final Direction direction)
    {
        return new Snowball_more(level, position.x(), position.y(), position.z(), itemStack);
    }
}
