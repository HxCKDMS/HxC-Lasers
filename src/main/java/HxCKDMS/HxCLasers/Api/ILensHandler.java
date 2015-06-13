package HxCKDMS.HxCLasers.Api;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface ILensHandler {
    void entityInterract(LensUpgrade[] lensUpgrades, Entity entity);
    void blockInterract(LensUpgrade[] lensUpgrades, int x, int y, int z, World world);
}
