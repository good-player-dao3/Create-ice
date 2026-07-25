package create_ice.mixin.Block;

import com.zurrtum.create.AllFluids;
import com.zurrtum.create.content.fluids.potion.PotionFluidHandler;
import com.zurrtum.create.content.fluids.spout.FillingBySpout;
import com.zurrtum.create.content.fluids.spout.SpoutBlockEntity;
import com.zurrtum.create.infrastructure.fluids.FluidStack;
import create_ice.Nodes.Entity.Effect.Effects;
import create_ice.Nodes.Fluid.AllFluid;
import create_ice.Nodes.Items.AllItem;
import create_ice.Nodes.Items.Item_Class.SnowballItem_more;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(SpoutBlockEntity.class)
public class mixin_SpoutBlockEntity
{
    @Redirect(
            method = "onItemReceived",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/zurrtum/create/content/fluids/spout/FillingBySpout;getRequiredAmountForItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lcom/zurrtum/create/infrastructure/fluids/FluidStack;)I"
            )
    )
    public int getRequiredAmountForItem_1(ServerLevel world, ItemStack stack, FluidStack availableFluid)
    {
        return getRequiredAmountForItem(world,stack,availableFluid);
    }
    @Redirect(
            method = "whenItemHeld",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/zurrtum/create/content/fluids/spout/FillingBySpout;fillItem(Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/ItemStack;Lcom/zurrtum/create/infrastructure/fluids/FluidStack;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    public ItemStack fillItem(ServerLevel world, int requiredAmount, ItemStack stack, FluidStack availableFluid)
    {
        List<MobEffectInstance> list = new java.util.ArrayList<>(stack.getOrDefault(SnowballItem_more.ADD_TYPE,List.of()));
        ItemStack out = FillingBySpout.fillItem(world,requiredAmount,stack,availableFluid);
        if(out.is(AllItem.SNOWBALL_MORE) || out.is(Items.SNOWBALL))
        {
            if(availableFluid.isOf(AllFluid.POWDER_SNOW_FLUID))
            {
                var effect = new MobEffectInstance(
                        Effects.COLD,
                        20*20,
                        0,
                        false,
                        false,
                        true
                );
                System.out.println(effect);
                list.addLast(effect);
                out.set(SnowballItem_more.ADD_TYPE,list);
            }
            else if(availableFluid.isOf(AllFluids.POTION))
            {
                ItemStack point = PotionFluidHandler.fillBottle(Items.POTION.getDefaultInstance(),availableFluid);
                var contents = point.get(DataComponents.POTION_CONTENTS);
                if(contents != null && contents.hasEffects())
                {
                    for(MobEffectInstance e:contents.getAllEffects())
                    {
                        boolean flag = true;
                        for(MobEffectInstance l:list)
                        {
                            if(l.getEffect().equals(e.getEffect()))
                            {
                                flag = false;
                                break;
                            }
                        }
                        if(flag)
                        {
                            list.add(new MobEffectInstance(e));
                        }
                    }
                    out.set(SnowballItem_more.ADD_TYPE,list);
                }
            }
            System.out.println("Out "+out+" with "+out.getOrDefault(SnowballItem_more.ADD_TYPE,List.of()));
        }
        return out;
    }

    @Redirect(
            method = "whenItemHeld",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/zurrtum/create/content/fluids/spout/FillingBySpout;getRequiredAmountForItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lcom/zurrtum/create/infrastructure/fluids/FluidStack;)I"
            )
    )
    public int getRequiredAmountForItem_2(ServerLevel world, ItemStack stack, FluidStack availableFluid)
    {
        return getRequiredAmountForItem(world,stack,availableFluid);
    }


    @Unique
    private static int getRequiredAmountForItem(ServerLevel world, ItemStack stack, FluidStack availableFluid)
    {
        if(stack.is(AllItem.SNOWBALL_MORE) || stack.is(Items.SNOWBALL))
        {
            List<MobEffectInstance> list = stack.getOrDefault(SnowballItem_more.ADD_TYPE,List.of());
            System.out.println("Stack "+stack+" with "+list);
            if(availableFluid.isOf(AllFluid.POWDER_SNOW_FLUID))
            {
                if(!list.isEmpty())
                {
                    for(MobEffectInstance e:list)
                    {
                        if(e.getEffect().equals(Effects.COLD))
                        {
                            return -1;
                        }
                    }
                }
            }
            else if(availableFluid.isOf(AllFluids.POTION))
            {
                ItemStack point = PotionFluidHandler.fillBottle(Items.POTION.getDefaultInstance(),availableFluid);
                var contents = point.get(DataComponents.POTION_CONTENTS);
                if(contents != null && contents.hasEffects())
                {
                    boolean flag = true;
                    for(MobEffectInstance e:contents.getAllEffects())
                    {
                        boolean flag2 = true;
                        for(MobEffectInstance l:list)
                        {
                            if(l.getEffect().equals(e.getEffect()))
                            {
                                flag2 = false;
                                break;
                            }
                        }
                        if(flag2)
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        return -1;
                }
            }
        }
        return FillingBySpout.getRequiredAmountForItem(world,stack,availableFluid);
    }
}
