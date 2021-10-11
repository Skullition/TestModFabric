package xyz.skullition.testmodfabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public World world;

    @Shadow public abstract BlockPos getBlockPos();

    @Shadow public abstract SoundCategory getSoundCategory();


    @Shadow @Final protected Random random;

    @Shadow public abstract double getParticleX(double widthScale);

    @Shadow public abstract double getRandomBodyY();

    @Shadow public abstract double getParticleZ(double widthScale);

    @Inject(method = "interact", at = @At("TAIL"))
    public void interactMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty()) {
            this.world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_WOLF_WHINE, this.getSoundCategory(), 1F, 1F);
        }
        if (this.world.isClient()) {
            for(int i = 0; i < 7; ++i) {
                double d = this.random.nextGaussian() * 0.02D;
                double e = this.random.nextGaussian() * 0.02D;
                double f = this.random.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
            }
        }
    }
}
