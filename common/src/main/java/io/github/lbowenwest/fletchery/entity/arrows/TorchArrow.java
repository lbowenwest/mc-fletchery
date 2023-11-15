package io.github.lbowenwest.fletchery.entity.arrows;

import io.github.lbowenwest.fletchery.registry.FletcheryEntities;
import io.github.lbowenwest.fletchery.registry.FletcheryObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TorchArrow extends AbstractArrow {

    public TorchArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public TorchArrow(Level level, LivingEntity livingEntity) {
        super( FletcheryEntities.TORCH_ARROW.get(), livingEntity, level);
    }


//
//    public TorchArrow(Level level, double d, double e, double f ) {
//        super(FletcheryEntities.TORCH_ARROW, d, e, f, level);
//    }


    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(FletcheryObjects.TORCH_ARROW.get());
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (!this.level().isClientSide()) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos finalPos = blockPos.relative(direction);
            BlockState state = this.level().getBlockState(finalPos);

            if ((state.isAir() || state.canBeReplaced()) && direction != Direction.DOWN) {
                // TODO place torch
            }
        }
        super.onHitBlock(blockHitResult);
    }
}
