package xyz.skullition.testmodfabric.blocks.blockentities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import xyz.skullition.testmodfabric.registry.Setup;

public class BoxScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public BoxScreenHandler(int syncId, PlayerInventory inv, Inventory inventory) {
        super(Setup.BOX_SCREEN_HANDLER, syncId);
        checkSize(inventory, 9);
        this.inventory = inventory;

        inventory.onOpen(inv.player);
        int m;
        int l;
        //Our inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18));
            }
        }
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inv, m, 8 + m * 18, 142));
        }
    }

    public  BoxScreenHandler(int syncId, PlayerInventory inv) {
        this(syncId, inv, new SimpleInventory(9));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
