package xyz.skullition.testmodfabric.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import xyz.skullition.testmodfabric.blocks.blockentities.BoxBlockEntity;
import xyz.skullition.testmodfabric.blocks.blockentities.UnsafeBlockEntity;
import xyz.skullition.testmodfabric.registry.Setup;

public class UnsafeBlock extends BlockWithEntity {
    public static final BooleanProperty CHARGED = BooleanProperty.of("charged");

    public UnsafeBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(CHARGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);
        // for lightning to spawn
        // check if block is active
        if (player.getStackInHand(hand).isEmpty()) {
            if (state.get(CHARGED).equals(true) && !world.isClient()) {
                world.setBlockState(pos, state.with(CHARGED, false));
            } else {
                world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.HOSTILE, 1F, 1F);
                world.setBlockState(pos, state.with(CHARGED, true));
            }
        }

        if (!player.getStackInHand(hand).isEmpty()) {
            if (blockEntity.getStack(0).isEmpty()) {
                blockEntity.setStack(0, player.getStackInHand(hand).copy());
                player.getStackInHand(hand).setCount(0);
            } else if (blockEntity.getStack(1).isEmpty()) {
                blockEntity.setStack(1, player.getStackInHand(hand).copy());
                player.getStackInHand(hand).setCount(0);
            } else { // player's hand is empty
                if (!blockEntity.getStack(0).isEmpty()) {
                    player.getInventory().offerOrDrop(blockEntity.getStack(0));
                    blockEntity.removeStack(0);
                } else if (!blockEntity.getStack(1).isEmpty()) {
                    player.getInventory().offerOrDrop(blockEntity.getStack(1));
                    blockEntity.removeStack(1);
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state.get(CHARGED)) {
            PigEntity pigEntity = EntityType.PIG.create(world);
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
            if (lightningEntity != null) {
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
            }
            if (pigEntity != null) {
                pigEntity.refreshPositionAfterTeleport(Vec3d.ofCenter(pos));
            }
            world.spawnEntity(pigEntity);
            world.spawnEntity(lightningEntity);
            world.setBlockState(pos, state.with(CHARGED, false));
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new UnsafeBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Setup.UNSAFE_BLOCK_ENTITY, (world1, pos, state1, be) -> UnsafeBlockEntity.tick(world1, pos, state1, be));
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (world.isClient()) {
            return;
        }
        BlockPos pos = hit.getBlockPos();
        if (projectile.canModifyAt(world, pos) && state.get(CHARGED)) {
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10, true, Explosion.DestructionType.BREAK);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (state.getBlock() != newState.getBlock()) {
            ItemScatterer.spawn(world, pos, (UnsafeBlockEntity)blockEntity);
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
