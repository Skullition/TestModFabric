package xyz.skullition.testmodfabric.test.mixin;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xyz.skullition.testmodfabric.registry.Setup;

public class MixinTest implements FabricGameTest {
    @GameTest(structureName = EMPTY_STRUCTURE)
    public void sheepDropsWool(TestContext tc) {
        BlockPos pos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = tc.getAbsolutePos(pos);

        // spawn sheep
        SheepEntity sheep = this.spawnSheep(tc, absolutePos);
        sheep.sheared(SoundCategory.PLAYERS);
        tc.addInstantFinalTask(() -> tc.expectItemsAt(Items.BONE, pos, 1, 1));
    }

    @GameTest(structureName = EMPTY_STRUCTURE)
    public  void PlayerShearsWool(TestContext tc) {
        BlockPos pos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = tc.getAbsolutePos(pos);

        SheepEntity sheepEntity = this.spawnSheep(tc, absolutePos);
        ItemStack shear = Items.SHEARS.getDefaultStack();
        PlayerEntity playerEntity = tc.createMockPlayer();
        playerEntity.getInventory().setStack(0, shear);
        sheepEntity.interactMob(playerEntity, Hand.MAIN_HAND);
        tc.addInstantFinalTask(() -> tc.expectItemsAt(Items.BONE, pos, 1, 1));
    }

    private SheepEntity spawnSheep(TestContext tc, BlockPos absolutePos) {
        // place blocks
        tc.setBlockState(1, 1, 1, Blocks.DIRT);
        tc.setBlockState(0, 1, 1, Blocks.DIRT);
        tc.setBlockState(0, 1, 2, Blocks.DIRT);


        SheepEntity sheep = EntityType.SHEEP.create(tc.getWorld());
        if (sheep != null) {
            sheep.refreshPositionAfterTeleport(Vec3d.ofCenter(absolutePos));
        }
        sheep.setPersistent();
        sheep.clearGoalsAndTasks();
        tc.getWorld().spawnEntity(sheep);
        return sheep;
    }
}
