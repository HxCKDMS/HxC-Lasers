package HxCKDMS.HxCLasers.TileEntities;

import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Api.LaserRegistry;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import HxCKDMS.HxCLasers.Items.ItemLens;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.awt.*;

public class TileEntityLensMaker extends TileEntity implements ISidedInventory {
    public float red_percentage = 0;
    public float green_percentage = 0;
    public float blue_percentage = 0;
    public boolean canStart = false;

    private ItemStack[] slots = new ItemStack[10];

    public int ticksRemaining = 0;

    @Override
    public int[] getSlotsForFace(int side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        return isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return slot == 9;
    }

    @Override
    public int getSizeInventory() {
        return slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slots[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack itemStack = getStackInSlot(slot);
        if(itemStack != null){
            if(itemStack.stackSize <= amount){
                setInventorySlotContents(slot, null);
            }else{
                itemStack = itemStack.splitStack(amount);
                if(itemStack.stackSize == 0){
                    setInventorySlotContents(slot, null);
                }
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack itemStack = getStackInSlot(slot);
        if(itemStack != null){
            setInventorySlotContents(slot, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        slots[slot] = itemStack;
        if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()){
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "LensMaker";
    }

    @Override
    public boolean isCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote){
            if(canStart){
                ticksRemaining = 60;
                canStart = false;
            }

            if(ticksRemaining > 0){
                ticksRemaining--;

                System.out.println(ticksRemaining);

                if(ticksRemaining == 0 && slots[9] != null){
                    slots[9] = ((ItemLens)slots[9].getItem()).setColor(slots[9], new Color((int)(red_percentage * 255), (int)(green_percentage * 255), (int)(blue_percentage * 255)));
                    LensUpgrade[] lensUpgrades = new LensUpgrade[6];
                    for(int i = 3; i <= 8; i++){
                        lensUpgrades[i - 3] = LaserRegistry.getLaserUpgrade(slots[i]);
                        slots[i] = null;
                    }
                    slots[9] = ((ItemLens)slots[9].getItem()).setUpgrades(slots[9], lensUpgrades);
                }
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return slot != 9 || itemStack.getItem() instanceof ILens;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        readSyncableDataFromNBT(tagCompound);

        NBTTagList tagList = tagCompound.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < slots.length) {
                slots[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

    public void readSyncableDataFromNBT(NBTTagCompound tagCompound){
        red_percentage = tagCompound.getFloat("Red");
        green_percentage = tagCompound.getFloat("Green");
        blue_percentage = tagCompound.getFloat("Blue");
        ticksRemaining = tagCompound.getInteger("TicksRemaining");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        writeSyncableDataToNBT(tagCompound);

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < slots.length; i++) {
            ItemStack stack = slots[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        tagCompound.setTag("Inventory", itemList);
    }

    public void writeSyncableDataToNBT(NBTTagCompound tagCompound){
        tagCompound.setFloat("Red", red_percentage);
        tagCompound.setFloat("Green", green_percentage);
        tagCompound.setFloat("Blue", blue_percentage);
        tagCompound.setInteger("TicksRemaining", ticksRemaining);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeSyncableDataToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readSyncableDataFromNBT(pkt.getNbtCompound());
    }
}
