package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;
import java.util.List;

public class LaserWhite extends LaserHandler {

    @Override
    public boolean canExist() {
        for(Object object : laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox(laserBeam.direction, laserBeam.boundingBox)))
            if(object instanceof Entity && !(object instanceof EntityLaserBeam)) return false;

        return super.canExist();
    }

    @Override
    public boolean canBePlaced() {
        for(Object object : laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox(laserBeam.direction, laserBeam.boundingBox).offset(laserBeam.direction.offsetX, laserBeam.direction.offsetY, laserBeam.direction.offsetZ))) {
            if (object instanceof Entity && !(object instanceof EntityLaserBeam)) {
                laserBeam.shouldDrawTop = true;
                return false;
            }
        }

        return super.canBePlaced();
    }

    @Override
    public boolean canBePlacedFromBlock(Color color, TileEntityLaser tileEntityLaser) {
        int xCoord = tileEntityLaser.xCoord;
        int yCoord = tileEntityLaser.yCoord;
        int zCoord = tileEntityLaser.zCoord;
        World worldObj = tileEntityLaser.getWorldObj();

        ForgeDirection direction = ForgeDirection.getOrientation(tileEntityLaser.getBlockMetadata());

        AxisAlignedBB axisAlignedBB = worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord);
        axisAlignedBB.offset(direction.offsetX, direction.offsetY, direction.offsetZ);

        List entityList = tileEntityLaser.getWorldObj().getEntitiesWithinAABB(Entity.class, getLaserBoundingBox(direction, axisAlignedBB));

        for(Object object : entityList) if (object instanceof Entity && !(object instanceof EntityLaserBeam)) return false;

        return super.canBePlacedFromBlock(color, tileEntityLaser);
    }


}
