package HxCKDMS.HxCLasers.Slots;

import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Api.LaserRegistry;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensBench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import static HxCKDMS.HxCLasers.Slots.SlotLensBench.SlotType.GEM;
import static HxCKDMS.HxCLasers.Slots.SlotLensBench.SlotType.LENS;
import static HxCKDMS.HxCLasers.Slots.SlotLensBench.SlotType.*;

@SuppressWarnings("Duplicates")
public class SlotLensBench extends Slot {
    private SlotType type;
    TileEntityLensBench lensBench;

    public SlotLensBench(IInventory inventory, int id, int xPos, int yPos, SlotType type) {
        super(inventory, id, xPos, yPos);
        this.type = type;
        lensBench = (TileEntityLensBench) inventory;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(type == LENS) return stack.getItem() instanceof ILens;
        else if (type == UPGRADE) return LaserRegistry.isItemUpgrade(stack);
        else if(type == GEM) return true;
        return false;
    }

    public enum SlotType{
        LENS,
        UPGRADE,
        GEM
    }
}
