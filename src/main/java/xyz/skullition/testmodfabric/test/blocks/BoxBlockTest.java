package xyz.skullition.testmodfabric.test.blocks;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import xyz.skullition.testmodfabric.blocks.blockentities.BoxBlockEntity;
import xyz.skullition.testmodfabric.blocks.blockentities.UnsafeBlockEntity;
import xyz.skullition.testmodfabric.registry.Setup;

public class BoxBlockTest implements FabricGameTest {

    @GameTest(structureName = EMPTY_STRUCTURE)
    public void checkInventory(TestContext test) {
        // TODO: FINISH THIS
        BlockPos pos = new BlockPos(2, 2, 2);
        test.setBlockState(pos, Setup.BOX_BLOCK);
        BoxBlockEntity boxBlockEntity = (BoxBlockEntity) test.getWorld().getBlockEntity(test.getAbsolutePos(pos));
        boxBlockEntity.setStack(0, Items.SNOWBALL.getDefaultStack());
        test.addInstantFinalTask(() -> {
            test.expectContainerWith(pos, Items.SNOWBALL);
        });
    }
}
