package HxCKDMS.HxCLasers.Network;

import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageLensMakerStart implements IMessage {
    int x;
    int y;
    int z;

    public MessageLensMakerStart() {}

    public MessageLensMakerStart(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<MessageLensMakerStart, IMessage> {

        @Override
        public IMessage onMessage(MessageLensMakerStart message, MessageContext ctx) {
            TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

            if(tileEntity instanceof TileEntityLensMaker){
                TileEntityLensMaker tileEntityLensMaker = (TileEntityLensMaker) tileEntity;

                tileEntityLensMaker.canStart = true;
            }
            return null;
        }
    }
}
