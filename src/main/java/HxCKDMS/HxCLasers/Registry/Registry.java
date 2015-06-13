package HxCKDMS.HxCLasers.Registry;

import HxCKDMS.HxCLasers.Blocks.BlockLensMaker;
import HxCKDMS.HxCLasers.Items.ItemLens;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import static HxCKDMS.HxCLasers.Lib.References.*;

public class Registry {
    public static final CreativeTabHxCLasers creativeTabHxCLasers = new CreativeTabHxCLasers("HxCLasers");

    //BLOCKS
    public static final Block blockLensMaker = new BlockLensMaker(Material.iron, creativeTabHxCLasers);

    //ITEMS
    public static final Item itemLens = new ItemLens(creativeTabHxCLasers);

    public static void preInit(){
        registerBlocks();
        registerItems();
        registerTileEntities();
    }

    private static void registerBlocks(){
        GameRegistry.registerBlock(blockLensMaker, "BlockLensMaker");
    }

    private static void registerItems(){
        GameRegistry.registerItem(itemLens, "ItemLens");
    }

    private static void registerTileEntities(){
        GameRegistry.registerTileEntity(TileEntityLensMaker.class, MOD_ID + "TileEntityLensMaker");
    }
}
