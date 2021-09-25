package xyz.skullition.testmodfabric.test.items;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import xyz.skullition.testmodfabric.TestModFabric;
import xyz.skullition.testmodfabric.registry.Setup;

public class LightningStaffTest implements FabricGameTest {
    @GameTest(structureName = EMPTY_STRUCTURE)
    // TODO: everything lol
    public void checkLightning(TestContext test) {
        ItemStack lightningStaff = Setup.LIGHTNING_STAFF.getDefaultStack();
        BlockPos pos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = test.getAbsolutePos(pos);
        PlayerEntity player = test.createMockPlayer();
        //ItemEntity staffEntity = test.spawnItem(lightningStaff.getItem(), pos.getX(), pos.getY(), pos.getZ());
        player.teleport(absolutePos.getX(), absolutePos.getY(), absolutePos.getZ(), true);
        player.getInventory().setStack(0, lightningStaff);
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() == Setup.LIGHTNING_STAFF) {
            lightningStaff.use(test.getWorld(), player, Hand.MAIN_HAND);
        }
        test.complete();
    }
}
