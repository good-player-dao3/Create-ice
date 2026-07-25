package create_ice.Nodes.Entity.Effect;

import create_ice.CreateIce;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class Effects {
    public static final Holder<MobEffect> COLD =
            Registry.registerForHolder(
                    BuiltInRegistries.MOB_EFFECT,
                    CreateIce.id("cold"),
                    new Cold()
            );

    public static void initialize()
    {

    }
}
