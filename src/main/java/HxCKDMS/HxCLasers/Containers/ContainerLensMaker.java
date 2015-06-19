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
        addSlotsToContainer(tileEntity);

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

    private void addSlotsToContainer(TileEntityLensMaker tileEntityLensMaker){
        addSlotToContainer(new Slot(tileEntityLensMaker, 0, 8, 62)); //Red gem slot
        addSlotToContainer(new Slot(tileEntityLensMaker, 1, 35, 62)); //Green gem slot
        addSlotToContainer(new Slot(tileEntityLensMaker, 2, 62, 62)); //Blue gem slot

        //3-8 update slots
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 2; y++){
                addSlotToContainer(new Slot(tileEntityLensMaker, 3 + x + y * 2, 82 + x * 20, 5 + y * 19));
            }
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }


}
