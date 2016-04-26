package HxCKDMS.HxCLasers.Containers;

import HxCKDMS.HxCLasers.Slots.SlotLensMaker;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
        //0-2 gem slots red -> green -> blue
        for (int x = 0; x < 3; x++){
            addSlotToContainer(new SlotLensMaker(tileEntityLensMaker, x, 8 + 27 * x, 62, SlotLensMaker.SlotType.GEM));
        }

        //3-8 upgrade slots
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 2; y++){
                addSlotToContainer(new SlotLensMaker(tileEntityLensMaker, 3 + x + y * 3, 82 + x * 20, 5 + y * 19, SlotLensMaker.SlotType.UPGRADE));
            }
        }
        addSlotToContainer(new SlotLensMaker(tileEntityLensMaker, 9, 103, 65, SlotLensMaker.SlotType.LENS)); //Lens slot
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return null;
    }
}
