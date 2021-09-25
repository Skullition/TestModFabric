package xyz.skullition.testmodfabric.blocks.blockentities;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import xyz.skullition.testmodfabric.registry.Setup;

public class BoxBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    private final DefaultedList<ItemStack> itemInventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public BoxBlockEntity(BlockPos pos, BlockState state) {
        super(Setup.BOX_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return itemInventory;
    }

    /**
     * Returns the title of this screen handler; will be a part of the open
     * screen packet sent to the client.
     */
    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BoxScreenHandler(syncId, inv, this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.itemInventory);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.itemInventory);
        return nbt;
    }

    /**
     * Writes additional server -&gt; client screen opening data to the buffer.
     *
     * @param player the player that is opening the screen
     * @param buf    the packet buffer
     */
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}
