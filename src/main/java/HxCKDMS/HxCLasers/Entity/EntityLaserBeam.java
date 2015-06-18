package HxCKDMS.HxCLasers.Entity;

import HxCKDMS.HxCLasers.Api.ILaser;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
        isAirBorne = true;
    }

    @Override
    protected void entityInit() {
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

    public boolean canExist() {
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

    public boolean canBePlaced() {
        Block block = worldObj.getBlock((int)Math.floor(posX) + direction.offsetX, (int)Math.floor(posY) + direction.offsetY, (int)Math.floor(posZ) + direction.offsetZ);
        if(block.isOpaqueCube()) return false;

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

    @Override
    public void onUpdate() {
        if(!worldObj.isRemote){
            if(!canExist()) setDead();

            if(distanceExtending > 0 && canBePlaced()){
                worldObj.spawnEntityInWorld(new EntityLaserBeam(worldObj, posX + direction.offsetX, posY + direction.offsetY, posZ + direction.offsetZ, uuid, direction, distanceExtending - 1));
            }

            AxisAlignedBB axisAlignedBB = AxisAlignedBB.getBoundingBox(boundingBox.minX + 0.4, boundingBox.minY, boundingBox.minZ + 0.4, boundingBox.maxX - 0.4, boundingBox.maxY, boundingBox.maxZ - 0.4);

            List entityList = worldObj.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB);

            for(Object object : entityList){
                if(object instanceof EntityLiving){
                    EntityLiving living = (EntityLiving) object;
                    living.setFire(5);
                }
            }

            shouldDrawTop = distanceExtending == 0;

            dataWatcher.updateObject(30, direction.ordinal());
            dataWatcher.updateObject(31, shouldDrawTop ? (byte) 1 : (byte) 0);

            dataWatcher.updateObject(29, (float) posY);
        }else{
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
        super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        uuid = UUID.fromString(tagCompound.getString("UUID"));
        direction = ForgeDirection.getOrientation(tagCompound.getInteger("Direction"));
        distanceExtending = tagCompound.getInteger("DistanceExtending");
        super.readFromNBT(tagCompound);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
}
