package HxCKDMS.HxCLasers.Blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLensMaker extends BlockContainer {
    public BlockLensMaker(Material material, CreativeTabs creativeTabs) {
        super(material);
        setCreativeTab(creativeTabs);
        setBlockName("BlockLensMaker");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return null;
    }
}
