package HxCKDMS.HxCLasers.Network;

import HxCKDMS.HxCLasers.TileEntities.TileEntityLensMaker;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageLensMakerSync implements IMessage {
    int x = 0;
    int y = 0;
    int z = 0;

    float red = 0;
    float green = 0;
    float blue = 0;

    public MessageLensMakerSync() {}

    public MessageLensMakerSync(int x, int y, int z, float red, float green, float blue) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();

        red = buf.readFloat();
        green = buf.readFloat();
        blue = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeFloat(red);
        buf.writeFloat(green);
        buf.writeFloat(blue);
    }

    public static class Handler implements IMessageHandler<MessageLensMakerSync, IMessage> {
        @Override
        public IMessage onMessage(MessageLensMakerSync message, MessageContext ctx) {
            TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

            if(tileEntity != null && tileEntity instanceof TileEntityLensMaker){
                TileEntityLensMaker tileEntityLensMaker = (TileEntityLensMaker) tileEntity;

                tileEntityLensMaker.red_percentage = message.red;
                tileEntityLensMaker.green_percentage = message.green;
                tileEntityLensMaker.blue_percentage = message.blue;

                ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(message.x, message.y, message.z);
            }
            return null;
        }
    }
}
