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
    private final Player player;

    public FletchingTableContainerMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    public FletchingTableContainerMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(FletcheryMod.FLETCHING_TABLE_MENU_HANDLER.get(), i);
        this.craftSlots = new TransientCraftingContainer(this, 3, 1);
        this.resultSlots = new ResultContainer();
        this.access = containerLevelAccess;
        this.player = inventory.player;

    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, Blocks.FLETCHING_TABLE);
    }
}
