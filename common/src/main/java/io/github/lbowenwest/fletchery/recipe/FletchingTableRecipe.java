package io.github.lbowenwest.fletchery.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class FletchingTableRecipe implements Recipe<Container> {

    private final ResourceLocation identifier;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;

    public FletchingTableRecipe(ResourceLocation identifier, NonNullList<Ingredient> inputs, ItemStack output) {
        this.identifier = identifier;
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(Container container, Level level) {
        // TODO currently allows ingredients in any order which isn't great
        StackedContents recipeMatcher = new StackedContents();
        int matchingStacks = 0;

        for (int i = 0; i < 3; ++i) {
            ItemStack itemStack = container.getItem(i);
            if (!itemStack.isEmpty()) {
                ++matchingStacks;
                recipeMatcher.accountStack(itemStack, 1);
            }
        }
        return matchingStacks == this.inputs.size() && recipeMatcher.canCraft(this, null);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack result = this.getResultItem(registryAccess).copy();
        if (result.is(Items.TIPPED_ARROW)) {
            ItemStack inputPotion = getInputPotionStack(container);
            if (!inputPotion.isEmpty()) {
                PotionUtils.setPotion(result, PotionUtils.getPotion(inputPotion));
                PotionUtils.setCustomEffects(result, PotionUtils.getCustomEffects(inputPotion));
            }
        }
        return result;
    }

    private ItemStack getInputPotionStack(Container container) {
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemStack = container.getItem(i);
            if (PotionUtils.getPotion(itemStack) != Potions.EMPTY) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FletcheryRecipeTypes.FLETCHING_TABLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FletcheryRecipeTypes.FLETCHING_TABLE_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FletchingTableRecipe> {

        @Override
        public FletchingTableRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            final var ingredientsArray = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < jsonObject.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredientsArray.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for fletching table");
            } else if (ingredients.size() > 3) {
                throw new JsonParseException("Too many ingredients for fletching table");
            } else {
                return new FletchingTableRecipe(resourceLocation, ingredients, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result")));
            }
        }

        @Override
        public FletchingTableRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            final var ingredients = NonNullList.withSize(friendlyByteBuf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(friendlyByteBuf));
            return new FletchingTableRecipe(resourceLocation, ingredients, friendlyByteBuf.readItem());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, FletchingTableRecipe recipe) {
            friendlyByteBuf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.toNetwork(friendlyByteBuf);
            }
            friendlyByteBuf.writeItem(recipe.output);
        }
    }
}
