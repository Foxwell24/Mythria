package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.BlockOre;
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

        if(ds.equals(DamageSource.DROWN)) {
            if(player.isInWater() && profile.hasFlag(AttributeFlag.MELINIAS_WATER_BREATHING)) event.setCanceled(true);
            else if(profile.hasFlag(AttributeFlag.ELIANA_BREATHING)) event.setCanceled(true);
        }
        if(ds.equals(DamageSource.WITHER)) {
            if(profile.hasFlag(AttributeFlag.FELIXIA_NO_WITHER)) event.setCanceled(true);
        }
        if(ds.equals(DamageSource.FALL)) {
            if(profile.hasFlag(AttributeFlag.ELIANA_NO_FALL)) event.setCanceled(true);
        }
        if(ds.equals(DamageSource.LIGHTNING_BOLT)) {
            if(profile.hasFlag(AttributeFlag.RAIKA_SMITE)) event.setCanceled(true);
        }
        if(ds.equals(DamageSource.LAVA) || ds.equals(DamageSource.IN_FIRE) || ds.equals(DamageSource.ON_FIRE) || ds.equals(DamageSource.HOT_FLOOR)) {
            if(profile.hasFlag(AttributeFlag.KASAI_NO_FIRE)) event.setCanceled(true);
        }
        if(ds.damageType.contains("explosion")) {
            if(profile.hasFlag(AttributeFlag.ASANA_NO_EXLODE)) event.setCanceled(true);
        }

        if(ds.damageType.equalsIgnoreCase("player") || ds.damageType.equalsIgnoreCase("playeroffhand")) {
            if(profile.hasFlag(AttributeFlag.RAIKA_ELECTROCUTE)) {
                EntityPlayer e = (EntityPlayer) ds.getTrueSource();
                DamageSource d = DamageSource.causeThornsDamage(player);
                e.attackEntityFrom(d, 1);
            }
        }
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        long tick = player.world.getWorldTime();

        if(tick % 20 == 0) { //Every Second
            IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);

            if(profile.hasFlag(AttributeFlag.FELIXIA_HEALING)) {
                if(player.world.getLight(player.getPosition()) > 13 && player.world.canSeeSky(player.getPosition())) {
                    player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth()));
                }
            }

            if(profile.hasFlag(AttributeFlag.FELIXIA_BURN)) {
                if(player.world.getLight(player.getPosition()) > 13 && player.world.canSeeSky(player.getPosition())) {
                    player.setFire(5);
                }
            }

            if(profile.hasFlag(AttributeFlag.ELIANA_SUFFOCATE)) {
                player.setAir(2);
            }

            if(profile.hasFlag(AttributeFlag.RAIKA_WRATH)) {
                if(player.world.canSeeSky(player.getPosition())) {
                    EntityLightningBolt bolt = new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, false);
                    player.world.spawnEntity(bolt);
                }
            }

            if(profile.hasFlag(AttributeFlag.MELINIAS_WATER_CURSE)) {
                if(player.isInWater()) {
                    player.setHealth(player.getHealth()-1);
                }
            }

            if(profile.hasFlag(AttributeFlag.KASAI_NETHER_CURSE)) {
                if(player.dimension == -1) {
                    player.setHealth(player.getHealth()-1);
                }
            }

            if(profile.hasFlag(AttributeFlag.MELINIAS_WATER_CURSE)) {
                if(isStone(player.world.getBlockState(player.getPosition().down()))) {
                    player.setHealth(player.getHealth()-1);
                }
            }

            if(profile.hasFlag(AttributeFlag.LILASIA_SHADOW_HEALING)) {
                if(player.world.getLight(player.getPosition()) == 0 && !player.world.canSeeSky(player.getPosition())) {
                    player.setHealth(Math.min(player.getHealth() + 1, player.getMaxHealth()));
                }
            }

            if(profile.hasFlag(AttributeFlag.LILASIA_SHADOW_HEALING)) {
                if(player.world.getLight(player.getPosition()) < 8) {
                    player.setHealth(player.getHealth() - 1);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void handleWaterJet(EntityPlayer player, IProfile profile) {
        Deity d = DeityManager.getSelectedDeities().get(player.getEntityId());
        if(profile.hasFlag(AttributeFlag.MELINIAS_WATER_JET) || (d != null && d.equals(Deity.MELINIAS))) {
            if(player.isInWater() && player.isSprinting()) {
                Vec3d vec = player.getLookVec().scale(0.2);
                player.addVelocity(vec.x, vec.y, vec.z);
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
            IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);

            if(MythriaUtil.isLilasiaMob(e)) {
                if(profile.hasFlag(AttributeFlag.LILASIA_NO_MOBS)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isFireMob(e)) {
                if(profile.hasFlag(AttributeFlag.KASAI_NO_MOBS)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isWaterMob(e)) {
                if(profile.hasFlag(AttributeFlag.MELINIAS_NO_MOBS)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isEarthMob(e)) {
                if(profile.hasFlag(AttributeFlag.ASANA_NO_MOBS)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }

            if(MythriaUtil.isNatureMob(e)) {
                if(profile.hasFlag(AttributeFlag.SELINA_NO_NATURE_MOBS)) {
                    ((EntityLiving) e).setAttackTarget(null);
                }
            }
        }
    }

    public static void onPlayerAttackEntity(IProfile profile, LivingHurtEvent event, EntityPlayer player) {
        EntityLivingBase target = event.getEntityLiving();

        if(profile.hasFlag(AttributeFlag.RAIKA_SMITE)) {
            EntityLightningBolt bolt = new EntityLightningBolt(player.world, target.posX, target.posY, target.posZ, false);
            player.world.spawnEntity(bolt);
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
        Deity d = DeityManager.getSelectedDeities().get(player.getEntityId());
        if(profile.hasFlag(AttributeFlag.KASAI_LAVA_JET) || (d != null && d.equals(Deity.KASAI))) {
            if(player.isInLava() && player.isSprinting()) {
                Vec3d vec = player.getLookVec().scale(0.2);
                player.addVelocity(vec.x, vec.y, vec.z);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void handleElytra(EntityPlayer player, IProfile profile) {
        if(player.world.getWorldTime() % 20 != 0) return;
        Deity d = DeityManager.getSelectedDeities().get(player.getEntityId());
        if(profile.hasFlag(AttributeFlag.ELIANA_FLIGHT) || (d != null && d.equals(Deity.ELIANA))) {
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

    public static void onPunchBlock(PlayerInteractEvent.LeftClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);

        if(profile.hasFlag(AttributeFlag.ASANA_EARTH_CRUMPLE)) {
            BlockPos pos = event.getPos();
            IBlockState state = player.world.getBlockState(pos);
            if(state.getMaterial().equals(Material.ROCK) || state.getMaterial().equals(Material.GROUND) ||
                    state.getMaterial().equals(Material.SAND)) {
                player.world.destroyBlock(pos, true);
            }
        }
    }
}
