package io.github.lbowenwest.fletchery.items.arrows;

import io.github.lbowenwest.fletchery.entity.arrows.TorchArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TorchArrowItem extends ArrowItem {
    public TorchArrowItem(Properties properties) {
        super(properties);
    }

//    public TorchArrow(Level level, )

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        return new SpectralArrow(level, livingEntity);
    }
}
