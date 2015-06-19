package HxCKDMS.HxCLasers.Network;

import HxCKDMS.HxCCore.api.AbstractPacket;
import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PackLensMakerStart extends AbstractPacket {
    private int x;
    private int y;
    private int z;

    public PackLensMakerStart() {}

    public PackLensMakerStart(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);
    }

    @Override
    public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        x = byteBuf.readInt();
        y = byteBuf.readInt();
        z = byteBuf.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer entityPlayer) {

    }

    @Override
    public void handleServerSide(EntityPlayer entityPlayer) {
        TileEntity tileEntity = entityPlayer.worldObj.getTileEntity(x, y, z);

        if(tileEntity instanceof TileEntityLensMaker){
            TileEntityLensMaker tileEntityLensMaker = (TileEntityLensMaker) tileEntity;

            tileEntityLensMaker.canStart = true;
        }
    }
}
