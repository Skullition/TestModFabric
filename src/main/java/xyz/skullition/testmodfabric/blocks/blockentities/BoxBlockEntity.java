package xyz.skullition.testmodfabric.blocks.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import xyz.skullition.testmodfabric.registry.Setup;

public class BoxBlockEntity extends BlockEntity {
    public BoxBlockEntity( BlockPos pos, BlockState state) {
        super(Setup.BOX_BLOCK_ENTITY, pos, state);
    }
}
