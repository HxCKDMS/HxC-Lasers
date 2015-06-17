package HxCKDMS.HxCLasers.TileEntities;

import HxCKDMS.HxCLasers.Api.ILaser;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.UUID;

public class TileEntityLaser extends TileEntity implements ILaser {
    boolean isPowered = false;
    UUID uuid;

    public TileEntityLaser() {}

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote){
            isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
            if(uuid == null) uuid = UUID.randomUUID();

            if(isPowered && canPlaceLaser()) {
                ForgeDirection direction = ForgeDirection.getOrientation(getBlockMetadata());

                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, xCoord + 0.5 + direction.offsetX, yCoord + direction.offsetY, zCoord + 0.5 + direction.offsetZ, uuid, direction, 4));
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        isPowered = tagCompound.getBoolean("IsPowered");
        uuid = UUID.fromString(tagCompound.getString("UUID"));

        super.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setBoolean("IsPowered", isPowered);
        tagCompound.setString("UUID", uuid.toString());

        super.writeToNBT(tagCompound);
    }

    @Override
    public boolean isOn() {
        return isPowered;
    }

    @Override
    public boolean canPlaceLaser() {
        ForgeDirection direction = ForgeDirection.getOrientation(getBlockMetadata());

        Block block = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
        if(block.isOpaqueCube()) return false;

        block = worldObj.getBlock(xCoord, yCoord, zCoord);

        AxisAlignedBB axisAlignedBB = block.getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord);
        axisAlignedBB.offset(direction.offsetX, direction.offsetY, direction.offsetZ);

        List entityList = worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, axisAlignedBB);
        if(entityList.size() == 0) return true;

        boolean canBePlaced = true;

        for(Object object : entityList){
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid.toString().equals(uuid.toString())) {
                    canBePlaced = false;
                }
            }
        }
        return canBePlaced;
    }
}
