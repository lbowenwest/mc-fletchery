package io.github.lbowenwest.fletchery.integration.jei.category;

import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.client.gui.FletchingTableScreen;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class FletchingTableCategory implements IRecipeCategory<FletchingTableRecipe> {
    public static final RecipeType<FletchingTableRecipe> FLETCHING_TABLE = RecipeType.create(Fletchery.MOD_ID, "fletching_table", FletchingTableRecipe.class);
    public static final int WIDTH = 124;
    public static final int HEIGHT = 60;
    public static final int WIDTH_OF = 26;
    public static final int HEIGHT_OF = 13;
    private final IDrawable background;
    private final IDrawable icon;
    private final Component name;


    public FletchingTableCategory(IGuiHelper helper) {
        this.name = Component.translatable("jei.fletchery.fletching_table_category");
        this.background = helper.createDrawable(FletchingTableScreen.BACKGROUND, WIDTH_OF, HEIGHT_OF, WIDTH, HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, Blocks.FLETCHING_TABLE.asItem().getDefaultInstance());
    }
    @Override
    public RecipeType<FletchingTableRecipe> getRecipeType() {
        return FLETCHING_TABLE;
    }

    @Override
    public Component getTitle() {
        return this.name;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FletchingTableRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int size = ingredients.size();

        if (size > 0) builder.addSlot(RecipeIngredientRole.INPUT, 48 - WIDTH_OF, 17 - HEIGHT_OF).addIngredients(ingredients.get(0));
        if (size > 1) builder.addSlot(RecipeIngredientRole.INPUT, 48 - WIDTH_OF, 35 - HEIGHT_OF).addIngredients(ingredients.get(1));
        if (size > 2) builder.addSlot(RecipeIngredientRole.INPUT, 48 - WIDTH_OF, 53 - HEIGHT_OF).addIngredients(ingredients.get(2));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 124 - WIDTH_OF, 35 - HEIGHT_OF).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));

    }
}
