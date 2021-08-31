package xyz.skullition.testmodfabric.items;

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

public class RealPhone extends Item {


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

        if (player != null) {
            player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, 1.0F, 1.0F);
        }

        world.playSound(player, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!world.isClient) {
            world.setBlockState(blockPos, Blocks.DIAMOND_BLOCK.getDefaultState());
            if (player != null) {
                context.getStack().damage(1, player, (p) -> p.sendToolBreakStatus(context.getHand()));
            }

            return ActionResult.success(false);
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
