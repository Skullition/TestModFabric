package xyz.skullition.testmodfabric.items;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExtendedPickaxe extends PickaxeItem {
    public ExtendedPickaxe(Settings settings) {
        super(ToolMaterials.DIAMOND, 2, -2.8F, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        int distance = getDistance(stack);
        tooltip.add(new TranslatableText("message.extendedpickaxe.tooltip", Integer.toString(distance)));
    }

    private int getDistance(ItemStack stack) {
        int distance;
        if (stack.hasNbt()) {
            assert stack.getNbt() != null;
            distance = stack.getNbt().getInt("distance");
        } else {
            distance = 0;
        }
        return distance;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int distance = stack.getOrCreateNbt().getInt("distance");
        distance++;
        if (distance > 4) {
            distance = 0;
        }
        stack.getNbt().putInt("distance", distance);
        if (!world.isClient()) {
            user.sendMessage(new TranslatableText("message.extendedpickaxe.text", Integer.toString(distance)), true);
        }
        return TypedActionResult.success(stack, true);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.postMine(stack, world, state, pos, miner);
        if (result) {
            int distance = this.getDistance(stack);
            if (distance > 0) {
                NbtCompound nbt = stack.getOrCreateNbt();
                boolean mining = nbt.getBoolean("mining");
                if (!mining) {
                    BlockHitResult hit = trace(world, miner);
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        nbt.putBoolean("mining", true);
                        for (int i = 0; i < distance; i++) {
                            BlockPos relativeDist = pos.offset(hit.getSide().getOpposite(), i + 1);
                            if (!tryHarvest(stack, miner, relativeDist)) {
                                nbt.putBoolean("mining", false);
                                return result;
                            }
                        }
                        nbt.putBoolean("mining", false);
                    }
                }
            }
        }
        return result;
    }

    private boolean tryHarvest(ItemStack stack, LivingEntity entityLiving, BlockPos pos) {
        BlockState state = entityLiving.world.getBlockState(pos);
        if (isSuitableFor(state)) {
            if (entityLiving instanceof ServerPlayerEntity player) {
                return player.interactionManager.tryBreakBlock(pos);
            }
        }
        return false;
    }

    private BlockHitResult trace(World world, LivingEntity miner) {
        Vec3d eye = miner.getCameraPosVec(1.0f);
        Vec3d view = miner.getRotationVec(1.0f);
        Vec3d withReach = eye.add(view.x * 4.5F, view.y * 4.5F, view.z * 4.5F);
        return world.raycast(new RaycastContext(eye, withReach, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, miner));
    }

}
