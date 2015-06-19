package HxCKDMS.HxCLasers.Items;

import HxCKDMS.HxCLasers.Api.ILens;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class ItemLens extends Item implements ILens{
    public ItemLens(CreativeTabs creativeTabs) {
        setUnlocalizedName("ItemLens");
        setCreativeTab(creativeTabs);
    }

    @Override
    public Color getColor(ItemStack stack) {
        if(!stack.hasTagCompound()){
            stack.stackTagCompound = new NBTTagCompound();
        }
        return new Color(stack.stackTagCompound.getInteger("Red"), stack.stackTagCompound.getInteger("Green"), stack.stackTagCompound.getInteger("Blue"));
    }

    @Override
    public void setColor(ItemStack stack, Color color) {
        if(!stack.hasTagCompound()){
            stack.stackTagCompound = new NBTTagCompound();
        }

        stack.stackTagCompound.setInteger("Red", color.getRed());
        stack.stackTagCompound.setInteger("Green", color.getGreen());
        stack.stackTagCompound.setInteger("Blue", color.getBlue());
    }
}
