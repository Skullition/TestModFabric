package xyz.skullition.testmodfabric.items;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class RealPhone extends Item {

    protected static final Map<Block, BlockState> BLOCK_CHANGE;

    static {
        BLOCK_CHANGE = Maps.newHashMap((new ImmutableMap.Builder()).put(Blocks.GRASS_BLOCK, Blocks.DIAMOND_BLOCK.getDefaultState()).put(Blocks.DIRT, Blocks.ANVIL.getDefaultState()).put(Blocks.PODZOL, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.MYCELIUM, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.getDefaultState()).build());
    }

    public RealPhone(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1.0F, 1.0F);
        if (!world.isClient) {
            user.sendMessage(new TranslatableText("realphone.message"), false);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState blockState2 = BLOCK_CHANGE.get(blockState.getBlock());
        BlockState blockState3 = null;

        player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, 1.0F, 1.0F);

        if (blockState2 != null) {
            world.playSound(player, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            blockState3 = blockState2;
        }

        if (blockState3 != null) {
            if (!world.isClient) {
                world.setBlockState(blockPos, Blocks.DIAMOND_BLOCK.getDefaultState());
                if (player != null) {
                    context.getStack().damage(1, player, (p) -> {
                        p.sendToolBreakStatus(context.getHand());
                    });
                }
            }

            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.damage(DamageSource.ANVIL, 1000F);
        return true;
    }
}
