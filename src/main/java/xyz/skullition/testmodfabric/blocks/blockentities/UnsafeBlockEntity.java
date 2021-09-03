package xyz.skullition.testmodfabric.blocks.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.skullition.testmodfabric.registry.Setup;

public class UnsafeBlockEntity extends BlockEntity {
    private int testInt = 4;

    public UnsafeBlockEntity(BlockPos pos, BlockState state) {
        super(Setup.UNSAFE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world1, BlockPos pos, BlockState state1, UnsafeBlockEntity be) {
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("somenumber", testInt);
        return nbt;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        testInt = nbt.getInt("somenumber");
    }
}
