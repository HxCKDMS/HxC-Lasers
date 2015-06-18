package HxCKDMS.HxCLasers.Entity;

import HxCKDMS.HxCLasers.Api.ILaser;
import HxCKDMS.HxCLasers.Api.LensRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class EntityLaserBeam extends Entity {
    public UUID uuid;
    public ForgeDirection direction;
    public int distanceExtending;
    public boolean shouldDrawTop;
    public Color color;

    public EntityLaserBeam(World world) {
        super(world);
        noClip = true;
    }

    public EntityLaserBeam(World world, double x, double y, double z, UUID uuid, ForgeDirection direction, int distanceExtending, Color color) {
        super(world);
        setPosition(x, y, z);
        noClip = true;
        this.uuid = uuid;
        this.direction = direction;
        this.distanceExtending = distanceExtending;
        this.color = color;
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(26, 0); //red
        dataWatcher.addObject(27, 0); //green
        dataWatcher.addObject(28, 0); //blue

        dataWatcher.addObject(29, (float) posY);

        dataWatcher.addObject(30, 1); //direction
        dataWatcher.addObject(31, (byte) 1); //shouldDrawTop
        setSize(1, 1);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {

    }

    private boolean canExist() {
        for(Object object : worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox()))
            if(color.getRGB() == Color.WHITE.getRGB() && object instanceof Entity && !(object instanceof EntityLaserBeam)) return false;


        Block block = worldObj.getBlock((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ));
        if(block.isOpaqueCube()) return false;

        AxisAlignedBB axisAlignedBB = boundingBox.copy();
        axisAlignedBB.offset(direction.getOpposite().offsetX, direction.getOpposite().offsetY, direction.getOpposite().offsetZ);

        for (Object object : worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, axisAlignedBB)) {
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid == uuid) {
                    return true;
                }
            }
        }
        TileEntity tileEntity = worldObj.getTileEntity((int)Math.floor(posX) + direction.getOpposite().offsetX, (int)Math.floor(posY) + direction.getOpposite().offsetY, (int)Math.floor(posZ) + direction.getOpposite().offsetZ);
        return tileEntity instanceof ILaser && ((ILaser)tileEntity).isOn();
    }

    private boolean canBePlaced() {
        for(Object object : worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox().offset(direction.offsetX, direction.offsetY, direction.offsetZ)))
            if(color.getRGB() == Color.WHITE.getRGB() && object instanceof Entity && !(object instanceof EntityLaserBeam)) {
                shouldDrawTop = true;
                return false;
            }

        Block block = worldObj.getBlock((int)Math.floor(posX) + direction.offsetX, (int)Math.floor(posY) + direction.offsetY, (int)Math.floor(posZ) + direction.offsetZ);
        if(block.isOpaqueCube()) {
            shouldDrawTop = true;
            return false;
        }

        AxisAlignedBB axisAlignedBB = boundingBox.copy();
        axisAlignedBB.offset(direction.offsetX, direction.offsetY, direction.offsetZ);

        List entityList = worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, axisAlignedBB);
        if(entityList.size() == 0) return true;

        boolean canBePlaced = true;

        for(Object object : entityList){
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid == uuid) {
                    canBePlaced = false;
                }
            }
        }
        return canBePlaced;
    }

    private void handleCollision(){
        List entityList = worldObj.getEntitiesWithinAABB(Entity.class , getLaserBoundingBox());

        for(Object object : entityList){
            if(object instanceof Entity && !(object instanceof EntityLaserBeam)){
                Entity entity = (Entity) object;
                LensRegistry.getLensHandler(color).entityInteract(null, entity);
            }
        }
    }

    private AxisAlignedBB getLaserBoundingBox(){
        double offsetX = (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN || direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) ? 0.33 : 0;
        double offsetY = (direction == ForgeDirection.EAST || direction == ForgeDirection.WEST || direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) ? 0.33 : 0;
        double offsetZ = (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN || direction == ForgeDirection.EAST || direction == ForgeDirection.WEST) ? 0.33 : 0;

        return AxisAlignedBB.getBoundingBox(boundingBox.minX + offsetX, boundingBox.minY + offsetY, boundingBox.minZ + offsetZ, boundingBox.maxX - offsetX, boundingBox.maxY - offsetY, boundingBox.maxZ - offsetZ);
    }

    @Override
    public void onUpdate() {
        if(!worldObj.isRemote){
            if(!canExist()) setDead();

            shouldDrawTop = distanceExtending == 0;

            if(distanceExtending > 0 && canBePlaced()){
                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, posX + direction.offsetX, posY + direction.offsetY, posZ + direction.offsetZ, uuid, direction, distanceExtending - 1, color));
            }

            handleCollision();

            dataWatcher.updateObject(26, color.getRed());
            dataWatcher.updateObject(27, color.getGreen());
            dataWatcher.updateObject(28, color.getBlue());

            dataWatcher.updateObject(30, direction.ordinal());
            dataWatcher.updateObject(31, shouldDrawTop ? (byte) 1 : (byte) 0);

            dataWatcher.updateObject(29, (float) posY);
        }else{
            color = new Color(dataWatcher.getWatchableObjectInt(26), dataWatcher.getWatchableObjectInt(27), dataWatcher.getWatchableObjectInt(28));

            direction = ForgeDirection.getOrientation(dataWatcher.getWatchableObjectInt(30));
            shouldDrawTop = dataWatcher.getWatchableObjectByte(31) == 1;

            posY = dataWatcher.getWatchableObjectFloat(29);
            lastTickPosY = dataWatcher.getWatchableObjectFloat(29);
        }
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ) {

    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString("UUID", uuid.toString());
        tagCompound.setInteger("Direction", direction.ordinal());
        tagCompound.setInteger("DistanceExtending", distanceExtending);
        tagCompound.setInteger("Red", color.getRed());
        tagCompound.setInteger("Green", color.getGreen());
        tagCompound.setInteger("Blue", color.getBlue());

        super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        uuid = UUID.fromString(tagCompound.getString("UUID"));
        direction = ForgeDirection.getOrientation(tagCompound.getInteger("Direction"));
        distanceExtending = tagCompound.getInteger("DistanceExtending");
        color = new Color(tagCompound.getInteger("Red"), tagCompound.getInteger("Green"), tagCompound.getInteger("Blue"));
        super.readFromNBT(tagCompound);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

}
