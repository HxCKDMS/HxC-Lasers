package HxCKDMS.HxCLasers.Client.Rendering.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EntityLaserRenderer extends RenderEntity{
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();
        {GL11.glTranslated(x, y, z);

            tessellator.addVertex(0, 0, 0); //Origin
            tessellator.addVertex(0, 1, 0); //1 unit up
            tessellator.addVertex(1, 1, 0); //1 unit up and to the right
            tessellator.addVertex(1, 0, 0); //1 unit to the right

        }
        tessellator.draw();

        super.doRender(entity, x, y, z, yaw, pitch);
    }
}