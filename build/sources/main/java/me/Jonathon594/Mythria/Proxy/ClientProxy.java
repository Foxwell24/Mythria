package me.Jonathon594.Mythria.Proxy;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Client.Keybindings;
import me.Jonathon594.Mythria.Client.Renderer.DeityRenderManager;
import me.Jonathon594.Mythria.Client.Renderer.ReticleRenderer;
import me.Jonathon594.Mythria.Entity.MythriaEntities;
import me.Jonathon594.Mythria.GUI.GuiListener;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.TileEntity.MythriaTileEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    @Override
    public void init(final FMLInitializationEvent e) {
        super.init(e);

        Keybindings.Init();
        MythriaBlocks.RegisterRenders();
        MythriaBlocks.registerCustomStateMappers();
        MythriaTileEntities.registerSpecialRenderers();
    }

    @Override
    public void postInit(final FMLPostInitializationEvent e) {
        super.postInit(e);

        DeityRenderManager.Initialize();
    }

    @Override
    public void preInit(final FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(GuiListener.class);
        MythriaEntities.RegisterRenders();
        MinecraftForge.EVENT_BUS.register(ReticleRenderer.class);

        // Field[] fieldList = Minecraft.class.getDeclaredFields();
        // for (int i = 0; i < fieldList.length; i++) {
        // System.out.println("[" + i + "] " + fieldList[i]);
        // }
        // This line is optional, but it makes it easier to find the field indexes
        // by stopping Minecraft once they are printed.

        // Reflection

        // FMLCommonHandler.instance().exitJava(0, true);

        super.preInit(e);
    }
}
