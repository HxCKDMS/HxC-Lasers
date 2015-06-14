package HxCKDMS.HxCLasers.Proxy;

import HxCKDMS.HxCLasers.Client.Rendering.Entity.EntityLaserRenderer;
import HxCKDMS.HxCLasers.Entity.EntityLaserBeam;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        registerEntityRendering();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerEntityRendering(){
        RenderingRegistry.registerEntityRenderingHandler(EntityLaserBeam.class, new EntityLaserRenderer());
    }
}
