package HxCKDMS.HxCLasers.Slots;

import HxCKDMS.HxCLasers.Api.LaserRegistry;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import HxCKDMS.HxCLasers.Registry.Registry;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensBench;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.awt.*;

import static HxCKDMS.HxCLasers.Slots.SlotLensBench.SlotType.*;

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
        if(type == OUTPUT) return false;
        else if (type == UPGRADE) return LaserRegistry.isItemUpgrade(stack) || (stack.getItem() == Items.dye && (stack.getMetadata() == 1 || stack.getMetadata() == 2 || stack.getMetadata() == 4 || stack.getMetadata() == 5 || stack.getMetadata() == 6 || stack.getMetadata() == 11));
        else if(type == GLASS) return Block.getBlockFromItem(stack.getItem()) == Blocks.glass;
        else if (type == AESTHETICS) return true;
        return false;
    }

    @Override
    public void onSlotChanged() {
        if (lensBench.getStackInSlot(0) != null) {
            ItemStack stack = new ItemStack(Registry.itemLens);
            LensUpgrade[] upgrades = new LensUpgrade[4];
            int dye = 0;

            for (int i = 2; i <= 5; i++) {
                ItemStack tmpStack = lensBench.getStackInSlot(i);
                if (tmpStack == null) continue;

                if (LaserRegistry.isItemUpgrade(tmpStack)) upgrades[i - 2] = LaserRegistry.getLaserUpgrade(tmpStack);
                else if (tmpStack.getItem() == Items.dye) dye = tmpStack.getMetadata();
            }

            stack = Registry.itemLens.setUpgrades(stack, upgrades);
            if (lensBench.getStackInSlot(6) != null) stack.stackTagCompound.setString("Aesthetics", lensBench.getStackInSlot(6).getDisplayName());
            Registry.itemLens.setColor(stack, getColorFromDye(dye));
            stack.stackSize = 1;

            lensBench.setInventorySlotContents(1, stack);
        } else lensBench.setInventorySlotContents(1, null);

        super.onSlotChanged();
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        if (type == OUTPUT) {
            for (int i = 0; i <= 6; i++) {
                ItemStack tmpStack = lensBench.getStackInSlot(i);
                if (tmpStack == null) continue;
                lensBench.decrStackSize(i, 1);
            }
        }
    }

    private Color getColorFromDye(int metadata) {
        switch (metadata) {
            case 1: return Color.RED;
            case 2: return Color.GREEN;
            case 4: return Color.BLUE;
            case 5: return new Color(128, 0, 128);
            case 6: return Color.CYAN;
            case 11: return Color.yellow;
            default: return Color.WHITE;
        }
    }

    public enum SlotType{
        GLASS,
        UPGRADE,
        OUTPUT,
        AESTHETICS
    }
}
