package HxCKDMS.HxCLasers.Items;

import HxCKDMS.HxCLasers.Api.ILens;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import HxCKDMS.HxCLasers.Registry.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.awt.*;
import java.util.*;
import java.util.List;

import static HxCKDMS.HxCLasers.Api.LensUpgrade.UpgradeType.*;

public class ItemLens extends Item implements ILens{
    public ItemLens(CreativeTabs creativeTabs) {
        setUnlocalizedName("ItemLens");
        setCreativeTab(creativeTabs);
        setMaxStackSize(1);
        setTextureName("hxclasers:itemLens");
    }

    @Override
    public Color getColor(ItemStack stack) {
        if (!stack.hasTagCompound()) return Color.white;
        return new Color(stack.stackTagCompound.getInteger("Red"), stack.stackTagCompound.getInteger("Green"), stack.stackTagCompound.getInteger("Blue"));
    }

    @Override
    public ItemStack setColor(ItemStack stack, Color color) {
        if (!stack.hasTagCompound()){
            stack.stackTagCompound = new NBTTagCompound();
        }

        stack.stackTagCompound.setInteger("Red", color.getRed());
        stack.stackTagCompound.setInteger("Green", color.getGreen());
        stack.stackTagCompound.setInteger("Blue", color.getBlue());

        return stack;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int meta) {
        return getColor(stack).getRGB();
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

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean flag) {
        tooltips.add(String.format("power: %d", getPowerLevel(stack)));
        tooltips.add(String.format("range: %d", getRange(stack)));
        if (isTransparent(stack)) tooltips.add("transparent");
        if (isAdvanced(stack)) tooltips.add("advanced");
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("Aesthetics")) tooltips.add(String.format("frame: %s", stack.stackTagCompound.getString("Aesthetics")));
    }

    @Override
    public int getPowerLevel(ItemStack itemStack) {
        int addPower = Math.max(Math.min(Arrays.asList(Registry.itemLens.getUpgrades(itemStack)).stream().filter(u -> u != null && u.getType() == POWER).mapToInt(LensUpgrade::getAmount).sum(), 8), 0);
        return 1 + addPower;
    }

    @Override
    public int getRange(ItemStack itemStack) {
        int addRange = Arrays.asList(Registry.itemLens.getUpgrades(itemStack)).stream().filter(u -> u != null && u.getType() == RANGE).mapToInt(LensUpgrade::getAmount).sum();
        return 10 + addRange;
    }

    @Override
    public boolean isTransparent(ItemStack itemStack) {
        return Arrays.asList(Registry.itemLens.getUpgrades(itemStack)).stream().anyMatch(u -> u != null && u.getType() == TRANSPARENCY);
    }

    @Override
    public boolean isAdvanced(ItemStack itemStack) {
        return Arrays.asList(Registry.itemLens.getUpgrades(itemStack)).stream().anyMatch(u -> u != null && u.getType() == ADVANCED);
    }
}
