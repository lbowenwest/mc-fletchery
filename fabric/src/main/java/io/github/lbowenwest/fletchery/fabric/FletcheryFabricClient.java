package io.github.lbowenwest.fletchery.fabric;

import io.github.lbowenwest.fletchery.client.FletcheryClient;
import net.fabricmc.api.ClientModInitializer;

public class FletcheryFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FletcheryClient.onInitializeClient();
    }
}
