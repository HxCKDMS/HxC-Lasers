package HxCKDMS.HxCLasers.Api;

import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
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
        if (block != Blocks.air && block != Blocks.glass) return false;

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
        if (block != Blocks.air && block != Blocks.glass) {
            laserBeam.shouldDrawTop = true;
            return false;
        }

        AxisAlignedBB axisAlignedBB = laserBeam.boundingBox.copy();
        axisAlignedBB.offset(laserBeam.direction.offsetX, laserBeam.direction.offsetY, laserBeam.direction.offsetZ);

        List entityList = laserBeam.worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, axisAlignedBB);
        if(entityList.isEmpty()) return true;

        boolean canBePlaced = true;

        for(Object object : entityList){
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid.toString().equals(laserBeam.uuid.toString())) {
                    canBePlaced = false;
                    break;
                }
            }
        }
        return canBePlaced;
    }

    public boolean canBePlacedFromBlock(Color color, TileEntityLaser tileEntityLaser){
        int xCoord = tileEntityLaser.xCoord;
        int yCoord = tileEntityLaser.yCoord;
        int zCoord = tileEntityLaser.zCoord;
        World worldObj = tileEntityLaser.getWorld();

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
                if (entityLaserBeam.uuid.toString().equals(tileEntityLaser.uuid.toString())) {
                    canBePlaced = false;

                    Color otherColor = Color.white;
                    if(entityLaserBeam.lens != null && entityLaserBeam.lens.hasTagCompound()) otherColor = new Color(entityLaserBeam.lens.stackTagCompound.getInteger("Red"), entityLaserBeam.lens.stackTagCompound.getInteger("Green"), entityLaserBeam.lens.stackTagCompound.getInteger("Blue"));
                    if(otherColor.getRGB() != color.getRGB()) {
                        tileEntityLaser.uuid = UUID.randomUUID();
                        return true;
                    }
                }
            }
        }
        return canBePlaced;
    }

    @SuppressWarnings("unchecked")
    public void handleCollision(){
        List entityList = laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox(laserBeam.direction, laserBeam.boundingBox, laserBeam.powerLevel));

        entityList.stream().filter(object -> object instanceof Entity && !(object instanceof EntityLaserBeam)).forEach(object -> {
            Entity entity = (Entity) object;
            entityInteract(entity);
        });

        blockInteract((int)Math.floor(laserBeam.posX), (int)Math.floor(laserBeam.posY), (int)Math.floor(laserBeam.posZ), laserBeam.worldObj);
    }

    protected static AxisAlignedBB getLaserBoundingBox(ForgeDirection direction, AxisAlignedBB boundingBox, int power) {
        double offsetX = (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN || direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) ? (-11D * power + 191D) / 400D : 0;
        double offsetY = (direction == ForgeDirection.EAST || direction == ForgeDirection.WEST || direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) ? (-11D * power + 191D) / 400D : 0;
        double offsetZ = (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN || direction == ForgeDirection.EAST || direction == ForgeDirection.WEST) ? (-11D * power + 191D) / 400D : 0;

        return AxisAlignedBB.getBoundingBox(boundingBox.minX + offsetX, boundingBox.minY + offsetY, boundingBox.minZ + offsetZ, boundingBox.maxX - offsetX, boundingBox.maxY - offsetY, boundingBox.maxZ - offsetZ);
    }

    protected static AxisAlignedBB getItemCollisionBox(AxisAlignedBB boundingBox) {
        return AxisAlignedBB.getBoundingBox(boundingBox.minX, boundingBox.minY, boundingBox.minZ , boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    }

    public void entityInteract(Entity entity) {

    }

    public void blockInteract(int x, int y, int z, World world) {

    }


    //HELPER METHODS
    @SuppressWarnings("unused")
    public static boolean areColorsEqual(Color color1, Color color2){
        return color1.getRed() == color2.getRed() && color1.getGreen() == color2.getGreen() && color1.getBlue() == color2.getBlue() && color1.getAlpha() == color2.getAlpha();
    }

    @SuppressWarnings("unused")
    public static Color blendColors(Color color1, Color color2, float ratio) {
        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;

        int i1 = color1.getRGB();
        int i2 = color2.getRGB();

        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);

        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);

        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));

        return new Color( a << 24 | r << 16 | g << 8 | b, true);
    }
}
