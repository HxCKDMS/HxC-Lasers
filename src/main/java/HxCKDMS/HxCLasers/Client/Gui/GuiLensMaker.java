package HxCKDMS.HxCLasers.Client.Gui;

import HxCKDMS.HxCLasers.Containers.ContainerLensMaker;
import HxCKDMS.HxCLasers.HxCLasers;
import HxCKDMS.HxCLasers.Lib.References;
import HxCKDMS.HxCLasers.Network.MessageLensMakerStart;
import HxCKDMS.HxCLasers.Network.MessageLensMakerSync;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiLensMaker extends GuiContainer {
    private boolean dragging_red = false;
    private boolean dragging_green = false;
    private boolean dragging_blue = false;

    private float red_percentage;
    private float green_percentage;
    private float blue_percentage;

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
        super.initGui();

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        buttonList.add(new GuiButton(0, xStart + 80, yStart + 42, 28, 11, "Start"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(new ResourceLocation(References.RESOURCE_LOCATION + "textures/gui/guiLensMaker.png"));
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        drawTexturedModalRect(xStart + 8, yStart + 8, 176, 0, 16, (int)(49 * (1 - red_percentage)));
        drawTexturedModalRect(xStart + 35, yStart + 8, 192, 0, 16, (int)(49 * (1 - green_percentage)));
        drawTexturedModalRect(xStart + 62, yStart + 8, 208, 0, 16, (int)(49 * (1 - blue_percentage)));

        GL11.glColor3f(red_percentage, green_percentage, blue_percentage);
        drawTexturedModalRect(xStart + 142, yStart + 62, 196, 49, 28, 16);
        GL11.glColor3f(1, 1, 1);
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        if(button == 0){
            int xStart = (width - xSize) / 2;
            int yStart = (height - ySize) / 2;

            if(y >= yStart + 8 && y <= yStart + 56){
                float percent = 1 - ((float)(y - yStart - 8)) / 48;

                if(x >= xStart + 8 && x <= xStart + 23) red_percentage = percent;
                else if(x >= xStart + 35 && x <= xStart + 50) green_percentage = percent;
                else if(x >= xStart + 62 && x <= xStart + 77) blue_percentage = percent;
                HxCLasers.network.sendToServer(new MessageLensMakerSync(x, y, z, red_percentage, green_percentage, blue_percentage));
            }
        }
        super.mouseClicked(x, y, button);
    }

    @Override
    protected void mouseClickMove(int x, int y, int lastButtonClicked, long timeSinceClicked) {
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        if(lastButtonClicked == 0){
            if(!dragging_red && !dragging_green && !dragging_blue){
                if(y >= yStart + 8 && y <= yStart + 56){
                    if(x >= xStart + 8 && x <= xStart + 23) dragging_red = true;
                    else if(x >= xStart + 35 && x <= xStart + 50) dragging_green = true;
                    else if(x >= xStart + 62 && x <= xStart + 77) dragging_blue = true;
                }
            }else{
                float percent = 1 - ((float)(y - yStart - 8)) / 48;

                if(percent > 1) percent = 1;
                if(percent < 0) percent = 0;

                if(dragging_red) red_percentage = percent;
                if(dragging_green) green_percentage = percent;
                if(dragging_blue) blue_percentage = percent;
            }
        }
        super.mouseClickMove(x, y, lastButtonClicked, timeSinceClicked);
    }

    @Override
    protected void mouseReleased(int x, int y, int which) {
        if(which == 0){
            dragging_red = false;
            dragging_green = false;
            dragging_blue = false;

            HxCLasers.network.sendToServer(new MessageLensMakerSync(x, y, z, red_percentage, green_percentage, blue_percentage));
        }

        super.mouseReleased(x, y, which);
    }

    @Override
    public void onGuiClosed() {
        if(!(mc.thePlayer.openContainer instanceof ContainerLensMaker)) {
            HxCLasers.network.sendToServer(new MessageLensMakerSync(x, y, z, red_percentage, green_percentage, blue_percentage));
        }
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.id == 0){
            HxCLasers.network.sendToServer(new MessageLensMakerSync(x, y, z, red_percentage, green_percentage, blue_percentage));
            HxCLasers.network.sendToServer(new MessageLensMakerStart(x, y, z));
        }
    }
}
