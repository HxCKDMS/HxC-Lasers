package HxCKDMS.HxCLasers.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemRuby extends Item {
    public ItemRuby(CreativeTabs creativeTabs) {
        setUnlocalizedName("ItemRuby");
        setCreativeTab(creativeTabs);
        setMaxStackSize(64);
        setTextureName("hxclasers:itemRuby");
    }
}
