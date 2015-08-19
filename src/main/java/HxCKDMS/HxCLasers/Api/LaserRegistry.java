package HxCKDMS.HxCLasers.Api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.HashMap;

public class LaserRegistry {
    private static HashMap<ItemStack, LensUpgrade> laserUpgrades = new HashMap<>();
    private static HashMap<Integer, Class<? extends LaserHandler>> laserHandlers = new HashMap<>();

    //LENS HANDLERS
    public static void registerLaserHandler(Color color, Class<? extends LaserHandler> lensHandlerClass){
        laserHandlers.put(color.getRGB(), lensHandlerClass);
    }

    public static LaserHandler getLaserHandler(Color color){
        try{
            return laserHandlers.get(color.getRGB()).newInstance();
        }catch (Exception unhandled){
            return new LaserHandler();
        }
    }

    //LENS UPGRADES
    public static void registerLaserUpgrade(Item item, LensUpgrade lensUpgrade){
        registerLaserUpgrade(new ItemStack(item), lensUpgrade);
    }

    public static void registerLaserUpgrade(Block block, LensUpgrade lensUpgrade){
        registerLaserUpgrade(new ItemStack(block), lensUpgrade);
    }

    public static void registerLaserUpgrade(ItemStack stack, LensUpgrade lensUpgrade){
        laserUpgrades.put(stack, lensUpgrade);
    }

    public static LensUpgrade getLaserUpgrade(Item item){
        return getLaserUpgrade(new ItemStack(item));
    }

    public static LensUpgrade getLaserUpgrade(Block block){
        return getLaserUpgrade(new ItemStack(block));
    }

    public static LensUpgrade getLaserUpgrade(ItemStack stack){
        if(stack == null) return null;
        for(HashMap.Entry<ItemStack, LensUpgrade> entry : laserUpgrades.entrySet())
            if (matchItemStacks(stack, entry.getKey())) return laserUpgrades.get(entry.getKey());
        return null;
    }

    public static boolean isItemUpgrade(ItemStack stack){
        return getLaserUpgrade(stack) != null;
    }

    //GETTERS
    public static HashMap<ItemStack, LensUpgrade> getLaserUpgrades() {
        return laserUpgrades;
    }

    public static HashMap<Integer, Class<? extends LaserHandler>> getLaserHandlers() {
        return laserHandlers;
    }

    //HELPERS
    private static boolean matchItemStacks(ItemStack stack1, ItemStack stack2){
        return (stack1.getItem() == stack2.getItem() && stack1.getMetadata() == stack2.getMetadata() && stack1.stackTagCompound == stack2.stackTagCompound);
    }
}
