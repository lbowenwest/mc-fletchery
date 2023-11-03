package io.github.lbowenwest.fletchery.integration.jei;

import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.client.gui.FletchingTableScreen;
import io.github.lbowenwest.fletchery.client.gui.handler.FletchingTableContainerMenu;
import io.github.lbowenwest.fletchery.integration.jei.category.FletchingTableCategory;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Objects;

public class FletcheryJEIPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FletchingTableCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<FletchingTableRecipe> fletchingRecipes = rm.getAllRecipesFor(FletcheryRecipeTypes.FLETCHING_TABLE_RECIPE_TYPE.get());
        registration.addRecipes(FletchingTableCategory.FLETCHING_TABLE, fletchingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(Blocks.FLETCHING_TABLE.asItem().getDefaultInstance(), FletchingTableCategory.FLETCHING_TABLE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                FletchingTableContainerMenu.class,
                Fletchery.FLETCHING_TABLE_MENU_HANDLER.get(),
                FletchingTableCategory.FLETCHING_TABLE,
                1,
                3,
                4,
                36);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FletchingTableScreen.class,
                75,
                32,
                28,
                23,
                FletchingTableCategory.FLETCHING_TABLE);
    }
    @Override
    public ResourceLocation getPluginUid() {
        return new FletcheryIdentifier("jei_plugin");
    }
}
