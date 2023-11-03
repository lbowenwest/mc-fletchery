package io.github.lbowenwest.fletchery.menu;

import io.github.lbowenwest.fletchery.FletcheryMod;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class FletchingTableContainerMenu extends AbstractContainerMenu {
    private final CraftingContainer craftSlots;
    private final ResultContainer resultSlots;
    private final ContainerLevelAccess access;

    public FletchingTableContainerMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    public FletchingTableContainerMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(FletcheryMod.FLETCHING_TABLE_MENU_HANDLER.get(), i);
        this.craftSlots = new TransientCraftingContainer(this, 3, 1);
        this.resultSlots = new ResultContainer();
        this.access = containerLevelAccess;

        buildCraftingContainer(inventory);
        buildPlayerContainer(inventory);

    }

    private void buildCraftingContainer(Inventory inventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            this.addSlot(new Slot(this.craftSlots, i, 48, 17 + i * 18));
        }
        this.addSlot(new ResultSlot(inventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

    }

    private void buildPlayerContainer(Inventory inventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        // TODO
        ItemStack itemStack = ItemStack.EMPTY;
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, Blocks.FLETCHING_TABLE);
    }
}
