package HxCKDMS.HxCLasers.Client.Gui;

import HxCKDMS.HxCLasers.Containers.ContainerLensMaker;
import HxCKDMS.HxCLasers.HxCLasers;
import HxCKDMS.HxCLasers.Lib.References;
import HxCKDMS.HxCLasers.Network.PacketLensMakerSync;
import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiLensMaker extends GuiContainer {
    private int counter = 0;

    private boolean firstTime = true;

    private double red_percentage;
    private double green_percentage;
    private double blue_percentage;

    private int x;
    private int y;
    private int z;

    public GuiLensMaker(ContainerLensMaker container) {
        super(container);

        red_percentage = container.tileEntityLensMaker.red_percentage;
        green_percentage = container.tileEntityLensMaker.green_percentage;
        blue_percentage = container.tileEntityLensMaker.blue_percentage;

        x = container.tileEntityLensMaker.xCoord;
        y = container.tileEntityLensMaker.yCoord;
        z = container.tileEntityLensMaker.zCoord;
    }

    @Override
    public void initGui() {
        firstTime = true;
        super.initGui();

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        buttonList.add(new GuiSlider(100, xStart - 110,  yStart + 5, 100, 20, "red", "", 0, 255, 0, false, false));
        buttonList.add(new GuiSlider(101, xStart - 110,  yStart + 30, 100, 20, "green", "", 0, 255, 0, false, false));
        buttonList.add(new GuiSlider(102, xStart - 110,  yStart + 55, 100, 20, "blue", "", 0, 255, 0, false, false));
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(new ResourceLocation(References.RESOURCE_LOCATION + "textures/gui/guiLensMaker.png"));
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        drawTexturedModalRect(xStart + 7, yStart + 7, 176, 0, 18, (int)(51 * (1 - red_percentage)));
        drawTexturedModalRect(xStart + 7 + 18 + 9, yStart + 7, 194, 0, 18, (int)(51 * (1 - green_percentage)));
        drawTexturedModalRect(xStart + 7 + 18 + 9 + 18 + 9, yStart + 7, 212, 0, 18, (int)(51 * (1 - blue_percentage)));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRendererObj.drawString("Red", -64 - "Red".length(), 10, 16777120);
        fontRendererObj.drawString("Green", -66 - "Green".length(), 35, 16777120);
        fontRendererObj.drawString("Blue", -64 - "Blue".length(), 60, 16777120);
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
    }

    @Override
    public void updateScreen() {
        for(Object button : buttonList){
            if(button instanceof GuiSlider){
                GuiSlider guiSlider = (GuiSlider) button;
                if(guiSlider.id == 100){
                    if(firstTime) guiSlider.setValue(red_percentage * 255);
                    else red_percentage = guiSlider.getValue() / 255;
                }else if(guiSlider.id == 101){
                    if(firstTime) guiSlider.setValue(green_percentage * 255);
                    else green_percentage = guiSlider.getValue() / 255;
                }else if(guiSlider.id == 102){
                    if(firstTime) guiSlider.setValue(blue_percentage * 255);
                    else blue_percentage = guiSlider.getValue() / 255;
                }
            }
        }
        firstTime = false;

        if(counter == 0) HxCLasers.packetPipeline.sendToServer(new PacketLensMakerSync(x, y, z, red_percentage, green_percentage, blue_percentage));
        if(counter++ >= 600) counter = 0;
    }

    @Override
    public void onGuiClosed() {
        if(!(mc.thePlayer.openContainer instanceof ContainerLensMaker)) {
            HxCLasers.packetPipeline.sendToServer(new PacketLensMakerSync(x, y, z, red_percentage, green_percentage, blue_percentage));
        }
        super.onGuiClosed();
    }


}
