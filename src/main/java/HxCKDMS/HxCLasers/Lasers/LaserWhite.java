package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.awt.*;

public class LaserWhite extends LaserHandler {

    @Override
    public boolean canExist() {
        for(Object object : laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox()))
            if(areColorsEqual(laserBeam.color, Color.WHITE) && object instanceof Entity && !(object instanceof EntityLaserBeam)) return false;

        return super.canExist();
    }

    @Override
    public boolean canBePlaced() {
        for(Object object : laserBeam.worldObj.getEntitiesWithinAABB(Entity.class, getLaserBoundingBox().offset(laserBeam.direction.offsetX, laserBeam.direction.offsetY, laserBeam.direction.offsetZ))) {
            if (areColorsEqual(laserBeam.color, Color.WHITE) && object instanceof Entity && !(object instanceof EntityLaserBeam)) {
                laserBeam.shouldDrawTop = true;
                return false;
            }
        }

        return super.canBePlaced();
    }

    @Override
    public void entityInteract(LensUpgrade[] lensUpgrades, Entity entity) {

    }

    @Override
    public void blockInteract(LensUpgrade[] lensUpgrades, int x, int y, int z, World world) {

    }
}
