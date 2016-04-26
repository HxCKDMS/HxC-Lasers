package HxCKDMS.HxCLasers.Containers;

import HxCKDMS.HxCLasers.Slots.SlotLensBench;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerLensBench extends Container {
    public TileEntityLensBench tileEntityLensBench;

    public ContainerLensBench(EntityPlayer player, TileEntityLensBench tileEntity) {
        bindPlayerInventory(player.inventory);
        addSlotsToContainer(tileEntity);

        tileEntityLensBench = tileEntity;
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 121 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 179));
        }
    }

    private void addSlotsToContainer(TileEntityLensBench tileEntityLensBench) {
        addSlotToContainer(new SlotLensBench(tileEntityLensBench, 0, 55, 52, SlotLensBench.SlotType.LENS));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
