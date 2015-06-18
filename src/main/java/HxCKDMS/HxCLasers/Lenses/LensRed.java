package HxCKDMS.HxCLasers.Lenses;

import HxCKDMS.HxCLasers.Api.ILensHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LensRed implements ILensHandler {
    @Override
    public void entityInteract(LensUpgrade[] lensUpgrades, Entity entity) {
        entity.setFire(5);
    }

    @Override
    public void blockInteract(LensUpgrade[] lensUpgrades, int x, int y, int z, World world) {

    }
}
