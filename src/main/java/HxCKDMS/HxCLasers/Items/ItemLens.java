package HxCKDMS.HxCLasers.Items;

import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.awt.*;

public class ItemLens extends Item implements ILens{
    public ItemLens(CreativeTabs creativeTabs) {
        setUnlocalizedName("ItemLens");
        setCreativeTab(creativeTabs);
        setMaxStackSize(1);
    }

    @Override
    public Color getColor(ItemStack stack) {
        if(!stack.hasTagCompound()) return Color.white;
        return new Color(stack.stackTagCompound.getInteger("Red"), stack.stackTagCompound.getInteger("Green"), stack.stackTagCompound.getInteger("Blue"));
    }

    @Override
    public ItemStack setColor(ItemStack stack, Color color) {
        if(!stack.hasTagCompound()){
            stack.stackTagCompound = new NBTTagCompound();
        }

        stack.stackTagCompound.setInteger("Red", color.getRed());
        stack.stackTagCompound.setInteger("Green", color.getGreen());
        stack.stackTagCompound.setInteger("Blue", color.getBlue());

        return stack;
    }

    @Override
    public LensUpgrade[] getUpgrades(ItemStack stack) {
        if(!stack.hasTagCompound()) return new LensUpgrade[0];

        NBTTagList tagList = stack.stackTagCompound.getTagList("LensUpgrades", 10);
        LensUpgrade[] lensUpgrades = new LensUpgrade[6];

        for(int i = 0; i < tagList.tagCount(); i++){
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            lensUpgrades[i] = new LensUpgrade(LensUpgrade.UpgradeType.valueOf(tagCompound.getString("Type")), tagCompound.getInteger("Amount"));
        }
        return lensUpgrades;
    }

    @Override
    public ItemStack setUpgrades(ItemStack stack, LensUpgrade[] lensUpgrades) {
        if(!stack.hasTagCompound()){
            stack.stackTagCompound = new NBTTagCompound();
        }

        NBTTagList tagList = new NBTTagList();
        for (LensUpgrade lensUpgrade : lensUpgrades) {
            if(lensUpgrade != null){
                NBTTagCompound tagCompound = new NBTTagCompound();

                tagCompound.setInteger("Amount", lensUpgrade.getAmount());
                tagCompound.setString("Type", lensUpgrade.getType().toString());

                tagList.appendTag(tagCompound);
            }
        }
        stack.stackTagCompound.setTag("LensUpgrades", tagList);

        return stack;
    }
}
