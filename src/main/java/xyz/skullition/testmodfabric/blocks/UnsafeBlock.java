package xyz.skullition.testmodfabric.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.skullition.testmodfabric.blocks.blockentities.UnsafeBlockEntity;
import xyz.skullition.testmodfabric.registry.Setup;

public class UnsafeBlock extends BlockWithEntity {
    public static final BooleanProperty LIGHTNING_THING = BooleanProperty.of("lightningthing");

    public UnsafeBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LIGHTNING_THING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHTNING_THING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // check if block is active
        if (world.getBlockState(pos).get(LIGHTNING_THING).equals(true) && !world.isClient()) {
            world.setBlockState(pos, state.with(LIGHTNING_THING, false));
        } else {
            world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.HOSTILE, 1F, 1F);
            world.setBlockState(pos, state.with(LIGHTNING_THING, true));
        }
        return ActionResult.PASS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity.isPlayer() && world.getBlockState(pos).get(LIGHTNING_THING)) {
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
            world.setBlockState(pos, state.with(LIGHTNING_THING, false));
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
        return checkType(type, Setup.UNSAFE_BLOCK_ENTITY, (world1, pos, state1, be) -> UnsafeBlockEntity.tick(world1, pos, state1, be))
    }
}
