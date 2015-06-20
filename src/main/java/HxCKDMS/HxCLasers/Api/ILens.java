package HxCKDMS.HxCLasers.Api;

import net.minecraft.item.ItemStack;

import java.awt.*;

@SuppressWarnings("unused")
public interface ILens {
    Color getColor(ItemStack stack);
    ItemStack setColor(ItemStack stack, Color color);

    LensUpgrade[] getUpgrades(ItemStack itemStack);
    ItemStack setUpgrades(ItemStack stack, LensUpgrade[] lensUpgrades);
}
