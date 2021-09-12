package xyz.skullition.testmodfabric.test.blocks;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import xyz.skullition.testmodfabric.blocks.blockentities.BoxBlockEntity;
import xyz.skullition.testmodfabric.registry.Setup;

public class BoxBlockTest implements FabricGameTest {

    @GameTest(structureName = EMPTY_STRUCTURE)
    public void checkInventory(TestContext test) {
        BlockPos pos = new BlockPos(2, 2, 2);
        test.setBlockState(pos, Setup.BOX_BLOCK);
        BoxBlockEntity boxBlockEntity = (BoxBlockEntity) test.getWorld().getBlockEntity(test.getAbsolutePos(pos));
        boxBlockEntity.setStack(0, Items.SNOWBALL.getDefaultStack());
        test.addInstantFinalTask(() -> test.expectContainerWith(pos, Items.SNOWBALL));
        test.setTime(1000);
    }

    @GameTest(structureName = EMPTY_STRUCTURE)
    public void checkInventoryWhenBroken(TestContext test) {
        BlockPos pos = new BlockPos(2, 2, 2);
        test.setBlockState(pos, Setup.BOX_BLOCK);
        test.setBlockState(2, 0, 2, Blocks.DIRT);
        BoxBlockEntity boxBlockEntity = (BoxBlockEntity) test.getBlockEntity(pos);
        boxBlockEntity.setStack(0, Items.BONE_BLOCK.getDefaultStack());
        ItemStack stack = boxBlockEntity.getStack(0);
        stack.setCount(64);
        test.removeBlock(pos);
        test.addInstantFinalTask(() -> test.expectItemsAt(Items.BONE_BLOCK, new BlockPos(2, 1, 2), 1, 64));
        test.waitAndRun(20, () -> test.killAllEntities());
    }
}
