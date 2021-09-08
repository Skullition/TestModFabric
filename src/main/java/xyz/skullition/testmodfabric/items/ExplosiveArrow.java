package xyz.skullition.testmodfabric.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.skullition.testmodfabric.entities.ExplosiveArrowEntity;

public class ExplosiveArrow extends ArrowItem {
    public ExplosiveArrow(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        ExplosiveArrowEntity EArrowEntity = new ExplosiveArrowEntity(world, shooter);
        EArrowEntity.initFromStack(stack);
        return EArrowEntity;
    }
}
