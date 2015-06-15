package HxCKDMS.HxCLasers.Entity;

import HxCKDMS.HxCLasers.Api.ILaser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.UUID;

public class EntityLaserBeam extends Entity {
    public UUID uuid;
    public ForgeDirection direction;
    public int distanceExtending;
    public boolean shouldDrawTop;

    public EntityLaserBeam(World world) {
        super(world);
        noClip = true;
        /*this.uuid = UUID.randomUUID();
        this.direction = ForgeDirection.UP;
        this.distanceExtending = 2;*/
    }

    public EntityLaserBeam(World world, double x, double y, double z, UUID uuid, ForgeDirection direction, int distanceExtending) {
        super(world);
        setPosition(x, y, z);
        noClip = true;
        this.uuid = uuid;
        this.direction = direction;
        this.distanceExtending = distanceExtending;

        dataWatcher.updateObject(30, this.direction.ordinal());
    }

    @Override
    protected void entityInit() {
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

    public boolean canExist() {
        AxisAlignedBB AABB_PREV = boundingBox.copy();
        AABB_PREV.offset(direction.getOpposite().offsetX, direction.getOpposite().offsetY, direction.getOpposite().offsetZ);

        for (Object object : worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, AABB_PREV)) {
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

    public boolean canBePlaced() {
        AxisAlignedBB AABB_NEXT = boundingBox.copy();
        AABB_NEXT.offset(direction.offsetX, direction.offsetY, direction.offsetZ);

        List entityList = worldObj.getEntitiesWithinAABB(EntityLaserBeam.class, AABB_NEXT);
        if(entityList.size() == 0) return true;
        for(Object object : entityList){
            if (object instanceof EntityLaserBeam) {
                EntityLaserBeam entityLaserBeam = (EntityLaserBeam) object;
                if (entityLaserBeam.uuid != uuid) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onUpdate() {
        if(!worldObj.isRemote){
            if(!canExist()) setDead();

            if(distanceExtending > 0 && canBePlaced()){
                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, posX + direction.offsetX, posY + direction.offsetY, posZ + direction.offsetZ, uuid, direction, distanceExtending - 1));
            }

            shouldDrawTop = distanceExtending == 0;

            dataWatcher.updateObject(30, direction.ordinal());
            dataWatcher.updateObject(31, shouldDrawTop ? (byte) 1 : (byte) 0);
        }
        super.onUpdate();
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ) {

    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString("UUID", uuid.toString());
        tagCompound.setInteger("Direction", direction.ordinal());
        tagCompound.setInteger("DistanceExtending", distanceExtending);
        super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        uuid = UUID.fromString(tagCompound.getString("UUID"));
        direction = ForgeDirection.getOrientation(tagCompound.getInteger("Direction"));
        distanceExtending = tagCompound.getInteger("DistanceExtending");
        super.readFromNBT(tagCompound);
    }


}
