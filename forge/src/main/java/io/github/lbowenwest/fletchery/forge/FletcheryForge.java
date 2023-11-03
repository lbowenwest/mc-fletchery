package io.github.lbowenwest.fletchery.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.lbowenwest.fletchery.Fletchery;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Fletchery.MOD_ID)
public class FletcheryForge {
    public FletcheryForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Fletchery.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Fletchery.init();
    }
}
