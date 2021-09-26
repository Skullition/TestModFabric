package xyz.skullition.testmodfabric.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class NoodleEntity extends PathAwareEntity {
    public NoodleEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (world.isClient()) return ActionResult.SUCCESS;
        player.sendMessage(new LiteralText("hello"), false);
        return ActionResult.SUCCESS;
    }
}
