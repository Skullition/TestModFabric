package xyz.skullition.testmodfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.skullition.testmodfabric.registry.Setup;

public class TestModFabric implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "testmodfabric";

    public static final ItemGroup MY_ITEM_GROUP = FabricItemGroupBuilder
            .build(new Identifier(TestModFabric.MODID, "mygroup"), () -> new ItemStack(Setup.REAL_PHONE));

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        Setup.registerAll();

        // consumable tools callback
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            if (!player.isSpectator() && !player.isCreative() && player.getMainHandStack().getItem() instanceof ToolItem && player.getHungerManager().getFoodLevel() < 6) {
                world.emitGameEvent(player, GameEvent.EAT, player.getBlockPos());
                world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
                player.getMainHandStack().damage(100, player, (p) -> p.sendToolBreakStatus(hand));
                player.getHungerManager().add(4, 2);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 120));
            }
            return TypedActionResult.pass(ItemStack.EMPTY);
        });

    }
}
