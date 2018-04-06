package me.Jonathon594.Mythria.Proxy;

import me.Jonathon594.Mythria.Managers.ChatManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {

    @Override
    public void init(final FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(final FMLPostInitializationEvent e) {
        super.postInit(e);

        ChatManager.initilizeDiscord();
    }

    @Override
    public void preInit(final FMLPreInitializationEvent e) {
        super.preInit(e);
    }
}
