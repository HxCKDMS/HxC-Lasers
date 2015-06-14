package HxCKDMS.HxCLasers.Containers;

import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerLensMaker extends Container {
    public TileEntityLensMaker tileEntityLensMaker;

    public ContainerLensMaker(EntityPlayer player, TileEntityLensMaker tileEntity) {
        bindPlayerInventory(player.inventory);

        tileEntityLensMaker = tileEntity;
    }



    private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }


}
