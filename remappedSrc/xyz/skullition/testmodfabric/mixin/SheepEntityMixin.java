package xyz.skullition.testmodfabric.mixin;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.skullition.testmodfabric.TestModFabric;

@Mixin(SheepEntity.class)
abstract class SheepEntityMixin {
    @Inject(method = "sheared", at = @At("HEAD"))
    public void shearedMixin(CallbackInfo ci) {
        TestModFabric.LOGGER.info("Hello!");
        SheepEntity sheep = (SheepEntity)(Object)this;
        sheep.dropItem(Items.BONE);
    }
}
