package xyz.skullition.testmodfabric.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class LightningStaff extends ToolItem {
    public LightningStaff(Settings settings) {
        super(ToolMaterials.WOOD, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        Vec3d eye = user.getCameraPosVec(1.0f);
        Vec3d view = user.getRotationVec(1.0f);
        Vec3d withReach = eye.add(view.x * 20F, view.y * 20F, view.z * 20F);
        BlockHitResult hitResult = world.raycast(new RaycastContext(eye, withReach, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, user));
        BlockPos blockLocation = hitResult.getBlockPos();

        LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
        if (lightningEntity != null) {
            lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockLocation));
        }
        // TODO: Lightning distance scaling with view distance
         user.getStackInHand(hand).damage(10, user, (p) -> p.sendToolBreakStatus(hand));
        world.spawnEntity(lightningEntity);

        return TypedActionResult.success(user.getStackInHand(hand), true);
    }
}
