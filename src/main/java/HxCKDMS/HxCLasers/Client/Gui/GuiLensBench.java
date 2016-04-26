package HxCKDMS.HxCLasers.Client.Gui;

import HxCKDMS.HxCLasers.Lib.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiLensBench extends GuiContainer {
    public GuiLensBench(Container container) {
        super(container);
        ySize = 204;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        drawString(fontRendererObj, "X : " + mouseX + "  Y : " + mouseY, 0, 0, Color.white.getRGB());

        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(new ResourceLocation(References.RESOURCE_LOCATION + "textures/gui/guiLensMakerOld.png"));
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
