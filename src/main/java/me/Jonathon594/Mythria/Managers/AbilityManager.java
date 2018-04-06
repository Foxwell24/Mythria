package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.CombatMessages;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Items.MythriaItemHammer;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class AbilityManager {

    private static HashMap<EntityPlayer, PVPMode> pvpModes = new HashMap<>();

    public static void onLivingHurt(final IProfile profile, final LivingHurtEvent event, final EntityPlayer source) {
    }

    @SideOnly(Side.CLIENT)
    public static void onJumpClient() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if(player == null) return;
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        if(profile == null) return;
        Deity d = DeityManager.getSelectedDeities().get(player.getEntityId());
        if(profile.hasFlag(AttributeFlag.ELIANA_BLESSING_ELYTRA) || (d != null && d.equals(Deity.ELIANA))) {
            if(player.isSneaking()) {
                player.addVelocity(player.getLookVec().x, 1, player.getLookVec().z);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientTick() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if(player == null) return;
        if(player.world.getWorldTime() % 20 != 0) return;
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        if(profile == null) return;
        Deity d = DeityManager.getSelectedDeities().get(player.getEntityId());
        if(profile.hasFlag(AttributeFlag.ELIANA_BLESSING_ELYTRA) || (d != null && d.equals(Deity.ELIANA))) {
            if(player.isElytraFlying()) {
                if(player.getFoodStats().getFoodLevel() > 6) {
                    if(player.posY < 345) {
                        Vec3d vel = new Vec3d(player.motionX, player.motionY, player.motionZ);
                        if(!player.isSneaking()) {
                            Vec3d vec = new Vec3d(player.getLookVec().x, 0.767, player.getLookVec().z).scale(0.35);
                            player.addVelocity(vec.x, vec.y, vec.z);
                        } else if (vel.lengthVector() > 0.5) {
                            Vec3d neg = vel.scale(-0.5);
                            player.addVelocity(neg.x, neg.y, neg.z);
                        } else {
                            player.addVelocity(0, 0.4, 0);
                        }
                    }
                }
            }
        }
    }

    public static void handleCombatSkills(LivingHurtEvent event) {
        DamageSource ds = event.getSource();
        Entity trueSource = ds.getTrueSource();
        EntityLivingBase entityLiving = event.getEntityLiving();
        if(entityLiving == null) return;
        Random random = new Random();

        if (event.isCanceled())
            return;
        if (event.getEntity().world.isRemote)
            return;

        if(trueSource instanceof EntityPlayer) { //Offensive Combat
            EntityPlayer player = (EntityPlayer) trueSource;
            IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
            Item item = null;
            boolean melee = false;
            if(ds.damageType.equalsIgnoreCase("player")) {
                item = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
                melee = true;
            }
            if(ds.damageType.equalsIgnoreCase("playeroffhand")) {
                item = player.getHeldItem(EnumHand.OFF_HAND).getItem();
                melee = true;
            }

            if (melee) {
                if (MythriaUtil.isAxe(item)) {
                    handleOffensiveAxes(player, entityLiving, profile, event, random);
                    profile.addSkillExperience(MythicSkills.AXES, event.getAmount() * 3, (EntityPlayerMP) trueSource);
                    float meleeMult = MathHelper.clamp((float) profile.getAttribute(Attribute.STRENGTH) / 100.0f, 0, 1);
                    event.setAmount(event.getAmount() * (1+meleeMult/2));
                }
                if (item instanceof MythriaItemHammer) {
                    float meleeMult = MathHelper.clamp((float) profile.getAttribute(Attribute.STRENGTH) / 100.0f, 0, 1);
                    event.setAmount(event.getAmount() * (1+meleeMult/2));
                }
                if (MythriaUtil.isSword(item)) {
                    handleOffensiveSwords(player, entityLiving, profile, event, random);
                    profile.addSkillExperience(MythicSkills.SWORDS, event.getAmount() * 3, (EntityPlayerMP) trueSource);
                    float meleeMult = MathHelper.clamp((float) profile.getAttribute(Attribute.DEXTERITY) / 100.0f, 0, 1);
                    event.setAmount(event.getAmount() * (1+meleeMult/2));
                }
                if (MythriaUtil.isDagger(item)) {
                    float meleeMult = MathHelper.clamp((float) profile.getAttribute(Attribute.DEXTERITY) / 100.0f, 0, 1);
                    event.setAmount(event.getAmount() * (1+meleeMult/2));
                    handleOffensiveDaggers(player, entityLiving, profile, event, random);
                    profile.addSkillExperience(MythicSkills.DAGGERS, event.getAmount() * 3, (EntityPlayerMP) trueSource);
                }
                if (item == null || item.equals(Items.AIR)) {
                    float meleeMult = MathHelper.clamp((float) profile.getAttribute(Attribute.DEXTERITY) / 100.0f, 0, 1);
                    event.setAmount(event.getAmount() * (1+meleeMult/2));
                    handleOffensiveUnarmed(player, entityLiving, profile, event, random);
                    profile.addSkillExperience(MythicSkills.UNARMED, event.getAmount() * 3, (EntityPlayerMP) trueSource);
                }
                float meleeMult = MathHelper.clamp((float) profile.getAttribute(Attribute.STRENGTH) / 100.0f, 0, 1);
                event.setAmount(event.getAmount() * (1+meleeMult));

            }
            if(ds.getImmediateSource() instanceof EntityArrow) {
                handleOffensiveArchery(player, entityLiving, (EntityArrow) ds.getImmediateSource(), profile, event, random);
                profile.addSkillExperience(MythicSkills.ARCHERY, event.getAmount() * 3, (EntityPlayerMP) trueSource);
                float dexMult = MathHelper.clamp((float) profile.getAttribute(Attribute.DEXTERITY) / 100.0f, 0, 1);
                event.setAmount(event.getAmount() * (1+dexMult));
                float strMult = MathHelper.clamp((float) profile.getAttribute(Attribute.STRENGTH) / 100.0f, 0, 1);
                event.setAmount(event.getAmount() * (1+strMult/2));
            }
        } else if(entityLiving instanceof EntityPlayer) { // Defensive Combat
            EntityPlayer player = (EntityPlayer) entityLiving;
            IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
            Item item = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
            Item item2 = player.getHeldItem(EnumHand.OFF_HAND).getItem();

            if(trueSource instanceof EntityLivingBase) {
                if (player.isActiveItemStackBlocking()) {
                    handleDefensiveShields(player, (EntityLivingBase) trueSource, profile, event, random);
                }
                
                if(MythriaUtil.isSword(item) || MythriaUtil.isSword(item2)) {
                    handleDefensiveSwords(player, (EntityLivingBase) trueSource, profile, event, random);
                }

                handleDodge(player, profile, event, random);

                if(item.equals(Items.AIR) || item2.equals(Items.AIR)) {
                    handleDefensiveUnarmed(player, profile, event, random);
                }
            }

            profile.setConsumable(Consumable.PAIN, profile.getConsumables().get(Consumable.PAIN) + event.getAmount());
        }
    }

    private static void handleDodge(EntityPlayer player, IProfile profile, LivingHurtEvent event, Random random) {
        if (profile.hasFlag(AttributeFlag.DODGE)) { // Level 10 Skill
            double chance = scaleChance(100, 0.15, 0.25, profile.getSkillLevel(MythicSkills.AGILITY));
            if(random.nextDouble() < chance) {
                sendCombatMessage(player, CombatMessages.DODGE);
                event.setAmount(0);
            }
        }
    }

    private static void handleOffensiveArchery(EntityPlayer player, EntityLivingBase entityLiving, EntityArrow immediateSource, IProfile profile, LivingHurtEvent event, Random random) {
        if (profile.hasFlag(AttributeFlag.SKILLSHOT_1)) { // Level 10 Skill
            event.setAmount(event.getAmount()+2);
        }

        if (profile.hasFlag(AttributeFlag.ARROW_STUN) && !entityLiving.isActiveItemStackBlocking()) { // Level 25 Skill
            double chance = scaleChance(100, 0.1, 0.3, profile.getSkillLevel(MythicSkills.ARCHERY));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(2), 80, 3, false, false);
                entityLiving.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.ARROW_STUN);
            }
        }

        if (profile.hasFlag(AttributeFlag.SKILLSHOT_2)) { // Level 50 Skill
            event.setAmount(event.getAmount()+3);
        }

        if (profile.hasFlag(AttributeFlag.ARROW_BLIND) && !entityLiving.isActiveItemStackBlocking()) { // Level 75 Skill
            double chance = scaleChance(100, 0.1, 0.3, profile.getSkillLevel(MythicSkills.ARCHERY));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(15), 200, 4, false, false);
                entityLiving.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.ARROW_STUN);
            }
        }

        if (profile.hasFlag(AttributeFlag.ARROW_WEAKEN) && !entityLiving.isActiveItemStackBlocking()) { // Level 100 Skill
            if(random.nextDouble() < 0.2) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(15), 200, 4, false, false);
                entityLiving.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.ARROW_WEAKEN);
            }
        }

        if (profile.hasFlag(AttributeFlag.ARROW_EXECUTE) && !entityLiving.isActiveItemStackBlocking()) { // Level 125 Skill
            if(random.nextDouble() < 0.01) {
                entityLiving.setHealth(0);
                sendCombatMessage(player, CombatMessages.ARROW_EXECUTE);
            }
        }
    }

    private static void handleOffensiveDaggers(EntityPlayer player, EntityLivingBase entityLiving, IProfile profile, LivingHurtEvent event, Random random) {
        int armor = entityLiving.getTotalArmorValue();
        if(armor > 7 || entityLiving.isActiveItemStackBlocking()) return;

        if (profile.hasFlag(AttributeFlag.DAGGER_SKILL_1)) { // Level 10 Skill
            event.setAmount(event.getAmount()+1);
        }

        if (profile.hasFlag(AttributeFlag.CRITICAL_DAGGER)) { // Level 25 Skill
            double chance = scaleChance(100, 0.1, 0.3, profile.getSkillLevel(MythicSkills.DAGGERS));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount() * 5);
                sendCombatMessage(player, CombatMessages.CRITICAL_DAGGER);
            }
        }

        if (profile.hasFlag(AttributeFlag.DAGGER_SKILL_2)) { // Level 50 Skill
            event.setAmount(event.getAmount()+1);
        }

        if (profile.hasFlag(AttributeFlag.DAGGER_SKILL_3)) { // Level 75 Skill
            event.setAmount(event.getAmount()+2);
        }

        if (profile.hasFlag(AttributeFlag.FATAL_DAGGER)) { // Level 100 Skill
            double chance = scaleChance(100, 0.1, 0.15, profile.getSkillLevel(MythicSkills.DAGGERS));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount() * 4);
                sendCombatMessage(player, CombatMessages.FATAL_STRIKE);
            }
        }

        if (profile.hasFlag(AttributeFlag.THROAT_SLICE) && !entityLiving.isActiveItemStackBlocking()) { // Level 125 Skill
            if(random.nextDouble() < 0.01) {
                entityLiving.setHealth(0);
                sendCombatMessage(player, CombatMessages.THROAT_SLICE);
            }
        }
    }

    private static void handleDefensiveUnarmed(EntityPlayer player, IProfile profile, LivingHurtEvent event, Random random) {
        if (profile.hasFlag(AttributeFlag.DEFLECT)) { // Level 25 Skill
            double chance = scaleChance(100, 0.15, 0.25, profile.getSkillLevel(MythicSkills.DAGGERS));
            if(random.nextDouble() < chance) {
                sendCombatMessage(player, CombatMessages.DEFLECT);
                event.setAmount(0);
            }
        }
    }

    private static void handleOffensiveUnarmed(EntityPlayer player, EntityLivingBase entityLiving, IProfile profile, LivingHurtEvent event, Random random) {
        if (profile.hasFlag(AttributeFlag.FIST_TRAINING_1)) { // Level 10 Skill
            event.setAmount(event.getAmount()+3);
        }

        if (profile.hasFlag(AttributeFlag.DAZING_PUNCH) && !entityLiving.isActiveItemStackBlocking()) { // Level 25 Skill
            double chance = scaleChance(100, 0.1, 0.3, profile.getSkillLevel(MythicSkills.UNARMED));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(9), 80, 4, false, false);
                player.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.DAZING_PUNCH);
            }
        }

        if (profile.hasFlag(AttributeFlag.CRITICAL_FIST)) { // Level 50 Skill
            double chance = scaleChance(100, 0.1, 0.3, profile.getSkillLevel(MythicSkills.UNARMED));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount() * 2);
                sendCombatMessage(player, CombatMessages.CRITICAL_FIST);
            }
        }

        if (profile.hasFlag(AttributeFlag.DISARM) && !entityLiving.isActiveItemStackBlocking()) { // Level 75 Skill
            double chance = scaleChance(100, 0.1, 0.3, profile.getSkillLevel(MythicSkills.UNARMED));
            for (EnumHand hand = EnumHand.MAIN_HAND; hand != EnumHand.OFF_HAND; hand = EnumHand.OFF_HAND) {
                if (random.nextDouble() < chance) {
                    ItemStack is = entityLiving.getHeldItem(hand);
                    if(is == null || is.isEmpty()) continue;
                    EntityItem item = new EntityItem(player.world, entityLiving.posX, entityLiving.posY, entityLiving.posZ, is);
                    entityLiving.setHeldItem(hand, ItemStack.EMPTY);
                    item.setPickupDelay(20);
                    sendCombatMessage(player, CombatMessages.DISARM);
                    player.world.spawnEntity(item);
                }
            }
        }

        if (profile.hasFlag(AttributeFlag.PRESSURE_POINT) && !entityLiving.isActiveItemStackBlocking()) { // Level 100 Skill
            double chance = scaleChance(100, 0.05, 0.15, profile.getSkillLevel(MythicSkills.UNARMED));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(2), 80, 1, false, false);
                player.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.PRESSURE_POINT_SLOW);
            }
            else if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(15), 80, 0, false, false);
                player.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.PRESSURE_POINT_BLIND);
            }
            else if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(9), 80, 4, false, false);
                player.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.PRESSURE_POINT_NAUSEA);
            }
        }

        if (profile.hasFlag(AttributeFlag.CARDIAC_PUNCH) && !entityLiving.isActiveItemStackBlocking()) { // Level 125 Skill
            if(random.nextDouble() < 0.01) {
                entityLiving.setHealth(0);
                sendCombatMessage(player, CombatMessages.CARDIAC_PUNCH);
            }
        }
    }

    private static void handleDefensiveSwords(EntityPlayer player, EntityLivingBase trueSource, IProfile profile, LivingHurtEvent event, Random random) {
        if (profile.hasFlag(AttributeFlag.PARRY)) { // Level 10 Skill
            double chance = scaleChance(100, 0.5, 0.6, profile.getSkillLevel(MythicSkills.SWORDS));
            if(random.nextDouble() < chance) {
                sendCombatMessage(player, CombatMessages.PARRY);
                event.setAmount(0);
            }
        }
        if (profile.hasFlag(AttributeFlag.COUNTERSTRIKE)) { // Level 10 Skill
            double chance = scaleChance(100, 0.25, 0.35, profile.getSkillLevel(MythicSkills.SWORDS));
            if(random.nextDouble() < chance) {
                sendCombatMessage(player, CombatMessages.COUNTERSTRIKE);
                event.setAmount(0);
                DamageSource ds = DamageSource.causeThrownDamage(player, null);
                trueSource.attackEntityFrom(ds, event.getAmount() /2);
            }
        }
    }

    private static void handleDefensiveShields(EntityPlayer player, EntityLivingBase entityLiving, IProfile profile, LivingHurtEvent event, Random random) {
        if (profile.hasFlag(AttributeFlag.STAGGER)) { // Level 10 Skill
            double chance = scaleChance(100, 0.05, 0.15, profile.getSkillLevel(MythicSkills.BLOCK));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(4), 60, 1, false, false);
                entityLiving.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.STAGGER);
            }
        }
        if (profile.hasFlag(AttributeFlag.FORTIFY)) { // Level 25 Skill
            double chance = scaleChance(100, 0.05, 0.15, profile.getSkillLevel(MythicSkills.BLOCK));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(22), 200, 0, false, false);
                player.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.FORTIFY);
            }
        }
        if (profile.hasFlag(AttributeFlag.SHIELD_BASH)) { // Level 50 Skill
            double chance = scaleChance(100, 0.35, 0.45, profile.getSkillLevel(MythicSkills.BLOCK));
            if(random.nextDouble() < chance) {
                Vec3d v = player.getLookVec();
                v.crossProduct(new Vec3d(1,0,1));
                v.normalize();
                v = new Vec3d(v.x, 0.4, v.z);
                entityLiving.addVelocity(v.x, v.y, v.z);
                entityLiving.velocityChanged = true;
                player.addVelocity(v.x, 0, v.y);
                player.velocityChanged = true;
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(2), 60, 0, false, false);
                entityLiving.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.SHIELD_BASH);
            }
        }
        if (profile.hasFlag(AttributeFlag.STAGGER_2)) { // Level 75 Skill
            double chance = scaleChance(100, 0.05, 0.15, profile.getSkillLevel(MythicSkills.BLOCK));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(18), 60, 1, false, false);
                entityLiving.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.STAGGER_2);
            }
        }
        if (profile.hasFlag(AttributeFlag.SHIELD_DISARM)) { // Level 100 Skill
            double chance = scaleChance(100, 0.35, 0.45, profile.getSkillLevel(MythicSkills.BLOCK));
            for (EnumHand hand = EnumHand.MAIN_HAND; hand != EnumHand.OFF_HAND; hand = EnumHand.OFF_HAND) {
                if (random.nextDouble() < chance) {
                    final float y = (float) (Math.random() * 360);
                    Vec3d v = new Vec3d(Math.cos(y), 1.8, Math.cos(y));

                    ItemStack is = entityLiving.getHeldItem(hand);
                    if(is == null || is.isEmpty()) continue;
                    EntityItem item = new EntityItem(player.world, entityLiving.posX, entityLiving.posY, entityLiving.posZ, is);
                    entityLiving.setHeldItem(hand, ItemStack.EMPTY);
                    item.setVelocity(v.x, v.y, v.z);
                    item.velocityChanged = true;
                    player.world.spawnEntity(item);

                    sendCombatMessage(player, CombatMessages.SHIELD_DISARM);
                }
            }
        }
        if (profile.hasFlag(AttributeFlag.SHIELD_CHARGE)) { // Level 125 Skill
            double chance = scaleChance(100, 0.35, 0.45, profile.getSkillLevel(MythicSkills.BLOCK));
            if(random.nextDouble() < chance) {
                Vec3d v = player.getLookVec();
                v.crossProduct(new Vec3d(1,0,1));
                v.normalize();
                v.scale(1.3);
                entityLiving.addVelocity(v.x, 0.8, v.z);
                entityLiving.velocityChanged = true;
                player.addVelocity(v.x, 0, v.y);
                player.velocityChanged = true;
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(2), 60, 0, false, false);
                entityLiving.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.SHIELD_CHARGE);
            }
        }
    }

    private static void handleOffensiveSwords(EntityPlayer player, EntityLivingBase entityLiving, IProfile profile, LivingHurtEvent event, Random random) {
        if (profile.hasFlag(AttributeFlag.CRITICAL_SWORD)) { // Level 25 Skill
            double chance = scaleChance(100, 0.1, 0.2, profile.getSkillLevel(MythicSkills.SWORDS));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount() * 1.2f);
                sendCombatMessage(player, CombatMessages.CRITICAL_SWORD);
            }
        }

        if (profile.hasFlag(AttributeFlag.SWORD_MASTERY)) { // Level 50 Skill
            event.setAmount(event.getAmount()+2);
        }

        if (profile.hasFlag(AttributeFlag.FATAL_SWORD)) { // Level 100 Skill
            double chance = scaleChance(100, 0.03, 0.04, profile.getSkillLevel(MythicSkills.SWORDS));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount() * 4f);
                sendCombatMessage(player, CombatMessages.FATAL_STRIKE);
            }
        }

        if (profile.hasFlag(AttributeFlag.DECAPICATE) && !entityLiving.isActiveItemStackBlocking()) { // Level 125 Skill
            if(random.nextDouble() < 0.01) {
                entityLiving.setHealth(0);
                sendCombatMessage(player, CombatMessages.DECAPITATE);
            }
        }
    }

    private static void handleOffensiveAxes(EntityPlayer player, EntityLivingBase entityLiving, IProfile profile, LivingHurtEvent event, Random random) {
        boolean isBlocking = entityLiving.isActiveItemStackBlocking();

        if (profile.hasFlag(AttributeFlag.AXE_MASTERY)) { // Level 10 Skill
            event.setAmount(event.getAmount()+2);
        }

        if (profile.hasFlag(AttributeFlag.AXE_RAGE)) { // Level 25 Skill
            double chance = scaleChance(100, 0.05, 0.15, profile.getSkillLevel(MythicSkills.AXES));
            if(random.nextDouble() < chance) {
                PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(5), 60, 1, false, false);
                player.addPotionEffect(potionEffect);
                sendCombatMessage(player, CombatMessages.AXE_RAGE);
            }
        }

        if (profile.hasFlag(AttributeFlag.AXE_SMASH)) { // Level 50 Skill
            double chance = scaleChance(100, 0.05, 0.15, profile.getSkillLevel(MythicSkills.AXES));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount()+2);
                sendCombatMessage(player, CombatMessages.AXE_SMASH);
            }
        }

        if (profile.hasFlag(AttributeFlag.CRITICAL_AXE)) { // Level 75 Skill
            double chance = scaleChance(100, 0.1, 0.2, profile.getSkillLevel(MythicSkills.AXES));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount() * 1.2f);
                sendCombatMessage(player, CombatMessages.CRITICAL_AXE);
            }
        }

        if (profile.hasFlag(AttributeFlag.FATAL_AXE)) { // Level 100 Skill
            double chance = scaleChance(100, 0.03, 0.04, profile.getSkillLevel(MythicSkills.AXES));
            if(random.nextDouble() < chance) {
                event.setAmount(event.getAmount() * 4f);
                sendCombatMessage(player, CombatMessages.FATAL_STRIKE);
            }
        }

        if (profile.hasFlag(AttributeFlag.LEGENDARY_AXE) && !entityLiving.isActiveItemStackBlocking()) { // Level 125 Skill
            if(random.nextDouble() < 0.1) {
                event.setAmount(Math.max(event.getAmount(), entityLiving.getMaxHealth())*3);
                sendCombatMessage(player, CombatMessages.LEGENDARY_AXE);
            }
        }
    }

    private static void sendCombatMessage(EntityPlayer target, String message) {
        target.sendMessage(new TextComponentString(MythriaConst.CONT_COLOR + "" + message));
    }

    private static double scaleChance(int maxLevel, double minChance, double maxChance, Integer level) {
        if(maxChance == 0) return 0.0;
        double prop = MathHelper.clamp((double) level / maxLevel, 0, 1);
        double slider = maxChance - minChance;
        double adjusted = slider * prop;
        double chance = minChance + adjusted;
        return chance;
    }

    public static void handleFallDamage(LivingFallEvent event, EntityPlayer player, IProfile profile) {
        if (PerkManager.hasAttributeFlag(profile, AttributeFlag.SOFTLANDING))
            if (player.isSneaking())
                event.setDistance(Math.max(event.getDistance() - 8, 0));
            else
                event.setDistance(Math.max(event.getDistance() - 5, 0));
    }

    // TODO BUG TEST THIS FUNCTIONALITY
    public static void handlePlayerInteractPlayer(EntityPlayer player, EntityPlayer target, IProfile profile, IProfile targetProfile) {
        if(profile.hasFlag(AttributeFlag.PICKPICKET)) {
            int thievery = profile.getSkillLevel(MythicSkills.THIEVERY);
            double chance =  scaleChance(100, 0.1, 0.8, thievery);
            int oThievery = targetProfile.getSkillLevel(MythicSkills.THIEVERY);
            double reduction = 1-scaleChance(100, 0.0, 0.5, oThievery);
            chance *= reduction;
            if(Math.random() < chance) {
                ArrayList<ItemStack> isList = new ArrayList<>();
                for(int i = 0; i < target.inventory.getSizeInventory(); i++) {
                    ItemStack is = target.inventory.getStackInSlot(i);
                    if(is.getItem() instanceof ItemBlock) continue;
                    if(is == null || is.isEmpty()) continue;
                    isList.add(is.copy());
                }
                Collections.shuffle(isList);
                if(isList.size() == 0) return;
                ItemStack toTake = isList.get(0);
                int slot = target.inventory.getSlotFor(toTake);
                target.inventory.getStackInSlot(slot).shrink(1);
                toTake.setCount(1);
                if(!player.inventory.addItemStackToInventory(toTake)) {
                    EntityItem item = new EntityItem(player.world, player.posX, player.posY, player.posZ, toTake);
                    item.setPickupDelay(20);
                    player.world.spawnEntity(item);
                    sendCombatMessage(player, CombatMessages.PICKPOCKET);
                }
            } else {
                sendCombatMessage(target, CombatMessages.PICKPOCKET_FAIL_OTHER);
                sendCombatMessage(player, CombatMessages.PICKPOCKET_FAIL);
            }
        }
    }
}
