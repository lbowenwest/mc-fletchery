package io.github.lbowenwest.fletchery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.entity.arrows.TorchArrow;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class FletcheryEntities {
    public static final Registrar<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Fletchery.MOD_ID, Registries.ENTITY_TYPE).getRegistrar();

    public static final RegistrySupplier<EntityType<TorchArrow>> TORCH_ARROW = register("torch_arrow",
        () -> EntityType.Builder.of(TorchArrow::new, MobCategory.MISC)
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(20)
            .build(Fletchery.res("torch_arrow").toString())
    );

    public static <T extends Entity> RegistrySupplier<EntityType<T>> register(String name, Supplier<EntityType<T>> entityType) {
        return ENTITY_TYPES.register(Fletchery.res(name), entityType);
    }
}
