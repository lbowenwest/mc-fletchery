package io.github.lbowenwest.fletchery.mixin;

import dev.architectury.registry.menu.MenuRegistry;
import io.github.lbowenwest.fletchery.client.gui.handler.FletchingTableContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FletchingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FletchingTableBlock.class)
public class FletchingTableBlockMixin {


    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void handleUse(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> result) {
        if (level.isClientSide) {
            result.setReturnValue(InteractionResult.SUCCESS);
        } else {
            MenuRegistry.openMenu((ServerPlayer) player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.translatable("container.fletchery.fletching_table_menu");
                }

                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new FletchingTableContainerMenu(i, inventory, ContainerLevelAccess.create(level, blockPos));
                }
            });
            result.setReturnValue(InteractionResult.CONSUME);
        }
    }

}
