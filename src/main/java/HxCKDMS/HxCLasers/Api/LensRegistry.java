package HxCKDMS.HxCLasers.Api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.HashMap;

@SuppressWarnings("unused")
public class LensRegistry {
    private static HashMap<ItemStack, LensUpgrade> lensUpgrades = new HashMap<>();
    private static HashMap<Color, ILensHandler> lensHandlers = new HashMap<>();

    //LENS HANDLERS
    public static void registerLensHandler(Color color, ILensHandler lensHandler){
        lensHandlers.put(color, lensHandler);
    }

    public static ILensHandler getLensHandler(Color color){
        return lensHandlers.get(color);
    }

    //LENS UPGRADES
    public static void registerLensUpgrade(Item item, LensUpgrade lensUpgrade){
        registerLensUpgrade(new ItemStack(item), lensUpgrade);
    }

    public static void registerLensUpgrade(Block block, LensUpgrade lensUpgrade){
        registerLensUpgrade(new ItemStack(block), lensUpgrade);
    }

    public static void registerLensUpgrade(ItemStack stack, LensUpgrade lensUpgrade){
        lensUpgrades.put(stack, lensUpgrade);
    }

    public static LensUpgrade getLensUpgrade(Item item){
        return getLensUpgrade(new ItemStack(item));
    }

    public static LensUpgrade getLensUpgrade(Block block){
        return getLensUpgrade(new ItemStack(block));
    }

    public static LensUpgrade getLensUpgrade(ItemStack stack){
        return lensUpgrades.get(stack);
    }

    //GETTERS
    public static HashMap<ItemStack, LensUpgrade> getLensUpgrades() {
        return lensUpgrades;
    }

    public static HashMap<Color, ILensHandler> getLensHandlers() {
        return lensHandlers;
    }


}
