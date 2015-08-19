package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import HxCKDMS.HxCLasers.Items.ItemLens;
import net.minecraft.entity.Entity;

public class LaserBlue extends LaserHandler {
    @Override
    public void entityInteract(Entity entity) {
        //if(!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode) {
            boolean isAdvanced = false;
            int power = 1;
            for (LensUpgrade lensUpgrade : ((ItemLens) laserBeam.lens.getItem()).getUpgrades(laserBeam.lens)) {
                if (lensUpgrade == null) continue;
                if (lensUpgrade.getType() == LensUpgrade.UpgradeType.POWER) power += lensUpgrade.getAmount();
                if (lensUpgrade.getType() == LensUpgrade.UpgradeType.ADVANCED) isAdvanced = true;
            }
            float slowDown = (0.25F - (0.025F * power));
            if (slowDown > 0.9F) slowDown = 0.9F;
            if (slowDown < 0.1F) slowDown = 0.1F;

            entity.motionX *= slowDown;
            entity.motionZ *= slowDown;
            if (isAdvanced) {
                entity.motionY *= slowDown;
                entity.fallDistance = 0;
            }
            entity.velocityChanged = true;
        //}
    }
}
