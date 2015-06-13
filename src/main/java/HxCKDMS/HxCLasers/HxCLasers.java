package HxCKDMS.HxCLasers;

import HxCKDMS.HxCCore.api.Utils.LogHelper;
import HxCKDMS.HxCLasers.Proxy.IProxy;
import HxCKDMS.HxCLasers.Registry.Registry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import static HxCKDMS.HxCLasers.Lib.References.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDENCIES)
public class HxCLasers {

    @Mod.Instance(MOD_ID)
    public static HxCLasers HxCLasers;

    @SidedProxy(serverSide = SERVER_PROXY_LOCATION, clientSide = CLIENT_PROXY_LOCATION)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Registry.preInit();

        LogHelper.info("Thank you for using HxC Lasers", MOD_ID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }
}
