package io.github.lbowenwest.fletchery;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.menu.FletchingTableContainerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;


public class FletcheryMod {
    public static final String MOD_ID = "fletchery";

    public static final Registrar<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registries.MENU).getRegistrar();
    public static final RegistrySupplier<MenuType<FletchingTableContainerMenu>> FLETCHING_TABLE_MENU_HANDLER = MENUS.register(
            new ResourceLocation(MOD_ID, "fletching_table_menu"),
            () -> new MenuType<>(FletchingTableContainerMenu::new, FeatureFlags.VANILLA_SET)
    );

    public static void init() {
//        System.out.println(FletcheryExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
