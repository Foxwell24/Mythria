package me.Jonathon594.Mythria;

import me.Jonathon594.Mythria.Commands.*;
import me.Jonathon594.Mythria.Proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Mythria.MODID, name = Mythria.MODNAME, version = Mythria.VERSION)
public class Mythria {

    public static final String MODID = "mythria";
    public static final String MODNAME = "Mythria";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "me.Jonathon594.Mythria.Proxy.ClientProxy", serverSide = "me.Jonathon594.Mythria.Proxy.ServerProxy")
    public static CommonProxy proxy;

    @Instance
    public static Mythria instance = new Mythria();

    @EventHandler
    public static void onFMLStart(final FMLServerStartingEvent event) {
        event.registerServerCommand(new GameModeCreative());
        event.registerServerCommand(new GameModeSurvival());
        event.registerServerCommand(new GameModeSpectator());
        event.registerServerCommand(new HealCommand());
        event.registerServerCommand(new DeityCommand());
        event.registerServerCommand(new BlessItemCommand());
    }

    @EventHandler
    public void init(final FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @EventHandler
    public void preInit(final FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }
}
