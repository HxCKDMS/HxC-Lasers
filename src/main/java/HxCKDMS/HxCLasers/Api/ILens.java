package HxCKDMS.HxCLasers.Api;

import net.minecraft.item.ItemStack;

import java.awt.*;

public interface ILens {
    Color getColor(ItemStack stack);

    void setColor(ItemStack stack, Color color);
}
