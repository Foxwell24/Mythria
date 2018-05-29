package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Food.FoodProvider;
import me.Jonathon594.Mythria.Capability.Food.IFood;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.CureCondition;
import me.Jonathon594.Mythria.DataTypes.HealthCondition;
import me.Jonathon594.Mythria.DataTypes.HealthConditionInstance;
import me.Jonathon594.Mythria.DataTypes.ObtainCondition;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Event.NewDayEvent;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class HealthManager {
    private static ArrayList<HealthCondition> registeredConditions = new ArrayList<>();
    private static int tick;

    public static void addHealhCondition(final HealthCondition hc) {
        if (registeredConditions.contains(hc))
            return;
        registeredConditions.add(hc);
    }

    public static ArrayList<HealthCondition> getRegisteredConditions() {
        return registeredConditions;
    }

    public static void Initialize() {
        // Ankles
        new HealthCondition("Sprained Left Ankle", 3, true, 4, Items.BONE, HealthConditionTree.SPRAIN_LEFT_ANKLE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 2, 0.25, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 4, 0.1, null))
                .addStatModifier(StatType.MAX_SPEED, -0.01).addStatModifier(StatType.MAX_HEALTH, -2)
                .addStatModifier(StatType.MAX_STAMINA, -50).addStatModifier(StatType.MAX_WEIGHT, -50);
        new HealthCondition("Broken Left Ankle", 12, true, 4, Items.BONE, HealthConditionTree.BROKEN_LEFT_ANKLE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 4, 0.25, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 6, 0.1, null))
                .addStatModifier(StatType.MAX_SPEED, -0.02).addStatModifier(StatType.MAX_HEALTH, -4)
                .addStatModifier(StatType.MAX_STAMINA, -100).addStatModifier(StatType.MAX_WEIGHT, -75);

        new HealthCondition("Sprained Right Ankle", 3, true, 4, Items.BONE, HealthConditionTree.SPRAIN_RIGHT_ANKLE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 2, 0.25, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 4, 0.1, null))
                .addStatModifier(StatType.MAX_SPEED, -0.01).addStatModifier(StatType.MAX_HEALTH, -2)
                .addStatModifier(StatType.MAX_STAMINA, -50).addStatModifier(StatType.MAX_WEIGHT, -50);
        new HealthCondition("Broken Right Ankle", 12, true, 4, Items.BONE, HealthConditionTree.BROKEN_RIGHT_ANKLE)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 4, 0.25, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 6, 0.1, null))
                .addStatModifier(StatType.MAX_SPEED, -0.02).addStatModifier(StatType.MAX_HEALTH, -4)
                .addStatModifier(StatType.MAX_STAMINA, -100).addStatModifier(StatType.MAX_WEIGHT, -75);

        // Arms
        new HealthCondition("Broken Left Arm", 12, true, 4, Items.BONE, HealthConditionTree.LEFT_ARM)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 6, 0.25, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 6, 0.25, null))
                .addStatModifier(StatType.MAX_HEALTH, -4);
        new HealthCondition("Broken Right Arm", 12, true, 4, Items.BONE, HealthConditionTree.RIGHT_ARM)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 6, 0.25, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 6, 0.25, null))
                .addStatModifier(StatType.MAX_HEALTH, -4);

        // Ribs
        new HealthCondition("Broken Ribs", 20, true, 6, Items.BONE, HealthConditionTree.RIBS)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 8, 0.25, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 8, 0.25, null))
                .addStatModifier(StatType.MAX_HEALTH, -6).addStatModifier(StatType.MAX_WEIGHT, -100);

        new HealthCondition("Stomach Virus", 2, true, 0, Items.SLIME_BALL, HealthConditionTree.STOMACH_VIRUS)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.RAW_FOOD, 0, 0.75, null))
                .addStatModifier(StatType.MAX_SPEED, -0.01).addStatModifier(StatType.MAX_STAMINA, -50)
                .addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 4));

        new HealthCondition("Parasites", 2, true, 0, Items.SLIME_BALL, HealthConditionTree.PARASITES)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.RAW_FOOD, 0, 0.1, null))
                .addStatModifier(StatType.MAX_SPEED, -0.01).addStatModifier(StatType.MAX_STAMINA, -50)
                .addPotionEffect(new PotionEffect(MobEffects.HUNGER, 200, 4));

        // Skull
        new HealthCondition("Cracked Skull", 14, true, 8, Items.SKULL, HealthConditionTree.SKULL)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.MELEE, 9, 0.15, null))
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FALL, 14, 0.05, null))
                .addStatModifier(StatType.MAX_SPEED, -0.01).addStatModifier(StatType.MAX_HEALTH, -6)
                .addStatModifier(StatType.MAX_STAMINA, -100).addStatModifier(StatType.MAX_WEIGHT, -25);

        // Conditions
        new HealthCondition("Hypothermia I", 1, true, 2, Item.getItemFromBlock(Blocks.ICE),
                HealthConditionTree.HYPOTHERMIA)
                .addObtainCondition(new ObtainCondition(ObtainConditionType.FREEZING, 0, 0.01, null))
                .addStatModifier(StatType.MAX_SPEED, -0.03).addStatModifier(StatType.MAX_STAMINA, -50)
                .setProgressionType(HealthProgressionType.PROGRESS).setProgressionTarget("Hypothermia II");
        new HealthCondition("Hypothermia II", 1, true, 6, Item.getItemFromBlock(Blocks.ICE),
                HealthConditionTree.HYPOTHERMIA).addStatModifier(StatType.MAX_SPEED, -0.08)
                .addStatModifier(StatType.MAX_STAMINA, -100).addStatModifier(StatType.MAX_HEALTH, -4)
                .setProgressionType(HealthProgressionType.PROGRESS).setProgressionTarget("Hypothermia III");
        new HealthCondition("Hypothermia III", 1, true, 12, Item.getItemFromBlock(Blocks.ICE),
                HealthConditionTree.HYPOTHERMIA).addStatModifier(StatType.MAX_SPEED, -0.16)
                .addStatModifier(StatType.MAX_STAMINA, -200).addStatModifier(StatType.MAX_HEALTH, -4)
                .setProgressionType(HealthProgressionType.PROGRESS).setProgressionTarget("Hypothermia IV");
        new HealthCondition("Hypothermia IV", 16, true, 16, Item.getItemFromBlock(Blocks.ICE),
                HealthConditionTree.HYPOTHERMIA).addStatModifier(StatType.MAX_SPEED, -0.3)
                .addStatModifier(StatType.MAX_STAMINA, -500).addStatModifier(StatType.MAX_HEALTH, -10)
                .setProgressionType(HealthProgressionType.DEATH);
    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        final EntityLivingBase e = event.getEntityLiving();
        if (e.getEntityWorld().isRemote)
            return;
        if (!(e instanceof EntityPlayer))
            return;

        final EntityPlayer p = (EntityPlayer) event.getEntityLiving();
        final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);
        for (final HealthCondition hc : registeredConditions)
            if (event.getSource().damageType.equals("fall")) { // Fall Damage
                final ObtainCondition oc = hc.getObtainCondition(ObtainConditionType.FALL);
                if (oc != null)
                    if (event.getAmount() >= oc.getThreshhold() && Math.random() < oc.getChance()) {
                        final double over = event.getAmount() - oc.getThreshhold();
                        addHealthConditionToProfile(profile, hc, Math.min(over / 4, 1));
                    }
            }
    }

    @SubscribeEvent
    public static void onPlayerConsume (LivingEntityUseItemEvent.Finish event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) return;
        if(entity.world.isRemote) return;
        if(entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            final IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
            if (event.getItem().hasCapability(FoodProvider.FOOD_CAP, null)) {
                if(FoodManager.isRaw(event.getItem())) {

                    for (final HealthCondition hc : registeredConditions) {
                        final ObtainCondition oc = hc.getObtainCondition(ObtainConditionType.RAW_FOOD);
                        if (oc != null) {
                            if(Math.random() < oc.getChance()) {
                                addHealthConditionToProfile(profile, hc, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void addHealthConditionToProfile(final IProfile profile, final HealthCondition hc,
                                                    final double severity) {
        if (!profile.getCreated())
            return;
        profile.addHealthCondition(new HealthConditionInstance(hc, severity));
        profile.getPlayer().sendMessage(new TextComponentString(MythriaConst.CONT_COLOR + hc.getName() + "!"));
    }

    @SubscribeEvent
    public static void onNewDay(final NewDayEvent event) {
        for (final EntityPlayer p : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                .getPlayers()) {
            final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);
            final ArrayList<HealthConditionInstance> toRemove = new ArrayList<>();
            final ArrayList<HealthConditionInstance> toAdd = new ArrayList<>();
            for (final HealthConditionInstance hci : profile.getHealthConditions()) {
                hci.setAge(hci.getAge() + 1);
                if (hci.getAge() > (hci.isCured() ? hci.getCondition().getCureTime() : hci.getCondition().getDuration()))
                    if (hci.getCondition().getProgressionType().equals(HealthProgressionType.PROGRESS)) {
                        if (hci.getCondition().getProgressionTarget() != "") {
                            final HealthCondition toProgress = getHealthConditionByName(
                                    hci.getCondition().getProgressionTarget());
                            if (toProgress != null) {
                                toRemove.add(hci);
                                toAdd.add(new HealthConditionInstance(toProgress, hci.getSeverity()));
                            }
                        }
                    } else if (hci.getCondition().getProgressionType().equals(HealthProgressionType.DEATH)) {
                        p.setHealth(0);
                    } else if (hci.getCondition().isCurable())
                        toRemove.add(hci);
            }
            profile.getHealthConditions().removeAll(toRemove);
            profile.getHealthConditions().addAll(toAdd);
            StatManager.UpdateStats((EntityPlayerMP) p);
        }
    }

    public static HealthCondition getHealthConditionByName(final String string) {
        for (final HealthCondition hc : registeredConditions)
            if (hc.getName().equals(string))
                return hc;
        return null;
    }

    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer p = event.player;
        if (p.getEntityWorld().isRemote)
            return;

        if (tick == 60) {
            tick = 0;
            final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);
            final double temp = profile.getConsumables().get(Consumable.TEMPERATURE);

            if (temp <= 8.5)
                for (final HealthCondition hc : registeredConditions) {
                    final ObtainCondition oc = hc.getObtainCondition(ObtainConditionType.FREEZING);
                    if (oc != null)
                        if (Math.random() < oc.getChance())
                            addHealthConditionToProfile(profile, hc, 1);
                }
            if (temp >= 14)
                for (final HealthConditionInstance hci : profile.getHealthConditions()) {
                    if(hci.getNextCureStep() != null && hci.getNextCureStep().equals(CureCondition.WARMTH)) {
                        hci.setCureStep(hci.getCureStep()+1);
                    }
                }

            // Update Potion Effects
            for(HealthConditionInstance hci : profile.getHealthConditions()) {
                for(PotionEffect pe : hci.getCondition().getPotionEffects()) {
                    PotionEffect current = p.getActivePotionEffect(pe.getPotion());
                    if(current == null || current.getDuration() < 60) {
                        p.addPotionEffect(pe);
                    }
                }
            }
        }
        tick++;
    }

    public static void curePlayer(EntityPlayer player) {
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.getHealthConditions().clear();
    }
}
