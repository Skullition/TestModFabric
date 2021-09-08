package xyz.skullition.testmodfabric.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ExplosiveArrowEntity extends ArrowEntity {
    public ExplosiveArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public ExplosiveArrowEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public ExplosiveArrowEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        BlockPos pos = target.getBlockPos();
        target.getEntityWorld().createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 4.0F, Explosion.DestructionType.BREAK);
    }
}
