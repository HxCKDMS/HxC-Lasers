package HxCKDMS.HxCLasers.Network;

import HxCKDMS.HxCCore.api.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class PacketLaserSync extends AbstractPacket {


    public PacketLaserSync() {}

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {

    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {

    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }
}
