package HxCKDMS.HxCLasers.Blocks;

import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Lib.References;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLaser extends BlockContainer {
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockLaser(Material material, CreativeTabs creativeTab) {
        super(material);
        setUnlocalizedName("BlockLaser");
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[2];

        icons[0] = iconRegister.registerIcon(References.RESOURCE_LOCATION + "blockLaserSides");
        icons[1] = iconRegister.registerIcon(References.RESOURCE_LOCATION + "blockLaserTop");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == meta ? icons[1] : icons[0];
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        world.setBlockMetadataWithNotify(x, y, z, BlockPistonBase.determineOrientation(world, x, y, z, player), 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float bx, float by, float bz) {
        TileEntityLaser tileEntityLaser = (TileEntityLaser) world.getTileEntity(x, y, z);
        if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ILens) tileEntityLaser.lens = player.inventory.getCurrentItem();
        return true;
    }
}
