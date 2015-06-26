package HxCKDMS.HxCLasers.Api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

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

        Iterator<ItemStack> iterator = laserUpgrades.keySet().iterator();
        do{
            ItemStack stack2 = iterator.next();
            if(matchItemStacks(stack, stack2)){
                return laserUpgrades.get(stack2);
            }
        }while(iterator.hasNext());

        return null;
    }

    public static boolean isItemUpgrade(ItemStack stack){
        Iterator<ItemStack> iterator = laserUpgrades.keySet().iterator();
        do{
            ItemStack stack2 = iterator.next();
            if(matchItemStacks(stack, stack2)){
                return true;
            }
        }while(iterator.hasNext());

        return false;
    }

    //GETTERS
    public static HashMap<ItemStack, LensUpgrade> getLaserUpgrades() {
        return laserUpgrades;
    }

    public static HashMap<Integer, Class<? extends LaserHandler>> getLaserHandlers() {
        return laserHandlers;
    }
    private static boolean matchItemStacks(ItemStack stack1, ItemStack stack2){
        return (stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage() && stack1.stackTagCompound == stack2.stackTagCompound);
    }
}
