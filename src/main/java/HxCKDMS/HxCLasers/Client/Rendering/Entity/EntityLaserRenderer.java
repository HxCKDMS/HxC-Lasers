package HxCKDMS.HxCLasers.Client.Rendering.Entity;

import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import HxCKDMS.HxCLasers.Lib.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class EntityLaserRenderer extends RenderEntity{
    ResourceLocation textureLaserBeam = new ResourceLocation(References.RESOURCE_LOCATION + "textures/entities/entityLaser.png");

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        doRender((EntityLaserBeam) entity, x, y, z);
    }

    private void doRender(EntityLaserBeam entityLaserBeam, double x, double y, double z){
        ForgeDirection direction = entityLaserBeam.direction;
        boolean goNorth = (direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH);
        boolean goEast = (direction == ForgeDirection.EAST || direction == ForgeDirection.WEST);

        double x_offset = goEast ? 0.5 : -0.5;
        double y_offset = goNorth ? 1 : 0;

        float x_rotation = goNorth ? 1 : 0;
        float z_rotation = goEast ? 1 : 0;

        GL11.glPushMatrix();
        {
            Tessellator tessellator = Tessellator.instance;

            GL11.glTranslated(x + x_offset, y + y_offset, z - 0.5);
            GL11.glRotatef((goEast || goNorth) ? 90 : 0, x_rotation, 0, z_rotation);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 230, 230);

            tessellator.startDrawingQuads();
            {
                tessellator.setBrightness(15728880);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                Color color = new Color(entityLaserBeam.getDataWatcher().getWatchableObjectInt(26), entityLaserBeam.getDataWatcher().getWatchableObjectInt(27), entityLaserBeam.getDataWatcher().getWatchableObjectInt(28));
                GL11.glColor4f(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 0.5F);

                bindTexture(textureLaserBeam);

                double size_1 = (-11D * entityLaserBeam.getDataWatcher().getWatchableObjectInt(25) + 191D) / 400D;
                double size_2 = 1 - ((-11D * entityLaserBeam.getDataWatcher().getWatchableObjectInt(25) + 191D) / 400D);

                //SIDES
                tessellator.addVertex(size_1, 0, size_1);
                tessellator.addVertex(size_1, 1, size_1);
                tessellator.addVertex(size_2, 1, size_1);
                tessellator.addVertex(size_2, 0, size_1);

                tessellator.addVertex(size_1, 1, size_1);
                tessellator.addVertex(size_1, 0, size_1);
                tessellator.addVertex(size_1, 0, size_2);
                tessellator.addVertex(size_1, 1, size_2);

                tessellator.addVertex(size_2, 0, size_2);
                tessellator.addVertex(size_2, 1, size_2);
                tessellator.addVertex(size_1, 1, size_2);
                tessellator.addVertex(size_1, 0, size_2);

                tessellator.addVertex(size_2, 1, size_2);
                tessellator.addVertex(size_2, 0, size_2);
                tessellator.addVertex(size_2, 0, size_1);
                tessellator.addVertex(size_2, 1, size_1);

                //TOP
                if(entityLaserBeam.shouldDrawTop) {
                    if(direction == ForgeDirection.SOUTH || direction == ForgeDirection.WEST || direction == ForgeDirection.UP){
                        tessellator.addVertex(size_1, 1, size_1);
                        tessellator.addVertex(size_1, 1, size_2);
                        tessellator.addVertex(size_2, 1, size_2);
                        tessellator.addVertex(size_2, 1, size_1);
                    }else{
                        tessellator.addVertex(size_1, 0, size_2);
                        tessellator.addVertex(size_1, 0, size_1);
                        tessellator.addVertex(size_2, 0, size_1);
                        tessellator.addVertex(size_2, 0, size_2);
                    }
                }
            }
            tessellator.draw();

            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void doRenderShadowAndFire(Entity entity, double x, double y, double z, float yaw, float pitch) {

    }


}