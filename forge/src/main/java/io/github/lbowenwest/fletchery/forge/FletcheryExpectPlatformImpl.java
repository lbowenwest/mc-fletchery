package io.github.lbowenwest.fletchery.forge;

import io.github.lbowenwest.fletchery.FletcheryExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class FletcheryExpectPlatformImpl {
    /**
     * This is our actual method to {@link FletcheryExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
