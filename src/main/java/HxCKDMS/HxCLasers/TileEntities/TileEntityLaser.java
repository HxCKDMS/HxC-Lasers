package HxCKDMS.HxCLasers.TileEntities;

import HxCKDMS.HxCLasers.Api.ILaser;
import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LaserRegistry;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;
import java.util.UUID;

public class TileEntityLaser extends TileEntity implements ILaser {
    public boolean isPowered = false;
    public UUID uuid;

    public ItemStack lens;

    public TileEntityLaser() {}

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote){
            isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
            if(uuid == null) uuid = UUID.randomUUID();

            Color color = Color.white;
            if(lens != null){
                color = ((ILens) lens.getItem()).getColor(lens);
            }
            LaserHandler laserHandler = LaserRegistry.getLaserHandler(color);

            if(isPowered && laserHandler.canBePlacedFromBlock(color, this)) {
                ForgeDirection direction = ForgeDirection.getOrientation(getBlockMetadata());

                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, xCoord + 0.5 + direction.offsetX, yCoord + direction.offsetY, zCoord + 0.5 + direction.offsetZ, uuid, direction, 4, color));
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        isPowered = tagCompound.getBoolean("IsPowered");
        uuid = UUID.fromString(tagCompound.getString("UUID"));
        lens = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("Lens"));

        super.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setBoolean("IsPowered", isPowered);
        tagCompound.setString("UUID", uuid.toString());
        if(lens != null) tagCompound.setTag("Lens", lens.writeToNBT(new NBTTagCompound()));

        super.writeToNBT(tagCompound);
    }

    @Override
    public boolean isOn() {
        return isPowered;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
