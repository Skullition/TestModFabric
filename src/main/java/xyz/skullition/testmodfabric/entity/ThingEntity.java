package xyz.skullition.testmodfabric.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class ThingEntity extends PathAwareEntity {
    public ThingEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
