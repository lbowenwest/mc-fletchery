package io.github.lbowenwest.fletchery.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.lbowenwest.fletchery.FletcheryMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FletcheryMod.MOD_ID)
public class FletcheryModForge {
    public FletcheryModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(FletcheryMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        FletcheryMod.init();
    }
}
