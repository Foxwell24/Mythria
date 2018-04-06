package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Keybindings;
import me.Jonathon594.Mythria.Client.Renderer.DeityRenderManager;
import me.Jonathon594.Mythria.Client.Renderer.ReticleRenderer;
import me.Jonathon594.Mythria.Client.Sounds.MythriaSounds;
import me.Jonathon594.Mythria.Const.Blacklist;
import me.Jonathon594.Mythria.Const.CombatMessages;
import me.Jonathon594.Mythria.Const.StaminaCost;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Event.NewDayEvent;
import me.Jonathon594.Mythria.Event.WallJumpEvent;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Interface.IBlockData;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Managers.*;
import me.Jonathon594.Mythria.Module.*;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.CommandPacket;
import me.Jonathon594.Mythria.Packets.CommandPacketHandler;
import me.Jonathon594.Mythria.Packets.SPacketSetSelectedDeity;
import me.Jonathon594.Mythria.Storage.GlobalSaveData;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class EventListener {
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void chatReceivedEvent(final ServerChatEvent event) {
        event.setCanceled(true);

        final EntityPlayerMP sender = event.getPlayer();
        final IProfile p = sender.getCapability(ProfileProvider.PROFILE_CAP, null);

        ChatManager.handleForgeChat(event, sender, p);
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void guiPostScreenEvent(final GuiScreenEvent.InitGuiEvent.Post event) {
        MythriaInventoryManager.onPostScreenEventGui(event);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent public static void onModelRegistry(ModelRegistryEvent event) {
        MythriaItems.RegisterRenders();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void keyPress(final KeyInputEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (Keybindings.KeyBindingOpenMenu.isPressed())
            mc.player.openGui(Mythria.instance, MythriaGui.MYTHRIA_MENU_GUI.ordinal(), mc.player.world, 0, 0, 0);
        if (Keybindings.KeyBindingToggleChat.isPressed())
            MythriaPacketHandler.INSTANCE.sendToServer(new CommandPacket(CommandPacketHandler.TOGGLE_CHAT_ID));
        if (Keybindings.KeyBindingThrowWeapon.isPressed())
            WeaponThrowingManager.throwWeapon();
    }

    @SubscribeEvent
    public static void onHarvestBlock(BlockEvent.HarvestDropsEvent event) {

    }

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event) {
        // If client, return;
        if (event.getWorld().isRemote)
            return;
        final EntityPlayer p = event.getPlayer();
        if (p.isCreative())
            return;
        final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);

        int cost = event.getState().getBlock() instanceof IBlockData ?
                ((IBlockData) event.getState().getBlock()).getStaminaCostForBreaking() :
                MaterialRequirementsManager.staminaCostForBreakingPlacing.get(event.getState().getBlock());
        if (profile.getConsumables().get(Consumable.STAMINA) < cost) {
            event.setCanceled(true);
            return;
        }
        MaterialRequirementsManager.onBlockBreak(event, p, profile);
        if (event.isCanceled())
            return;
        MythriaVanillaBlocksModification.onBlockBreak(event, p);
        if (event.isCanceled())
            return;

        StatManager.ChargeConsumable(event.getPlayer(), cost, Consumable.STAMINA);
        TreeModule.HandleTreeChop(p, event.getPos());

        BlockModule.softenStone(event.getWorld(), event.getPos(), event.getState().getBlock());
        BlockDropModule.onBlockBreak(event);
    }



    @SubscribeEvent
    public static void onBlockPlace(final BlockEvent.PlaceEvent event) {
        // If client, return;
        if (event.getWorld().isRemote)
            return;
        final EntityPlayer p = event.getPlayer();
        if (p.isCreative())
            return;
        final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);

        int cost = event.getState().getBlock() instanceof IBlockData ?
                ((IBlockData) event.getState().getBlock()).getStaminaCostForBreaking() :
                MaterialRequirementsManager.staminaCostForBreakingPlacing.get(event.getState().getBlock());
        if (profile.getConsumables().get(Consumable.STAMINA) < cost) {
            event.setCanceled(true);
            return;
        }
        MaterialRequirementsManager.onBlockPlace(event);
        if (event.isCanceled())
            return;
        StatManager.ChargeConsumable(event.getPlayer(), cost, Consumable.STAMINA);

        BlockModule.physicsCheck(event.getWorld(), event.getPos(), event.getPlacedBlock().getBlock(), 10,
                true);

        WeightManager.WeightUpdateQue.add(p);
    }

    @SubscribeEvent
    public static void onBreakSpeed(final net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        IProfile profile = event.getEntityPlayer().getCapability(ProfileProvider.PROFILE_CAP, null);
        float factor = 0f;
        if(MythriaUtil.isOre(event.getState().getBlock())) {
            int mining = profile.getSkillLevel(MythicSkills.MINING);
            factor = MathHelper.clamp((float) mining/100f, 0, 1);
        }
        event.setNewSpeed(event.getOriginalSpeed() / (1 + (9*(1-factor))));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase.equals(Phase.START)) {
            DualSwingManager.onClientTick();
            PainManager.onClientTick();
            AbilityManager.onClientTick();
        }
    }

    @SubscribeEvent
    public static void onCloseContainer(final PlayerContainerEvent.Close event) {
        if (event.getEntityPlayer().getEntityWorld().isRemote)
            return;
        WeightManager.WeightUpdateQue.add(event.getEntityPlayer());
    }

    @SubscribeEvent
    public static void onCraftItem(final ItemCraftedEvent event) {
        // If client, return;
        if (event.player.getEntityWorld().isRemote)
            return;
        final IProfile p = event.player.getCapability(ProfileProvider.PROFILE_CAP, null);
        final List<Perk> list = MaterialRequirementsManager.AttributesForCrafting
                .get(event.crafting.getItem());
        if (list != null)
            for (final Perk pa : list)
                if (p.getPlayerSkills().contains(pa))
                    for (final Entry<MythicSkills, Integer> s : pa.getRequiredSkills().entrySet())
                        p.addSkillExperience(s.getKey(), 25 * (s.getValue() / 10 + 1), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public static void onPlayerFish(ItemFishedEvent event) {
        // If client, return;
        if (event.getEntityPlayer().getEntityWorld().isRemote)
            return;
        final IProfile p = event.getEntityPlayer().getCapability(ProfileProvider.PROFILE_CAP, null);
        p.addSkillExperience(MythicSkills.FISHING, 50, (EntityPlayerMP) event.getEntityPlayer());
    }

    @SubscribeEvent
    public static void onEntityInteract(final PlayerInteractEvent.RightClickBlock event) {
        // If client, return;
        if (event.getEntityPlayer().getEntityWorld().isRemote)
            return;
//         //Be sure not to use getFields as that only fetches public fields.
//         final Field[] fieldList = Minecraft.class.getDeclaredFields();
//         for (int i = 0; i < fieldList.length; i++) {
//         System.out.println("[" + i + "] " + fieldList[ i ]);
//         }

        InteractModule.placePitKiln(event);
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getEntity();
            if (event.getWorld().isRemote)
                if (player.equals(Minecraft.getMinecraft().player))
                    MythriaPacketHandler.INSTANCE
                            .sendToServer(new CommandPacket(CommandPacketHandler.PROFILE_SYNC_REQUEST));
        }
        VanillaEntityManager.onEntityJoinWorld(event);
    }

    @SubscribeEvent
    public static void onEntityPickupItem(final EntityItemPickupEvent event) {
        if (event.getEntityPlayer() != null) {
            MythriaInventoryManager.onPlayerPickupItem(event);
            FoodManager.onPlayerPickupItem(event);
        }
    }

    @SubscribeEvent
    public static void onItemPickup(final PlayerEvent.ItemPickupEvent event) {
        if (event.player.getEntityWorld().isRemote)
            return;
        WeightManager.WeightUpdateQue.add(event.player);
    }

    @SubscribeEvent
    public static void onItemToss(final ItemTossEvent event) {
        if (event.getPlayer().getEntityWorld().isRemote)
            return;
        WeightManager.WeightUpdateQue.add(event.getPlayer());
    }

    @SubscribeEvent
    public static void onJump(final LivingJumpEvent event) {

        if (!event.getEntity().world.isRemote) {
            if (event.getEntity() instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer) event.getEntity();
                final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
                StatManager.onJump(player, p);
                if (p.getConsumables().get(Consumable.WEIGHT) > p.getStat(StatType.MAX_WEIGHT)) {
                    MythriaUtil.DropAllItems(player, false, true);
                }
            }
        } else {
            AbilityManager.onJumpClient();
        }

    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        AbilityManager.handleCombatSkills(event);

        if (event.getEntityLiving() instanceof EntityPlayer) {
            if (event.getSource().equals(DamageSource.STARVE)) {
                event.setCanceled(true);
                return;
            }
        } else {
            if (event.getEntity().getEntityWorld().isRemote)
                return;
            final Entity trueSource = event.getSource().getTrueSource();
            if (trueSource instanceof EntityPlayer) {
                final EntityPlayer source = (EntityPlayer) trueSource;
                final IProfile profile = source.getCapability(ProfileProvider.PROFILE_CAP, null);
                AbilityManager.onLivingHurt(profile, event, source);
            }
        }
    }

    @SubscribeEvent
    public static void onMouse(final MouseInputEvent event) {
    }

    @SubscribeEvent
    public static void onNewDay(final NewDayEvent event) {
        for (final EntityPlayerMP p : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                .getPlayers()) {
            final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);
        }
    }

    @SubscribeEvent
    public static void onOpenContainer(final PlayerContainerEvent.Open event) {
        if (event.getEntityPlayer().getEntityWorld().isRemote)
            return;
        FoodManager.UpdateFoodItems(event);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onOpenGui(final GuiOpenEvent event) {
        MythriaInventoryManager.onOpenGui(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        SkillManager.onPlayerTick(event);
        WallJumpManager.onPlayerTick(event);

        final EntityPlayer player = event.player;
        if (event.phase.equals(Phase.END)) {
            VesselModule.onPlayerTick(event);
        }

        if (player.world.isRemote)
            return;

        if (event.phase.equals(Phase.END)) {
            MythriaInventoryManager.onPlayerTickEvent(event);

            final ItemStack mh = player.getHeldItemMainhand();
            final ItemStack oh = player.getHeldItemOffhand();
            final AbstractAttributeMap mhm = new AttributeMap();
            mhm.registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
            int d = 0;
            double mhmv = 0;
            if (!mh.equals(ItemStack.EMPTY)) {
                mhm.applyAttributeModifiers(mh.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
                mhmv = mhm.getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                d++;
            } else
                mhmv = 4;
            final AbstractAttributeMap ohm = new AttributeMap();
            ohm.registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
            double ohmv = 0;
            if (!oh.equals(ItemStack.EMPTY)) {
                ohm.applyAttributeModifiers(oh.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
                ohmv = ohm.getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                d++;
            } else
                ohmv = 4;
            final double pas = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
            double total = Math.min(ohmv, mhmv);
            if (d == 2)
                total *= 1.5;
            if (pas != total) {
                final IAttributeInstance attackSpeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
                final Iterator<AttributeModifier> i = attackSpeed.getModifiers().iterator();
                while (i.hasNext()) {
                    final AttributeModifier am = i.next();
                    if (am.getName().equals("Weapon modifier") || am.getName().equals("Tool modifier"))
                        attackSpeed.removeModifier(am);
                }

                final AttributeModifier am = new AttributeModifier("Weapon modifier", total - 4, 0);
                attackSpeed.removeModifier(am);
                attackSpeed.applyModifier(am);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterSounds(final RegistryEvent.Register<SoundEvent> event) {
        MythriaSounds.registerSounds(event);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderPlayer(final RenderPlayerEvent.Pre event) {
        if(event.getEntityPlayer().isSpectator()) {
            event.setCanceled(true);
        }

        Deity d = DeityManager.getSelectedDeities().get(event.getEntityPlayer().getEntityId());
        if(d != null) {
            event.setCanceled(true);
            DeityRenderManager.onRenderPlayer(event);
        }
    }

    @SubscribeEvent
    public static void onServerTick(final TickEvent.ServerTickEvent event) {
        if (event.phase.equals(Phase.END)) {
            StatManager.onTick(event);
            TimeManager.onTick(event);
            HealManager.onTick(event);
        }
    }

    @SubscribeEvent
    public static void onStartTracking(net.minecraftforge.event.entity.player.PlayerEvent.StartTracking event) {
    }

    @SubscribeEvent
    public static void onWallJump(final WallJumpEvent event) {
        final EntityPlayer player = event.getEntityPlayer();
        StatManager.ChargeConsumable(player, StaminaCost.WALL_JUMP_COST, Consumable.STAMINA);
        player.fallDistance = 0;
    }

    @SubscribeEvent
    public static void onWorldLoad(final WorldEvent.Load event) {
        if (event.getWorld().isRemote)
            return;
        GlobalSaveData.get(event.getWorld()).markDirty();
        event.getWorld().getGameRules().setOrCreateGameRule("naturalRegeneration", "false");
    }

    @SubscribeEvent
    public static void onLivingDrop(LivingDropsEvent event) {
        ArrayList<EntityItem> toRemove = new ArrayList<>();
        for(EntityItem e : event.getDrops()) {
            if(Blacklist.isBlacklisted(e.getItem().getItem())) toRemove.add(e);
        }

        event.getDrops().removeAll(toRemove);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void playerLoggedIn(final PlayerLoggedInEvent event) {
        final EntityPlayerMP player = (EntityPlayerMP) event.player;
        ChatManager.setChatChannel(player, ChatChannel.LOCAL);
        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        p.setPlayer(player);
        player.sendMessage(new TextComponentString("This server is in BETA, please report any issues and suggestions on the discord."));

        for(Entry<Integer, Deity> e : DeityManager.getSelectedDeities().entrySet()) {
            MythriaPacketHandler.INSTANCE.sendTo(new SPacketSetSelectedDeity(e.getKey(), e.getValue().ordinal()), player);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void playerLoggedOut(final PlayerLoggedOutEvent event) {
        if (event.player.getEntityWorld().isRemote)
            return;
        final EntityPlayerMP player = (EntityPlayerMP) event.player;
        player.getCapability(ProfileProvider.PROFILE_CAP, null);
    }

    @SubscribeEvent
    public static void playerRespawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
        if (event.player.getEntityWorld().isRemote) {
            ReticleRenderer.reticle = null;
            return;
        }
        final EntityPlayerMP player = (EntityPlayerMP) event.player;
        player.getCapability(ProfileProvider.PROFILE_CAP, null);
    }
}
