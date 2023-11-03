package io.github.lbowenwest.fletchery.client.gui.handler.slot;

import io.github.lbowenwest.fletchery.registry.FletcheryRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FletchingTableOutputSlot extends Slot {
    private final CraftingContainer craftSlots;
    private final Player player;
    private int removeCount;

    public FletchingTableOutputSlot(Player player, CraftingContainer craftingContainer, Container container, int i, int j, int k) {
        super(container, i, j, k);
        this.player = player;
        this.craftSlots = craftingContainer;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }


    @Override
    public ItemStack remove(int i) {
        if (this.hasItem()) {
            this.removeCount += Math.min(i, this.getItem().getCount());
        }
        return super.remove(i);
    }

    @Override
    public void onTake(Player player, ItemStack itemStack) {
        NonNullList<ItemStack> remaining = player.level().getRecipeManager().getRemainingItemsFor(FletcheryRecipeTypes.FLETCHING_TABLE_RECIPE_TYPE.get(), this.craftSlots, player.level());
        for (int i = 0; i < remaining.size(); ++i) {
            ItemStack craftedStack = this.craftSlots.getItem(i);
            ItemStack remainingStack = remaining.get(i);
            if (!craftedStack.isEmpty()) {
                this.craftSlots.removeItem(i, 1);
                craftedStack = this.craftSlots.getItem(i);
            }

            if (!remainingStack.isEmpty()) {
                if (craftedStack.isEmpty()) {
                    this.craftSlots.setItem(i, remainingStack);
                } else if (ItemStack.isSameItemSameTags(craftedStack, remainingStack)) {
                    remainingStack.grow(craftedStack.getCount());
                    this.craftSlots.setItem(i, remainingStack);
                } else if (!this.player.getInventory().add(remainingStack)) {
                    this.player.drop(remainingStack, false);
                }
            }

        }

    }
}
