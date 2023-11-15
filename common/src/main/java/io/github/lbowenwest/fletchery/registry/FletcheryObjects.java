package io.github.lbowenwest.fletchery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.items.arrows.TorchArrowItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FletcheryObjects {
//    public static final Registrar<Block> BLOCKS = DeferredRegister.create(Fletchery.MOD_ID, Registries.BLOCK).getRegistrar();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Fletchery.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEMS_REGISTRAR = ITEMS.getRegistrar();

    public static final RegistrySupplier<Item> TORCH_ARROW = registerItem("torch_arrow",
        () -> new TorchArrowItem(new Item.Properties())
    );


    public static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS_REGISTRAR.register(Fletchery.res(name), item);
    }

    public static void init() {
        ITEMS.register();

    }
}
