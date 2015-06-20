package HxCKDMS.HxCLasers.Slots;

import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Api.LensRegistry;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import static HxCKDMS.HxCLasers.Slots.SlotLensMaker.SlotType.*;

public class SlotLensMaker extends Slot {
    private SlotType type;
    TileEntityLensMaker lensMaker;

    public SlotLensMaker(IInventory inventory, int id, int xPos, int yPos, SlotType type) {
        super(inventory, id, xPos, yPos);
        this.type = type;
        lensMaker = (TileEntityLensMaker) inventory;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(type == LENS) return stack.getItem() instanceof ILens;
        else if (type == UPGRADE) return LensRegistry.isItemUpgrade(stack);
        else if(type == GEM) return true;
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return !(lensMaker.ticksRemaining > 0);
    }

    public enum SlotType{
        LENS,
        UPGRADE,
        GEM
    }
}
