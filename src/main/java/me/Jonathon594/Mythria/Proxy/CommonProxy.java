package me.Jonathon594.Mythria.Proxy;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Capability.CapabilityHandler;
import me.Jonathon594.Mythria.Capability.Food.Food;
import me.Jonathon594.Mythria.Capability.Food.FoodStorage;
import me.Jonathon594.Mythria.Capability.Food.IFood;
import me.Jonathon594.Mythria.Capability.Metal.IMetal;
import me.Jonathon594.Mythria.Capability.Metal.Metal;
import me.Jonathon594.Mythria.Capability.Metal.MetalStorage;
import me.Jonathon594.Mythria.Capability.NPC.INPC;
import me.Jonathon594.Mythria.Capability.NPC.NPC;
import me.Jonathon594.Mythria.Capability.NPC.NPCStorage;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileStorage;
import me.Jonathon594.Mythria.Capability.Vessel.IVessel;
import me.Jonathon594.Mythria.Capability.Vessel.Vessel;
import me.Jonathon594.Mythria.Capability.Vessel.VesselStorage;
import me.Jonathon594.Mythria.Entity.MythriaEntities;
import me.Jonathon594.Mythria.GUI.GuiHandler;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Listener.*;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.*;
import me.Jonathon594.Mythria.TileEntity.MythriaTileEntities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public void init(final FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Mythria.instance, new GuiHandler());

        PerkManager.CreateTrades();
        PerkManager.CreateLifes();
        PerkManager.CreateCombats();
        PerkManager.CreatePersonalities();
        PerkManager.CreateBlessings();
        MaterialRequirementsManager.Initialize();
        RecipeManager.Initialize();
        WeightManager.Initialize();

        GameRegistry.registerWorldGenerator(new MythriaWorldGenerator(), 0);
    }

    public void postInit(final FMLPostInitializationEvent e) {
    }

    public void preInit(final FMLPreInitializationEvent e) {
        CapabilityManager.INSTANCE.register(IProfile.class, new ProfileStorage(), Profile.class);
        CapabilityManager.INSTANCE.register(IFood.class, new FoodStorage(), Food.class);
        CapabilityManager.INSTANCE.register(INPC.class, new NPCStorage(), NPC.class);
        CapabilityManager.INSTANCE.register(IVessel.class, new VesselStorage(), Vessel.class);
        CapabilityManager.INSTANCE.register(IMetal.class, new MetalStorage(), Metal.class);
        MythriaEntities.Initialize();
        MythriaTileEntities.RegisterTileEntities();

        HealthManager.Initialize();
        FoodManager.Initialize();
        SmeltingManager.Initialize();
        CastingManager.Initialize();
        SmithingManager.Initialize();
        CarpentryManager.Initialize();
        MythriaVanillaBlocksModification.setAllDefaultStackSizes();

        MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
        MinecraftForge.EVENT_BUS.register(EventListener.class);
        MinecraftForge.EVENT_BUS.register(MythriaItems.class);
        MinecraftForge.EVENT_BUS.register(MythriaBlocks.class);
        MinecraftForge.EVENT_BUS.register(RecipeListener.class);
        MinecraftForge.EVENT_BUS.register(HealthManager.class);
        MinecraftForge.EVENT_BUS.register(AbilityListener.class);
        MinecraftForge.TERRAIN_GEN_BUS.register(TerrainGenListener.class);
        MinecraftForge.ORE_GEN_BUS.register(OreGenListener.class);
        TimeManager.Initialize();

        int ID = 0;
        MythriaPacketHandler.INSTANCE.registerMessage(ProfileCreationPacketHandler.class, ProfileCreationPacket.class,
                ID++, Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(CommandPacketHandler.class, CommandPacket.class, ID++,
                Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(SPacketUpdateAttributeHandler.class, SPacketUpdateAttribute.class, ID++,
                Side.CLIENT);
        MythriaPacketHandler.INSTANCE.registerMessage(ClientProfileDataCachePacketHandler.class,
                ClientProfileDataCachePacket.class, ID++, Side.CLIENT);
        MythriaPacketHandler.INSTANCE.registerMessage(CPacketAddExperienceHandler.class, CPacketAddExperience.class,
                ID++, Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(SPacketUpdateExperienceHandler.class,
                SPacketUpdateExperience.class, ID++, Side.CLIENT);
        MythriaPacketHandler.INSTANCE.registerMessage(WallJumpPacketHandler.class, WallJumpPacket.class, ID++,
                Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(ThrowWeaponPacketHandler.class, ThrowWeaponPacket.class, ID++,
                Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(SPacketAddAttributeHandler.class, SPacketAddAttribute.class, ID++,
                Side.CLIENT);
        MythriaPacketHandler.INSTANCE.registerMessage(CPacketConsumeConsumableHandler.class,
                CPacketConsumeConsumable.class, ID++, Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(DPacketSoundEventHandler.class, DPacketSoundEvent.class, ID++,
                Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(CPacketSetResistanceTicksHandler.class,
                CPacketSetResistanceTicks.class, ID++, Side.SERVER);
        MythriaPacketHandler.INSTANCE.registerMessage(SPacketUpdateConsumablesHandler.class,
                SPacketUpdateConsumables.class, ID++, Side.CLIENT);
        MythriaPacketHandler.INSTANCE.registerMessage(SPacketAddHealthConditionInstanceHandler.class,
                SPacketAddHealthConditionInstance.class, ID++, Side.CLIENT);

        MythriaPacketHandler.INSTANCE.registerMessage(AttackEntityPacketCustomHandler.class,
                AttackEntityPacketCustom.class, ID++, Side.SERVER);

        MythriaPacketHandler.INSTANCE.registerMessage(SPacketUpdateVessel.SPacketUpdateVesselHandler.class,
                SPacketUpdateVessel.class, ID++, Side.CLIENT);

        MythriaPacketHandler.INSTANCE.registerMessage(CPacketAnvilButton.CPacketAnvilButtonHandler.class,
                CPacketAnvilButton.class, ID++, Side.SERVER);

        MythriaPacketHandler.INSTANCE.registerMessage(SPacketSetSelectedDeity.SPacketSetSelectedDeityHandler.class,
                SPacketSetSelectedDeity.class, ID++, Side.CLIENT);
    }

    public IThreadListener getThreadListener(MessageContext ctx) {
        return null;
    }

    public EntityPlayer getPlayer(MessageContext ctx) {
        return null;
    }
}
