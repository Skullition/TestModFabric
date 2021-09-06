package xyz.skullition.testmodfabric.test.blocks;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.entity.EntityType;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import xyz.skullition.testmodfabric.registry.Setup;

// currently will fail
public class UnsafeBlockTest implements FabricGameTest {
    @GameTest(structureName = EMPTY_STRUCTURE)
    public void checkLightning(TestContext test) {
        BlockPos blockPos = new BlockPos(2, 1, 2);

        test.setBlockState(blockPos, Setup.UNSAFE_BLOCK);
        test.expectBlock(Setup.UNSAFE_BLOCK, blockPos);
        test.useBlock(blockPos);
        test.spawnMob(EntityType.VILLAGER, 2, 5, 2);
        // 
        test.method_36038(EntityType.ZOMBIFIED_PIGLIN, new BlockPos(2, 2, 2));
        test.killAllEntities();
    }
}
