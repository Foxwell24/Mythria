package me.Jonathon594.Mythria.Capability;

import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Capability.Metal.MetalProvider;
import me.Jonathon594.Mythria.Capability.NPC.NPCProvider;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import me.Jonathon594.Mythria.Entity.EntityNPCPlayer;
import me.Jonathon594.Mythria.Interface.IWorkable;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

public class CapabilityHandler {
    public static final ResourceLocation PROFILE_CAP = new ResourceLocation(Mythria.MODID + ":" + "profile_capability");
    public static final ResourceLocation FOOD_CAP = new ResourceLocation(Mythria.MODID + ":" + "food_capability");
    public static final ResourceLocation NPC_CAP = new ResourceLocation(Mythria.MODID + ":" + "npc_capability");
    public static final ResourceLocation VESSEL_CAP = new ResourceLocation(Mythria.MODID + ":" + "vessel_capability");
    public static final ResourceLocation METAL_CAP = new ResourceLocation(Mythria.MODID + ":" + "metal_capability");

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(PROFILE_CAP, new ProfileProvider());
        }
        if (event.getObject() instanceof EntityNPCPlayer)
            event.addCapability(NPC_CAP, new NPCProvider());
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void attachCapabilityItem(final AttachCapabilitiesEvent<ItemStack> event) {
        if (!(event.getObject() instanceof ItemStack))
            return;
        final ItemStack is = event.getObject();
        if (MythriaUtil.isFood(is)) {
            event.addCapability(FOOD_CAP, new FoodProvider());
        }
        if(is.getItem().equals(MythriaItems.CERAMIC_VESSEL)) {
            event.addCapability(VESSEL_CAP, new VesselProvider());
        }
        if(is.getItem() instanceof IWorkable) {
            event.addCapability(METAL_CAP, new MetalProvider());
        }
    }
}
