package io.github.lbowenwest.fletchery.client.gui;

import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.client.gui.handler.FletchingTableContainerMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class FletchingTableScreen extends AbstractContainerScreen<FletchingTableContainerMenu> {

    public static ResourceLocation BACKGROUND = new ResourceLocation(Fletchery.MOD_ID, "textures/gui/fletching_table.png");

    public FletchingTableScreen(FletchingTableContainerMenu handler, Inventory inventory, Component component) {
        super(handler, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = this.leftPos;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BACKGROUND, k, l, 0, 0, this.imageWidth, this.imageHeight);

    }
}
