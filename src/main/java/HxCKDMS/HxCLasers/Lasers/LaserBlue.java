package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import net.minecraft.entity.Entity;

public class LaserBlue extends LaserHandler {
    @Override
    public void entityInteract(LensUpgrade[] lensUpgrades, Entity entity) {
        if(!entity.onGround && entity.motionY < 0.0D) {
            entity.motionY *= 0.6D;
            entity.fallDistance = 0;
        }
    }
}
