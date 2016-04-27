package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import HxCKDMS.HxCLasers.Registry.Registry;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;
import java.util.List;

public class LaserWhite extends LaserHandler {

    @Override
    public boolean canExist() {
        List entityList = laserBeam.worldObj.getEntitiesWithinAABB(EntityItem.class, laserBeam.boundingBox);
        if (!entityList.isEmpty()) return false;


        for(Object object : laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox(laserBeam.direction, laserBeam.boundingBox, laserBeam.powerLevel)))
            if(object instanceof Entity && !(object instanceof EntityLaserBeam)) return false;
        return super.canExist();
    }

    @Override
    public boolean canBePlaced() {
        List entityList = laserBeam.worldObj.getEntitiesWithinAABB(EntityItem.class, laserBeam.boundingBox.copy().offset(laserBeam.direction.offsetX, laserBeam.direction.offsetY, laserBeam.direction.offsetZ));
        if (!entityList.isEmpty()) {
            laserBeam.shouldDrawTop = true;
            return false;
        }

        for(Object object : laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox(laserBeam.direction, laserBeam.boundingBox, laserBeam.powerLevel).offset(laserBeam.direction.offsetX, laserBeam.direction.offsetY, laserBeam.direction.offsetZ))) {
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
        World worldObj = tileEntityLaser.getWorld();

        ForgeDirection direction = ForgeDirection.getOrientation(tileEntityLaser.getBlockMetadata());

        AxisAlignedBB axisAlignedBB = worldObj.getBlock(xCoord, yCoord, zCoord).getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord);
        axisAlignedBB.offset(direction.offsetX, direction.offsetY, direction.offsetZ);

        List entityList = tileEntityLaser.getWorld().getEntitiesWithinAABB(EntityItem.class, getItemCollisionBox(axisAlignedBB));
        if (!entityList.isEmpty()) return false;

        entityList = tileEntityLaser.getWorld().getEntitiesWithinAABB(Entity.class, getLaserBoundingBox(direction, axisAlignedBB, tileEntityLaser.lens != null ? Registry.itemLens.getPowerLevel(tileEntityLaser.lens) : 1));
        for(Object object : entityList) if (object instanceof Entity && !(object instanceof EntityLaserBeam)) return false;

        return super.canBePlacedFromBlock(color, tileEntityLaser);
    }
}
