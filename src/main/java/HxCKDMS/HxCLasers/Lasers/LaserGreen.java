package HxCKDMS.HxCLasers.Lasers;

import HxCKDMS.HxCLasers.Api.LaserHandler;
import HxCKDMS.HxCLasers.Api.LensUpgrade;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class LaserGreen extends LaserHandler {
    Random random = new Random();

    @Override
    public void blockInteract(LensUpgrade[] lensUpgrades, int x, int y, int z, World world) {
        if(random.nextInt(1) == 0){
            Block block = world.getBlock(x, y, z);
            if(block instanceof IGrowable || block instanceof IPlantable) {
                if(block instanceof BlockSapling) block.updateTick(world, x, y, z, new Random());
                else block.updateTick(world, x, y, z, new FixedRandom());
            }
        }
    }

    private class FixedRandom extends Random{
        @Override
        public int nextInt(int bound) {
            return 0;
        }
    }
}
