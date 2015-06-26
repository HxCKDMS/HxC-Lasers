package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import net.minecraft.entity.Entity;

public class LaserBlue extends LaserHandler {
    @Override
    public void entityInteract(LensUpgrade[] lensUpgrades, Entity entity) {
        entity.motionY = -0.01;
    }
}
