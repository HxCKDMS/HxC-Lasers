package HxCKDMS.HxCLasers.Client.Gui;

import HxCKDMS.HxCLasers.Containers.ContainerLensBench;
import HxCKDMS.HxCLasers.Lib.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiLensBench extends GuiContainer {
    private ContainerLensBench containerLensBench;

    public GuiLensBench(ContainerLensBench container) {
        super(container);
        this.containerLensBench = container;
        ySize = 202;
    }

    private void drawItem(ItemStack stack, int x, int y) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        itemRender.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), stack, (width - xSize) / 2 + x, (height - ySize) / 2 + y);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawString(fontRendererObj, "X : " + mouseX + "  Y : " + mouseY, 0, 0, Color.white.getRGB());

        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(new ResourceLocation(References.RESOURCE_LOCATION + "textures/gui/guiLensMakerOld.png"));
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        if (containerLensBench.tileEntityLensBench.getStackInSlot(6) != null) {
            drawItem(containerLensBench.tileEntityLensBench.getStackInSlot(6), 37, 34);
            drawItem(containerLensBench.tileEntityLensBench.getStackInSlot(6), 19, 52);
            drawItem(containerLensBench.tileEntityLensBench.getStackInSlot(6), 37, 70);
            drawItem(containerLensBench.tileEntityLensBench.getStackInSlot(6), 55, 88);
            drawItem(containerLensBench.tileEntityLensBench.getStackInSlot(6), 73, 70);
            drawItem(containerLensBench.tileEntityLensBench.getStackInSlot(6), 91, 52);
            drawItem(containerLensBench.tileEntityLensBench.getStackInSlot(6), 73, 34);
        }
    }
}
