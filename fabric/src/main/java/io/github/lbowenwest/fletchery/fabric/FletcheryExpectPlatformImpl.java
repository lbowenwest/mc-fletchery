package io.github.lbowenwest.fletchery.fabric;

import io.github.lbowenwest.fletchery.FletcheryExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FletcheryExpectPlatformImpl {
    /**
     * This is our actual method to {@link FletcheryExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
