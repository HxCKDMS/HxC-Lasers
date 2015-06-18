package HxCKDMS.HxCLasers.Api;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface ILensHandler {
    void entityInteract(LensUpgrade[] lensUpgrades, Entity entity);
    void blockInteract(LensUpgrade[] lensUpgrades, int x, int y, int z, World world);
}
