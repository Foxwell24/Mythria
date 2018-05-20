package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import scala.collection.immutable.Stream;

public class StatManager {

    final private static int ticksPerUpdate = 20;

    private static int currentTick = 0;

    public static void applyInitialStats(final IProfile profile) {
        profile.setAttributePoints(0);
        for (final Consumable c : Consumable.values()) {
            double v = 0.0;
            switch (c) {
                case STAMINA:
                    v = 0.0;
                    break;
                case TEMPERATURE:
                    v = 10.0;
                    break;
                case WEIGHT:
                    v = 0.0;
                    break;
                case HUNGER:
                    v = 15.0;
                    break;
                case THIRST:
                    v = 15.0;
                    break;
                case FATIGUE:
                    v = 0.0;
                    break;
            }
            profile.getConsumables().put(c, v);
        }
    }

    public static void onJump(final EntityPlayer player, final IProfile p) {
        ChargeConsumable(player, 10, Consumable.STAMINA);
    }

    public static boolean ChargeConsumable(final EntityPlayer player, final int i, final Consumable consumable) {
        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (p.getConsumables().get(consumable) >= i) {
            p.setConsumable(consumable, p.getConsumables().get(consumable) - i);
            switch (consumable) {
                case STAMINA:
                    final double fatigueMitigation = getTotalFatigueMitigation(p);
                    p.setConsumable(Consumable.FATIGUE,
                            p.getConsumables().get(Consumable.FATIGUE) + (double) i / (p.getStat(StatType.MAX_STAMINA) * 10.0) * (1 - fatigueMitigation));
                    p.addSkillExperience(MythicSkills.AGILITY, (double) i / 10, (EntityPlayerMP) player);
                case FATIGUE:
                    break;
                case HUNGER:
                    break;
                case TEMPERATURE:
                    break;
                case THIRST:
                    break;
                case WEIGHT:
                    break;
            }
            return true;
        }
        return false;
    }

    private static double getTotalFatigueMitigation(final IProfile p) {
        double fm = 0;
        for (final Perk pa : p.getPlayerSkills()) {
            if (pa == null)
                continue;
            fm += pa.getFatigueMitigation();
        }
        fm = MathHelper.clamp(fm, 0, 0.8);
        return fm;
    }

    public static void onTick(final ServerTickEvent event) {
        if (WeightManager.WeightUpdateQue.size() > 0) {
            UpdateCarryWeight(WeightManager.WeightUpdateQue.get(0));
            WeightManager.WeightUpdateQue.remove(0);
        }

        currentTick++;
        if (currentTick != ticksPerUpdate)
            return;
        currentTick = 1;
        final PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
        for (final EntityPlayerMP p : players.getPlayers())
            UpdateStats(p);
    }

    public static void UpdateCarryWeight(final EntityPlayer p) {
        double w = 0.0;
        for (final ItemStack is : p.inventoryContainer.inventoryItemStacks) {
            if(is.getItem() instanceof IItemData) {
                IItemData id = (IItemData) is.getItem();
                w+= id.getWeight();
            }
            else {
                if (!WeightManager.ItemWeightMap.containsKey(is.getItem()))
                    continue;
                final double iw = WeightManager.ItemWeightMap.get(is.getItem()) * is.getCount();
                w += iw;
            }
        }
        final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.setConsumable(Consumable.WEIGHT, w);
    }

