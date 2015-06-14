package HxCKDMS.HxCLasers.Blocks;

import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLaser extends BlockContainer {
    public BlockLaser(Material material, CreativeTabs creativeTab) {
        super(material);
        setBlockName("BlockLaser");
        setCreativeTab(creativeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityLaser();
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }
}
