package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.DeityFavor.DeityFavorProvider;
import me.Jonathon594.Mythria.Capability.DeityFavor.IDeityFavor;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Cooldown;
import me.Jonathon594.Mythria.Enum.BlessingType;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlessingManager {
    public static void onLivingHurt(EntityPlayer player, IProfile profile, LivingAttackEvent event) {
        DamageSource ds = event.getSource();
        IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);

        if(ds.equals(DamageSource.DROWN)) {
            if(player.isInWater()) {
                if(df.hasBlessing(BlessingType.MELINIAS_WATER_BREATHING) || DeityManager.isDeity(Deity.ELIANA, player)) {
                    if (DeityManager.hasPower(Deity.MELINIAS, 1)) {
                        DeityManager.consumePower(Deity.MELINIAS, 1, player);
                        event.setCanceled(true);
                    }
                }
            }
            else if(df.hasBlessing(BlessingType.ELIANA_BREATHING) || DeityManager.isDeity(Deity.ELIANA, player)) {
                if(DeityManager.hasPower(Deity.ELIANA, 1)) {
                    DeityManager.consumePower(Deity.ELIANA, 1, player);
                    event.setCanceled(true);
                }
            }
        }
        if(ds.equals(DamageSource.WITHER)) {
            if(DeityManager.hasPower(Deity.FELIXIA, 1)) {
                DeityManager.consumePower(Deity.FELIXIA, 1, player);
                if (df.hasBlessing(BlessingType.FELIXIA_NO_WITHER) || DeityManager.isDeity(Deity.FELIXIA, player)) event.setCanceled(true);
            }
        }
        if(ds.equals(DamageSource.FALL)) {
            if(DeityManager.hasPower(Deity.ELIANA, 1)) {
                DeityManager.consumePower(Deity.ELIANA, 1, player);
                if (df.hasBlessing(BlessingType.ELIANA_NO_FALL) || DeityManager.isDeity(Deity.ELIANA, player)) event.setCanceled(true);
            }
        }
        if(ds.equals(DamageSource.LIGHTNING_BOLT)) {
            if(DeityManager.hasPower(Deity.RAIKA, 1)) {
                DeityManager.consumePower(Deity.RAIKA, 1, player);
                if (df.hasBlessing(BlessingType.RAIKA_SMITE) || DeityManager.isDeity(Deity.RAIKA, player)) event.setCanceled(true);
            }
        }
        if(ds.equals(DamageSource.LAVA) || ds.equals(DamageSource.IN_FIRE) || ds.equals(DamageSource.ON_FIRE) || ds.equals(DamageSource.HOT_FLOOR)) {
            if(DeityManager.hasPower(Deity.KASAI, 1)) {
                DeityManager.consumePower(Deity.KASAI, 1, player);
                if (df.hasBlessing(BlessingType.KASAI_NO_FIRE) || DeityManager.isDeity(Deity.KASAI, player)) event.setCanceled(true);
            }
        }
        if(ds.damageType.contains("explosion")) {
            if(DeityManager.hasPower(Deity.ASANA, 1)) {
                DeityManager.consumePower(Deity.ASANA, 1, player);
                if (df.hasBlessing(BlessingType.ASANA_NO_EXLODE) || DeityManager.isDeity(Deity.ASANA, player)) event.setCanceled(true);
            }
        }
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        long tick = player.world.getWorldTime();

        if(tick % 20 == 0) { //Every Second
            IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);

            if(df.hasBlessing(BlessingType.FELIXIA_HEALING) || DeityManager.isDeity(Deity.FELIXIA, player)) {
                if(player.world.getLight(player.getPosition()) > 13 && player.world.canSeeSky(player.getPosition())) {
                    if(DeityManager.hasPower(Deity.FELIXIA, 1)) {
                        DeityManager.consumePower(Deity.FELIXIA, 1, player);
                        player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth()));
                    }
                }
            }

            if(df.hasBlessing(BlessingType.FELIXIA_BURN)) {
                if(player.world.getLight(player.getPosition()) > 13 && player.world.canSeeSky(player.getPosition())) {
                    if(DeityManager.hasPower(Deity.FELIXIA, 1)) {
                        DeityManager.consumePower(Deity.FELIXIA, 1, player);
                        player.setFire(5);
                    }
                }
            }

            if(df.hasBlessing(BlessingType.ELIANA_SUFFOCATE)) { //Broken
                player.setAir(2);
            }

            if(df.hasBlessing(BlessingType.RAIKA_WRATH)) {
                if(player.world.canSeeSky(player.getPosition())) {
                    if(DeityManager.hasPower(Deity.RAIKA, 1)) {
                        DeityManager.consumePower(Deity.RAIKA, 1, player);
                        EntityLightningBolt bolt = new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, false);
                        player.world.spawnEntity(bolt);
                    }
                }
            }

            if(df.hasBlessing(BlessingType.MELINIAS_WATER_CURSE)) {
                if(player.isInWater()) {
                    if(DeityManager.hasPower(Deity.MELINIAS, 1)) {
                        DeityManager.consumePower(Deity.MELINIAS, 1, player);
                        player.addVelocity(0, -1, 0);
                        player.velocityChanged = true;
                    }
                }
            }

            if(df.hasBlessing(BlessingType.KASAI_NETHER_CURSE)) {
                if(player.dimension == -1) {
                    if(DeityManager.hasPower(Deity.KASAI, 1)) {
                        DeityManager.consumePower(Deity.KASAI, 1, player);
                        player.setHealth(player.getHealth() - 1);
                    }
                }
            }

            if(df.hasBlessing(BlessingType.ASANA_EARTH_POISON)) {
                if(isStone(player.world.getBlockState(player.getPosition().down()))) {
                    if(DeityManager.hasPower(Deity.ASANA, 1)) {
                        DeityManager.consumePower(Deity.ASANA, 1, player);
                        player.setHealth(player.getHealth() - 1);
                    }
                }
            }

            if(df.hasBlessing(BlessingType.LILASIA_SHADOW_HEALING) || DeityManager.isDeity(Deity.LILASIA, player)) {
                if(player.world.getLight(player.getPosition()) == 0 && !player.world.canSeeSky(player.getPosition())) {
                    if(DeityManager.hasPower(Deity.LILASIA, 1)) {
                        DeityManager.consumePower(Deity.LILASIA, 1, player);
                        player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth()));
                    }
                }
            }

            if(df.hasBlessing(BlessingType.LILASIA_SHADOW_CURSE)) {
                if(player.world.getLight(player.getPosition()) < 8) {
                    if(DeityManager.hasPower(Deity.LILASIA, 1)) {
                        DeityManager.consumePower(Deity.LILASIA, 1, player);
                        player.setHealth(player.getHealth() - 1);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void handleWaterJet(EntityPlayer player, IProfile profile) {
        IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
        if(df.hasBlessing(BlessingType.MELINIAS_WATER_JET) || DeityManager.isDeity(Deity.MELINIAS, player)) {
            if(player.isInWater() && player.isSprinting()) {
                if(DeityManager.hasPower(Deity.MELINIAS, 1)) {
                    if(System.currentTimeMillis() % 1000 < 50) DeityManager.consumePower(Deity.MELINIAS, 1, player);
                    Vec3d vec = player.getLookVec().scale(0.2);
                    player.addVelocity(vec.x, vec.y, vec.z);
                }
            }
        }
    }

    private static boolean isStone(IBlockState blockState) {
        return blockState.getMaterial().equals(Material.ROCK);
    }

    public static void onEntityTarget(LivingSetAttackTargetEvent event) {
        EntityLivingBase e = event.getEntityLiving();
        EntityLivingBase t = event.getTarget();

        if(t instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) t;
            IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);

            if(MythriaUtil.isLilasiaMob(e)) {
                if(df.hasBlessing(BlessingType.LILASIA_NO_MOBS) || DeityManager.isDeity(Deity.LILASIA, player)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isFireMob(e)) {
                if(df.hasBlessing(BlessingType.KASAI_NO_MOBS) || DeityManager.isDeity(Deity.KASAI, player)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isWaterMob(e)) {
                if(df.hasBlessing(BlessingType.MELINIAS_NO_MOBS) || DeityManager.isDeity(Deity.MELINIAS, player)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isEarthMob(e)) {
                if(df.hasBlessing(BlessingType.ASANA_NO_MOBS) || DeityManager.isDeity(Deity.ASANA, player)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isNatureMob(e)) {
                if(df.hasBlessing(BlessingType.SELINA_NO_NATURE_MOBS) || DeityManager.isDeity(Deity.SELINA, player)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }
        }
    }

    public static void onPlayerAttackEntity(IProfile profile, LivingHurtEvent event, EntityPlayer player) {
        EntityLivingBase target = event.getEntityLiving();
        IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
        if(df.hasBlessing(BlessingType.RAIKA_SMITE) || DeityManager.isDeity(Deity.RAIKA, player)) {
            if(DeityManager.hasPower(Deity.RAIKA, 1)) {
                DeityManager.consumePower(Deity.RAIKA, 1, player);
                EntityLightningBolt bolt = new EntityLightningBolt(player.world, target.posX, target.posY, target.posZ, false);
                player.world.spawnEntity(bolt);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientTick() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if(player == null) return;
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        handleElytra(player, profile);
        handleWaterJet(player, profile);
        handleLavaJet(player, profile);
    }

    private static void handleLavaJet(EntityPlayer player, IProfile profile) {
        IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
        if(df.hasBlessing(BlessingType.KASAI_LAVA_JET) || DeityManager.isDeity(Deity.KASAI, player)) {
            if(player.isInLava() && player.isSprinting()) {
                if(DeityManager.hasPower(Deity.KASAI, 1)) {
                    if (System.currentTimeMillis() % 1000 < 50) DeityManager.consumePower(Deity.KASAI, 1, player);
                    Vec3d vec = player.getLookVec().scale(0.2);
                    player.addVelocity(vec.x, vec.y, vec.z);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void handleElytra(EntityPlayer player, IProfile profile) {
        if(player.world.getWorldTime() % 20 != 0) return;
        IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
        if(df.hasBlessing(BlessingType.ELIANA_FLIGHT) || DeityManager.isDeity(Deity.ELIANA, player)) {
            if(player.isElytraFlying()) {
                if(player.getFoodStats().getFoodLevel() > 6) {
                    if(player.posY < 345) {
                        Vec3d vel = new Vec3d(player.motionX, player.motionY, player.motionZ);
                        if(DeityManager.hasPower(Deity.ELIANA, 1)) {
                            DeityManager.consumePower(Deity.ELIANA, 1, player);
                            if (!player.isSneaking()) {
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
    }

    public static void onPunchBlock(PlayerInteractEvent.LeftClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        if(!player.isSneaking()) return;
        if(CooldownManager.hasCooldown(Cooldown.CooldownType.EARTH_CRUMPLE, player)) return;
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        IDeityFavor df = player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
        if(df.hasBlessing(BlessingType.ASANA_EARTH_CRUMPLE) || DeityManager.isDeity(Deity.ASANA, player)) {
            BlockPos pos = event.getPos();
            IBlockState state = player.world.getBlockState(pos);
            if(state.getMaterial().equals(Material.ROCK) || state.getMaterial().equals(Material.GROUND) ||
                    state.getMaterial().equals(Material.SAND)) {
                if(DeityManager.hasPower(Deity.ASANA, 1)) {
                    DeityManager.consumePower(Deity.ASANA, 1, player);
                    player.world.destroyBlock(pos, true);
                    CooldownManager.addCooldown(player, Cooldown.CooldownType.EARTH_CRUMPLE, 1000);
                }
            }
        }
    }
}
