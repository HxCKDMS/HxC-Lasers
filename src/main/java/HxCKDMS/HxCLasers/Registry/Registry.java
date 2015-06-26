package HxCKDMS.HxCLasers.Registry;

import HxCKDMS.HxCLasers.Api.LaserRegistry;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import HxCKDMS.HxCLasers.Blocks.BlockLaser;
import HxCKDMS.HxCLasers.Blocks.BlockLensMaker;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import HxCKDMS.HxCLasers.HxCLasers;
import HxCKDMS.HxCLasers.Items.ItemLens;
import HxCKDMS.HxCLasers.Lasers.LaserBlue;
import HxCKDMS.HxCLasers.Lasers.LaserGreen;
import HxCKDMS.HxCLasers.Lasers.LaserRed;
import HxCKDMS.HxCLasers.Lasers.LaserWhite;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.awt.*;

import static HxCKDMS.HxCLasers.Lib.References.MOD_ID;

public class Registry {
    public static final CreativeTabHxCLasers creativeTabHxCLasers = new CreativeTabHxCLasers("HxCLasers");

    //BLOCKS
    public static final Block blockLensMaker = new BlockLensMaker(Material.iron, creativeTabHxCLasers);
    public static final Block blockLaser = new BlockLaser(Material.rock, creativeTabHxCLasers);

    //ITEMS
    public static final Item itemLens = new ItemLens(creativeTabHxCLasers);

    public static void preInit(){
        registerBlocks();
        registerItems();
        registerTileEntities();
    }
    public static void init() {
        registerEntities();
        registerLenses();
        registerLensUpgrades();
    }

    private static void registerBlocks(){
        GameRegistry.registerBlock(blockLensMaker, "BlockLensMaker");
        GameRegistry.registerBlock(blockLaser, "BlockLaser");
    }

    private static void registerItems(){
        GameRegistry.registerItem(itemLens, "ItemLens");
    }

    private static void registerTileEntities(){
        GameRegistry.registerTileEntity(TileEntityLensMaker.class, MOD_ID + ":TileEntityLensMaker");
        GameRegistry.registerTileEntity(TileEntityLaser.class, MOD_ID + ":TileEntityLaser");
    }

    private static void registerEntities(){
        EntityRegistry.registerModEntity(EntityLaserBeam.class, "EntityLaserBeam", 0, HxCLasers.instance, 64, 1, false);
    }

    private static void registerLenses(){
        LaserRegistry.registerLaserHandler(Color.white, LaserWhite.class);
        LaserRegistry.registerLaserHandler(Color.red, LaserRed.class);
        LaserRegistry.registerLaserHandler(Color.green, LaserGreen.class);
        LaserRegistry.registerLaserHandler(Color.blue, LaserBlue.class);
    }

    private static void registerLensUpgrades(){
        LaserRegistry.registerLaserUpgrade(Items.redstone, new LensUpgrade(LensUpgrade.UpgradeType.POWER, 1));
        //RUBY
        LaserRegistry.registerLaserUpgrade(Items.diamond, new LensUpgrade(LensUpgrade.UpgradeType.POWER, 3));

        LaserRegistry.registerLaserUpgrade(Blocks.dirt, new LensUpgrade(LensUpgrade.UpgradeType.RANGE, -1));
        LaserRegistry.registerLaserUpgrade(Blocks.glass, new LensUpgrade(LensUpgrade.UpgradeType.RANGE, 1));
        LaserRegistry.registerLaserUpgrade(Blocks.glowstone, new LensUpgrade(LensUpgrade.UpgradeType.RANGE, 10));

        LaserRegistry.registerLaserUpgrade(Items.blaze_powder, new LensUpgrade(LensUpgrade.UpgradeType.TRANSPARENCY, 1));
    }
}
