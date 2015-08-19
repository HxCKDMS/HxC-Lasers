package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import HxCKDMS.HxCLasers.Items.ItemLens;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;

public class LaserRed extends LaserHandler {
    @Override
    public void entityInteract(Entity entity) {
        boolean isAdvanced = false;
        for(LensUpgrade lensUpgrade : ((ItemLens)laserBeam.lens.getItem()).getUpgrades(laserBeam.lens)) {
            if(lensUpgrade == null) continue;
            if(lensUpgrade.getType() == LensUpgrade.UpgradeType.ADVANCED) {
                isAdvanced = true;
                break;
            }
        }
        if(isAdvanced && !(entity instanceof EntityItem)) entity.setFire(5);
        else if(!isAdvanced) entity.setFire(5);
    }
}
