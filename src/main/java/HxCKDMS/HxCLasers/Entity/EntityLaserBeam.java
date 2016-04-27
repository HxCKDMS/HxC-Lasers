package HxCKDMS.HxCLasers.Entity;

import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LaserRegistry;
import HxCKDMS.HxCLasers.Registry.Registry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
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
    public ItemStack lens;
    public int powerLevel;
    public boolean transparent;

    private LaserHandler laserHandler;
    private boolean first = true;

    public EntityLaserBeam(World world) {
        super(world);
    }

    public EntityLaserBeam(World world, double x, double y, double z, UUID uuid, ForgeDirection direction, int distanceExtending, ItemStack lens) {
        super(world);
        setPosition(x, y, z);
        this.uuid = uuid;
        this.direction = direction;
        this.distanceExtending = distanceExtending;
        this.lens = lens;
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(24, (byte) 0); //transparent
        dataWatcher.addObject(25, 1); //powerLevel
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
            Color color = Color.white;
            if(lens != null && lens.hasTagCompound()) color = ((ILens) lens.getItem()).getColor(lens);

            if(first) {
                laserHandler = LaserRegistry.getLaserHandler(color);
                transparent = lens != null && Registry.itemLens.isTransparent(lens);
                powerLevel = lens != null ? Registry.itemLens.getPowerLevel(lens) : 1;
                laserHandler.laserBeam = this;
                first = false;
            }

            if(!laserHandler.canExist()) setDead();

            shouldDrawTop = distanceExtending == 0;

            if(distanceExtending > 0 && laserHandler.canBePlaced()){
                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, posX + direction.offsetX, posY + direction.offsetY, posZ + direction.offsetZ, uuid, direction, distanceExtending - 1, lens));
            }


            laserHandler.handleCollision();

            dataWatcher.updateObject(24, transparent ? (byte) 1 : (byte) 0);
            dataWatcher.updateObject(25, powerLevel);

            dataWatcher.updateObject(26, color.getRed());
            dataWatcher.updateObject(27, color.getGreen());
            dataWatcher.updateObject(28, color.getBlue());

            dataWatcher.updateObject(30, direction.ordinal());
            dataWatcher.updateObject(31, shouldDrawTop ? (byte) 1 : (byte) 0);

            dataWatcher.updateObject(29, (float) posY);
        } else {
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
        if(lens != null) tagCompound.setTag("Lens", lens.writeToNBT(new NBTTagCompound()));

        super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        uuid = UUID.fromString(tagCompound.getString("UUID"));
        direction = ForgeDirection.getOrientation(tagCompound.getInteger("Direction"));
        distanceExtending = tagCompound.getInteger("DistanceExtending");
        lens = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("Lens"));

        super.readFromNBT(tagCompound);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
}
