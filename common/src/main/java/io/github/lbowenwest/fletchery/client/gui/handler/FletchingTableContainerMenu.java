package io.github.lbowenwest.fletchery.client.gui.handler;

import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.client.gui.handler.slot.FletchingTableOutputSlot;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeTypes;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FletchingTableContainerMenu extends RecipeBookMenu<CraftingContainer> {
    private static final int RESULT_SLOT = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_END = 4;
    private static final int INV_SLOT_START = 4;
    private static final int INV_SLOT_END = 31;
    private static final int USE_ROW_SLOT_START = 31;
    private static final int USE_ROW_SLOT_END = 40;

    private final CraftingContainer craftSlots;
    private final ResultContainer resultSlots;
    private final ContainerLevelAccess access;
    private final Player player;

    public FletchingTableContainerMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    public FletchingTableContainerMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(Fletchery.FLETCHING_TABLE_MENU_HANDLER.get(), i);
        this.craftSlots = new TransientCraftingContainer(this, 3, 1);
        this.resultSlots = new ResultContainer();
        this.access = containerLevelAccess;
        this.player = inventory.player;

        buildCraftingContainer(inventory);
        buildPlayerContainer(inventory);

    }

    private void buildCraftingContainer(Inventory inventory) {
        this.addSlot(new FletchingTableOutputSlot(inventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));
        int i;
        for (i = 0; i < 3; ++i) {
            this.addSlot(new Slot(this.craftSlots, i, 48, 17 + i * 18));
        }
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

    protected static void slotChangedGrid(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftingContainer, ResultContainer resultContainer) {
        if (!level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<FletchingTableRecipe> optional = level.getRecipeManager().getRecipeFor(FletcheryRecipeTypes.FLETCHING_TABLE_RECIPE_TYPE.get(), craftingContainer, level);
            if (optional.isPresent()) {
                FletchingTableRecipe recipe = optional.get();
                itemStack = recipe.assemble(craftingContainer, level.registryAccess());
            }
            resultContainer.setItem(0, itemStack);
            menu.setRemoteSlot(0, itemStack);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));
        }
    }

    @Override
    public void slotsChanged(Container container) {
        this.access.execute(((level, blockPos) -> {
            slotChangedGrid(this, level, this.player, this.craftSlots, this.resultSlots);
        }));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot originSlot = this.slots.get(i);
        if (originSlot.hasItem()) {
            ItemStack originStack = originSlot.getItem();
            resultStack = originStack.copy();
            if (i == RESULT_SLOT) {
                this.access.execute((level, blockPos) -> {
                    originStack.getItem().onCraftedBy(originStack, level, player);
                });
                if (!this.moveItemStackTo(originStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                originSlot.onQuickCraft(originStack, resultStack);

            } else if (i >= INV_SLOT_START && i < USE_ROW_SLOT_END) {
                if (!this.moveItemStackTo(originStack, CRAFT_SLOT_START, CRAFT_SLOT_END, false)) {
                    if (i < INV_SLOT_END) {
                        if (!this.moveItemStackTo(originStack, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(originStack, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }

            } else if (!this.moveItemStackTo(originStack, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (originStack.isEmpty()) {
                originSlot.setByPlayer(ItemStack.EMPTY);
            } else {
                originSlot.setChanged();
            }

            if (originStack.getCount() == resultStack.getCount()) {
                return ItemStack.EMPTY;
            }

            originSlot.onTake(player, originStack);
            if (i == RESULT_SLOT) {
                player.drop(originStack, false);
            }
        }
        return resultStack;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
        this.craftSlots.fillStackedContents(stackedContents);
    }

    @Override
    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute(((level, blockPos) -> {
            this.clearContainer(player, this.craftSlots);
        }));
    }

    @Override
    public boolean recipeMatches(Recipe<? super CraftingContainer> recipe) {
        return recipe.matches(this.craftSlots, this.player.level());
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(itemStack, slot);
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    @Override
    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        // TODO create recipe book type
        return RecipeBookType.CRAFTING;
    }

    @Override
    public boolean shouldMoveToInventory(int i) {
        return i != this.getResultSlotIndex();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, Blocks.FLETCHING_TABLE);
    }
}
