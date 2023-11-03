package io.github.lbowenwest.fletchery;

import net.minecraft.resources.ResourceLocation;

public class FletcheryIdentifier extends ResourceLocation {

    public FletcheryIdentifier(String path) {
        super(Fletchery.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Fletchery.MOD_ID + ":" + path);
    }
}
