package HxCKDMS.HxCLasers.Entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityLaserBeam extends EntityLiving {

    public EntityLaserBeam(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        System.out.println("test");
        this.dataWatcher.addObject(12, (byte) 0);
    }

    @Override
    public void applyEntityCollision(Entity p_70108_1_) {
        super.applyEntityCollision(p_70108_1_);
    }

    @Override
    public void onUpdate() {
        System.out.println("test2");
        super.onUpdate();
    }


}
