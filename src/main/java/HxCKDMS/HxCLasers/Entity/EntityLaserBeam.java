package HxCKDMS.HxCLasers.Entity;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LaserRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;
import java.util.UUID;

public class EntityLaserBeam extends Entity {
    public UUID uuid;
    public ForgeDirection direction;
    public int distanceExtending;
    public boolean shouldDrawTop;
    public Color color;

    private LaserHandler laserHandler;
    private boolean first = true;

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

    @Override
    public void onUpdate() {
        if(!worldObj.isRemote){
            if(first) {
                laserHandler = LaserRegistry.getLaserHandler(color);
                laserHandler.laserBeam = this;
                first = false;
            }

            if(!laserHandler.canExist()) setDead();

            shouldDrawTop = distanceExtending == 0;

            if(distanceExtending > 0 && laserHandler.canBePlaced()){
                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, posX + direction.offsetX, posY + direction.offsetY, posZ + direction.offsetZ, uuid, direction, distanceExtending - 1, color));
            }

            laserHandler.handleCollision();

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
