package HxCKDMS.HxCLasers.Network;

import HxCKDMS.HxCCore.api.AbstractPacket;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketLensMakerSync extends AbstractPacket {
    int x = 0;
    int y = 0;
    int z = 0;

    double red = 0;
    double green = 0;
    double blue = 0;

    public PacketLensMakerSync() {}

    public PacketLensMakerSync(int x, int y, int z, double red, double green, double blue) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);

        byteBuf.writeDouble(red);
        byteBuf.writeDouble(green);
        byteBuf.writeDouble(blue);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        x = byteBuf.readInt();
        y = byteBuf.readInt();
        z = byteBuf.readInt();

        red = byteBuf.readDouble();
        green = byteBuf.readDouble();
        blue = byteBuf.readDouble();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        //NOOP
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        TileEntity tileEntity = player.worldObj.getTileEntity(x, y, z);

        if(tileEntity != null && tileEntity instanceof TileEntityLensMaker){
            TileEntityLensMaker tileEntityLensMaker = (TileEntityLensMaker) tileEntity;

            tileEntityLensMaker.red_percentage = red;
            tileEntityLensMaker.green_percentage = green;
            tileEntityLensMaker.blue_percentage = blue;

            player.worldObj.markBlockForUpdate(x, y, z);
        }
    }
}