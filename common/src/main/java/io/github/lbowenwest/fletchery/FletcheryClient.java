package io.github.lbowenwest.fletchery;

import dev.architectury.registry.menu.MenuRegistry;
import io.github.lbowenwest.fletchery.menu.FletchingTableScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FletcheryClient {
    public static void onInitializeClient() {
        MenuRegistry.registerScreenFactory(FletcheryMod.FLETCHING_TABLE_MENU_HANDLER.get(), FletchingTableScreen::new);
    }
}
