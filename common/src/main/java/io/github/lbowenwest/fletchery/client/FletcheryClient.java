package io.github.lbowenwest.fletchery.client;

import dev.architectury.registry.menu.MenuRegistry;
import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.client.gui.FletchingTableScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FletcheryClient {
    public static void onInitializeClient() {
        MenuRegistry.registerScreenFactory(Fletchery.FLETCHING_TABLE_MENU_HANDLER.get(), FletchingTableScreen::new);
    }
}
