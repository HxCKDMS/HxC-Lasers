package HxCKDMS.HxCLasers.TileEntities;

import HxCKDMS.HxCLasers.Api.ILaser;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

public class TileEntityLaser extends TileEntity implements ILaser {
    boolean prevIsPowered = false;
    boolean isPowered = false;

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote){
            //for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
                isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

            if(isPowered && !prevIsPowered) {
                ForgeDirection direction = ForgeDirection.getOrientation(getBlockMetadata());

                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, xCoord + 0.5 + direction.offsetX, yCoord + direction.offsetY, zCoord + 0.5 + direction.offsetZ, UUID.randomUUID(), direction, 2));
            }

            prevIsPowered = isPowered;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        prevIsPowered = tagCompound.getBoolean("PrevIsPowered");
        isPowered = tagCompound.getBoolean("IsPowered");

        super.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setBoolean("PrevIsPowered", prevIsPowered);
        tagCompound.setBoolean("IsPowered", isPowered);

        super.writeToNBT(tagCompound);
    }

    @Override
    public boolean isOn() {
        return isPowered;
    }
}
