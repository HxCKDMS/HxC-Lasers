package HxCKDMS.HxCLasers.Blocks;

import HxCKDMS.HxCLasers.HxCLasers;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensBench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockLensBench extends BlockContainer {
    @SideOnly(Side.CLIENT)
    private IIcon[] bSidedTextureArray;

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

    @Override
    public void registerIcons(IIconRegister register) {
        bSidedTextureArray = new IIcon[6];

        bSidedTextureArray[0] = register.registerIcon("hxclasers" + ":blockLensBenchBottom");
        bSidedTextureArray[1] = register.registerIcon("hxclasers" + ":blockLensBenchTop");
        bSidedTextureArray[2] = register.registerIcon("hxclasers" + ":blockLensBenchFront");
        bSidedTextureArray[3] = register.registerIcon("hxclasers" + ":blockLensBenchSides");
        bSidedTextureArray[4] = register.registerIcon("hxclasers" + ":blockLensBenchSides");
        bSidedTextureArray[5] = register.registerIcon("hxclasers" + ":blockLensBenchSides");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return bSidedTextureArray[side];
    }


}
