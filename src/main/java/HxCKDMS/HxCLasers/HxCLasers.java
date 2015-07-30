package HxCKDMS.HxCLasers;

import HxCKDMS.HxCLasers.Handlers.GuiHandler;
import HxCKDMS.HxCLasers.Network.MessageLensMakerStart;
import HxCKDMS.HxCLasers.Network.MessageLensMakerSync;
import HxCKDMS.HxCLasers.Proxy.IProxy;
import HxCKDMS.HxCLasers.Registry.Registry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

import static HxCKDMS.HxCLasers.Lib.References.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDENCIES)
public class HxCLasers {
    public static SimpleNetworkWrapper network;

    @Mod.Instance(MOD_ID)
    public static HxCLasers instance;

    @SidedProxy(serverSide = SERVER_PROXY_LOCATION, clientSide = CLIENT_PROXY_LOCATION)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(PACKET_CHANNEL_NAME);
        network.registerMessage(MessageLensMakerSync.Handler.class, MessageLensMakerSync.class, 0, Side.SERVER);
        network.registerMessage(MessageLensMakerStart.Handler.class, MessageLensMakerStart.class, 1, Side.SERVER);

        Registry.preInit();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        Registry.init();
        proxy.init(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }
}
