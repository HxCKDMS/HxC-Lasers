package HxCKDMS.HxCLasers.Api;

import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class LaserHandler {
    public EntityLaserBeam laserBeam;

    public boolean canExist() {
        Block block = laserBeam.worldObj.getBlock((int) Math.floor(laserBeam.posX), (int) Math.floor(laserBeam.posY), (int) Math.floor(laserBeam.posZ));
        if(block.isOpaqueCube()) return false;

        AxisAlignedBB axisAlignedBB = laserBeam.boundingBox.copy();
        axisAlignedBB.offset(laserBeam.direction.getOpposite().offsetX, laserBeam.direction.getOpposite().offsetY, laserBeam.direction.getOpposite().offsetZ);

        for (Object object : laserBeam.worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, axisAlignedBB)) {
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid.toString().equals(laserBeam.uuid.toString())) {
                    return true;
                }
            }
        }
        TileEntity tileEntity = laserBeam.worldObj.getTileEntity((int)Math.floor(laserBeam.posX) + laserBeam.direction.getOpposite().offsetX, (int)Math.floor(laserBeam.posY) + laserBeam.direction.getOpposite().offsetY, (int)Math.floor(laserBeam.posZ) + laserBeam.direction.getOpposite().offsetZ);
        return tileEntity instanceof ILaser && ((ILaser)tileEntity).isOn() && ((ILaser) tileEntity).getUUID().toString().equals(laserBeam.uuid.toString());
    }

    public boolean canBePlaced() {
        Block block = laserBeam.worldObj.getBlock((int) Math.floor(laserBeam.posX) + laserBeam.direction.offsetX, (int) Math.floor(laserBeam.posY) + laserBeam.direction.offsetY, (int) Math.floor(laserBeam.posZ) + laserBeam.direction.offsetZ);
        if(block.isOpaqueCube()) {
            laserBeam.shouldDrawTop = true;
            return false;
        }

        AxisAlignedBB axisAlignedBB = laserBeam.boundingBox.copy();
        axisAlignedBB.offset(laserBeam.direction.offsetX, laserBeam.direction.offsetY, laserBeam.direction.offsetZ);

        List entityList = laserBeam.worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, axisAlignedBB);
        if(entityList.size() == 0) return true;

        boolean canBePlaced = true;

        for(Object object : entityList){
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid.toString().equals(laserBeam.uuid.toString())) {
                    canBePlaced = false;
                }
            }
        }
        return canBePlaced;
    }

    public boolean canBePlacedFromBlock(Color color, UUID uuid, TileEntityLaser tileEntityLaser){
        int xCoord = tileEntityLaser.xCoord;
        int yCoord = tileEntityLaser.yCoord;
        int zCoord = tileEntityLaser.zCoord;
        World worldObj = tileEntityLaser.getWorldObj();

        ForgeDirection direction = ForgeDirection.getOrientation(tileEntityLaser.getBlockMetadata());

        Block block = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
        if(block.isOpaqueCube()) return false;

        block = worldObj.getBlock(xCoord, yCoord, zCoord);

        AxisAlignedBB axisAlignedBB = block.getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord);
        axisAlignedBB.offset(direction.offsetX, direction.offsetY, direction.offsetZ);

        List entityList = worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, axisAlignedBB);
        if(entityList.size() == 0) return true;

        boolean canBePlaced = true;

        for(Object object : entityList) {
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid.toString().equals(uuid.toString())) {
                    canBePlaced = false;

                    if(entityLaserBeam.color.getRGB() != color.getRGB()) {
                        //isPowered = false;
                        //tileEntityLaser. = UUID.randomUUID();
                        return true;
                    }
                }
            }
        }
        return canBePlaced;
    }

    public void handleCollision(){
        List entityList = laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox());

        for(Object object : entityList){
            if(object instanceof Entity && !(object instanceof EntityLaserBeam)){
                Entity entity = (Entity) object;
                LaserRegistry.getLaserHandler(laserBeam.color).entityInteract(null, entity);
            }
        }
    }

    public AxisAlignedBB getLaserBoundingBox(){
        double offsetX = (laserBeam.direction == ForgeDirection.UP || laserBeam.direction == ForgeDirection.DOWN || laserBeam.direction == ForgeDirection.NORTH || laserBeam.direction == ForgeDirection.SOUTH) ? 0.33 : 0;
        double offsetY = (laserBeam.direction == ForgeDirection.EAST || laserBeam.direction == ForgeDirection.WEST || laserBeam.direction == ForgeDirection.NORTH || laserBeam.direction == ForgeDirection.SOUTH) ? 0.33 : 0;
        double offsetZ = (laserBeam.direction == ForgeDirection.UP || laserBeam.direction == ForgeDirection.DOWN || laserBeam.direction == ForgeDirection.EAST || laserBeam.direction == ForgeDirection.WEST) ? 0.33 : 0;

        return AxisAlignedBB.getBoundingBox(laserBeam.boundingBox.minX + offsetX, laserBeam.boundingBox.minY + offsetY, laserBeam.boundingBox.minZ + offsetZ, laserBeam.boundingBox.maxX - offsetX, laserBeam.boundingBox.maxY - offsetY, laserBeam.boundingBox.maxZ - offsetZ);
    }

    public static boolean areColorsEqual(Color color1, Color color2){
        return color1.getRed() == color2.getRed() && color1.getGreen() == color2.getGreen() && color1.getBlue() == color2.getBlue() && color1.getAlpha() == color2.getAlpha();
    }

    public void entityInteract(LensUpgrade[] lensUpgrades, Entity entity) {

    }

    public void blockInteract(LensUpgrade[] lensUpgrades, int x, int y, int z, World world) {

    }
}
