package HxCKDMS.HxCLasers;

import HxCKDMS.HxCCore.api.Utils.LogHelper;
import HxCKDMS.HxCCore.network.PacketPipeline;
import HxCKDMS.HxCLasers.Handlers.GuiHandler;
import HxCKDMS.HxCLasers.Network.PacketLensMakerSync;
import HxCKDMS.HxCLasers.Proxy.IProxy;
import HxCKDMS.HxCLasers.Registry.Registry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import static HxCKDMS.HxCLasers.Lib.References.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDENCIES)
public class HxCLasers {
    public static PacketPipeline packetPipeline = new PacketPipeline();

    @Mod.Instance(MOD_ID)
    public static HxCLasers instance;

    @SidedProxy(serverSide = SERVER_PROXY_LOCATION, clientSide = CLIENT_PROXY_LOCATION)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Registry.preInit();
        proxy.preInit(event);

        LogHelper.info("Thank you for using HxC Lasers", MOD_ID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        Registry.init();
        registerPackets();
        packetPipeline.initialize(PACKET_CHANNEL_NAME);
        proxy.init(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        packetPipeline.postInitialize();
        proxy.postInit(event);
    }

    private void registerPackets(){
        packetPipeline.addPacket(PacketLensMakerSync.class);
    }
}