    public static void UpdateStats(final EntityPlayerMP p) {
        final IProfile profile = p.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (!profile.getCreated())
            return;
        if (profile.getConsumables().get(Consumable.HUNGER) <= 0
                || profile.getConsumables().get(Consumable.THIRST) <= 0) {
            p.setHealth(0.0f);
        }

        double thirstMod = 1.0;
        if (profile.getConsumables().get(Consumable.TEMPERATURE) > 16.5)
            thirstMod = 3;
        if (profile.getConsumables().get(Consumable.TEMPERATURE) > 19)
            thirstMod = 10;

        double staminaRegen = 1.0;
        if (profile.getConsumables().get(Consumable.TEMPERATURE) < 11)
            staminaRegen = 0.75;
        if (profile.getConsumables().get(Consumable.TEMPERATURE) < 8.5)
            staminaRegen = 0.5;

        profile.setConsumable(Consumable.PAIN, profile.getConsumables().get(Consumable.PAIN) - 0.3);

        if (p.getFoodStats().getFoodLevel() >= 18)
            profile.setConsumable(Consumable.HUNGER, profile.getConsumables().get(Consumable.HUNGER) + 0.00023 / 3);
        if (p.getFoodStats().getFoodLevel() < 10)
            profile.setConsumable(Consumable.HUNGER, profile.getConsumables().get(Consumable.HUNGER) - 0.00023 / 3);
        profile.setConsumable(Consumable.THIRST,
                profile.getConsumables().get(Consumable.THIRST) - 0.005 / 3 * thirstMod);
        profile.setConsumable(Consumable.FATIGUE,
                profile.getConsumables().get(Consumable.FATIGUE) - (p.isPlayerSleeping() ? 0.005 : 0.001));
        profile.setConsumable(Consumable.STAMINA,
                MathHelper.clamp(
                        profile.getConsumables().get(Consumable.STAMINA)
                                + profile.getStat(StatType.MAX_STAMINA) * 0.02 * staminaRegen,
                        0, profile.getStat(StatType.MAX_STAMINA)
                                * (1 - profile.getConsumables().get(Consumable.FATIGUE))));

        final double bTemp = p.getEntityWorld().getBiome(p.getPosition()).getTemperature(p.getPosition()) * 5 + 10;
        if (profile.getConsumables().get(Consumable.TEMPERATURE) < bTemp - 0.5)
            profile.setConsumable(Consumable.TEMPERATURE, profile.getConsumables().get(Consumable.TEMPERATURE) + 0.5);
        else if (profile.getConsumables().get(Consumable.TEMPERATURE) > bTemp + 0.5)
            profile.setConsumable(Consumable.TEMPERATURE, profile.getConsumables().get(Consumable.TEMPERATURE) - 0.5);
        else
            profile.setConsumable(Consumable.TEMPERATURE, bTemp);

        if (p.getEntityWorld().getBlockState(p.getPosition()).getBlock().equals(Blocks.WATER)
                || p.getEntityWorld().getBlockState(p.getPosition()).getBlock().equals(Blocks.FLOWING_WATER)
                || p.getEntityWorld().getBlockState(p.getPosition().add(0, 1, 0)).getBlock().equals(Blocks.WATER)
                || p.getEntityWorld().getBlockState(p.getPosition().add(0, 1, 0)).getBlock()
                .equals(Blocks.FLOWING_WATER)) {
            profile.setConsumable(Consumable.THIRST, profile.getConsumables().get(Consumable.THIRST) + 3);
            profile.setConsumable(Consumable.FATIGUE, profile.getConsumables().get(Consumable.FATIGUE) - 0.1);
        }

        if (p.isSprinting())
            ChargeConsumable(p, 5, Consumable.STAMINA);

        UpdateMaxHealth(profile, p);
    }

    public static void UpdateMaxHealth(final IProfile profile, final EntityPlayer p) {
        MythriaUtil.applyMythriaAttributeModifier(p, "Mythria.ProfileHealth",
                profile.getStat(StatType.MAX_HEALTH), SharedMonsterAttributes.MAX_HEALTH);
    }

    public static void UpdateSpeed(final IProfile profile, final EntityPlayer p, final double old) {
        if (p.world.isRemote)
            return;
        double value = profile.getStat(StatType.MAX_SPEED);
        final double oldSpeed = p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
        if (profile.getConsumables().get(Consumable.WEIGHT) > profile.getStat(StatType.MAX_WEIGHT))
            value = -0.05;
        if (profile.getConsumables().get(Consumable.WEIGHT) > profile.getStat(StatType.MAX_WEIGHT)
                * 4)
            value = -100;
        if (profile.getConsumables().get(Consumable.FATIGUE) >= 0.75)
            value = -0.05;
        if (profile.getConsumables().get(Consumable.FATIGUE) >= 0.85)
            value = -100;

        final double compare = p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + value;

        if (compare != oldSpeed)
             MythriaUtil.applyMythriaAttributeModifier(p, "Mythria.ProfileSpeed", value,
                    SharedMonsterAttributes.MOVEMENT_SPEED);
    }

    public static void handleConsume(LivingEntityUseItemEvent.Finish event) {
        //Give thirst upon drinking a potion.
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null) return;
        if (entity.world.isRemote) return;
        if (entity instanceof EntityPlayer) {
            if(event.getItem().equals(Items.POTIONITEM)) {
                IProfile profile = entity.getCapability(ProfileProvider.PROFILE_CAP, null);
                profile.setConsumable(Consumable.THIRST, profile.getConsumables().get(Consumable.THIRST) + 4);
            }
        }
    }
}
