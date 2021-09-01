package xyz.skullition.testmodfabric.blocks.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import xyz.skullition.testmodfabric.registry.Setup;

public class UnsafeBlockEntity extends BlockEntity {
    public UnsafeBlockEntity(BlockPos pos, BlockState state) {
        super(Setup.UNSAFE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        int testInt = 4;
        super.writeNbt(nbt);
        nbt.putInt("somenumber", testInt);
        return nbt;
    }
}