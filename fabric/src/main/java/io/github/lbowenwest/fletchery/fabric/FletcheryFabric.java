package io.github.lbowenwest.fletchery.fabric;

import io.github.lbowenwest.fletchery.Fletchery;
import net.fabricmc.api.ModInitializer;

public class FletcheryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Fletchery.init();
    }
}
