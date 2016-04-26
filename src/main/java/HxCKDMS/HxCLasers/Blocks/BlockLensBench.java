package HxCKDMS.HxCLasers.Blocks;

import HxCKDMS.HxCLasers.HxCLasers;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensBench;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLensBench extends BlockContainer {
    public BlockLensBench(Material material, CreativeTabs creativeTabs) {
        super(material);
        setCreativeTab(creativeTabs);
        setUnlocalizedName("BlockLensBench");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float bx, float by, float bz) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity != null && !player.isSneaking()) {
            player.openGui(HxCLasers.instance, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityLensBench();
    }
}
