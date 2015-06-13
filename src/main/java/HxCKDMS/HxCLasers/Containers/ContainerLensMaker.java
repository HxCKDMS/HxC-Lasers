package HxCKDMS.HxCLasers.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerLensMaker extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
