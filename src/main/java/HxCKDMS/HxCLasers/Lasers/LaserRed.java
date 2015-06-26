package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LaserRed extends LaserHandler {

    @Override
    public void entityInteract(LensUpgrade[] lensUpgrades, Entity entity) {
        entity.setFire(5);
    }

    @Override
    public void blockInteract(LensUpgrade[] lensUpgrades, int x, int y, int z, World world) {

    }
}
