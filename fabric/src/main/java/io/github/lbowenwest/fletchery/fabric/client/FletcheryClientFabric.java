package io.github.lbowenwest.fletchery.fabric.client;

import io.github.lbowenwest.fletchery.client.FletcheryClient;
import net.fabricmc.api.ClientModInitializer;

public class FletcheryClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FletcheryClient.onInitializeClient();
    }
}
