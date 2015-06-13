package HxCKDMS.HxCLasers.Registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabHxCLasers extends CreativeTabs {
    public CreativeTabHxCLasers(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        return Registry.itemLens;
    }
}
