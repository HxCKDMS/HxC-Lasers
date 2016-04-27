package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Registry.Registry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;

public class LaserRed extends LaserHandler {
    @Override
    public void entityInteract(Entity entity) {
        boolean isAdvanced = Registry.itemLens.isAdvanced(laserBeam.lens);
        if(isAdvanced && !(entity instanceof EntityItem)) entity.setFire(5);
        else if(!isAdvanced) entity.setFire(5);
    }
}
