package HxCKDMS.HxCLasers.Registry;

import HxCKDMS.HxCLasers.Blocks.BlockLaser;
import HxCKDMS.HxCLasers.Blocks.BlockLensMaker;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import HxCKDMS.HxCLasers.HxCLasers;
import HxCKDMS.HxCLasers.Items.ItemLens;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLaser;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;

import static HxCKDMS.HxCLasers.Lib.References.*;

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
        registerEntity(EntityLaserBeam.class, "EntityLaserBeam", 0xFF0000, 0xFF00FF);
    }

    @SuppressWarnings("unchecked")
    private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor1, int eggColor2){
        int entityID = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID);
        EntityRegistry.registerModEntity(entityClass, entityName, entityID, HxCLasers.instance, 64, 1, false);
        EntityList.entityEggs.put(entityID, new EntityList.EntityEggInfo(entityID, eggColor1, eggColor2));
    }
}
