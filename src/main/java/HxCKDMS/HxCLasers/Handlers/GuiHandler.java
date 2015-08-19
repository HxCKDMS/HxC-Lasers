package HxCKDMS.HxCLasers.Handlers;

import HxCKDMS.HxCLasers.Client.Gui.GuiLensBench;
import HxCKDMS.HxCLasers.Client.Gui.GuiLensMaker;
import HxCKDMS.HxCLasers.Containers.ContainerLensBench;
import HxCKDMS.HxCLasers.Containers.ContainerLensMaker;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensBench;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof TileEntityLensMaker)
            return new ContainerLensMaker(player, (TileEntityLensMaker) tileEntity);
        else if(tileEntity instanceof TileEntityLensBench)
            return new ContainerLensBench(player, (TileEntityLensBench) tileEntity);

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof TileEntityLensMaker)
            return new GuiLensMaker(new ContainerLensMaker(player, (TileEntityLensMaker) tileEntity));
        else if(tileEntity instanceof TileEntityLensBench)
            return new GuiLensBench(new ContainerLensBench(player, (TileEntityLensBench) tileEntity));

        return null;
    }
}
