package io.github.lbowenwest.fletchery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class FletcheryRecipeTypes {
    private static final Registrar<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Fletchery.MOD_ID, Registries.RECIPE_TYPE).getRegistrar();
    private static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Fletchery.MOD_ID, Registries.RECIPE_SERIALIZER).getRegistrar();


    public static final RegistrySupplier<RecipeType<FletchingTableRecipe>> FLETCHING_TABLE_RECIPE_TYPE = create("fletching");
    public static final RegistrySupplier<RecipeSerializer<FletchingTableRecipe>> FLETCHING_TABLE_RECIPE_SERIALIZER = create("fletching", FletchingTableRecipe.Serializer::new);


    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(String name, Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(new FletcheryIdentifier(name), serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create(String name) {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return RECIPE_TYPES.register(new FletcheryIdentifier(name), type);
    }

    public static void init() {
    }
}
