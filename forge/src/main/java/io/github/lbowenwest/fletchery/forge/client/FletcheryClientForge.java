package io.github.lbowenwest.fletchery.forge.client;

import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.client.FletcheryClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Fletchery.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FletcheryClientForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        FletcheryClient.onInitializeClient();
    }
}

