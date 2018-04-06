package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Entity.EntityNPCPlayer;
import me.Jonathon594.Mythria.Items.ItemWand;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.AttackEntityPacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.stats.StatList;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

public class DualSwingManager {

    public static boolean canAttack = true;

    private static int timer = 0;
    private static int leftClickCounter;

    public static void attackEntityCustom(final EntityPlayerMP player, final Entity targetEntity, final EnumHand hand,
                                          final int bonusDamage, final boolean reset) {
        if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget(player, targetEntity))
            return;
        if (targetEntity.canBeAttackedWithItem())
            if (!targetEntity.hitByEntity(player)) {
                float f = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                float f1;

                final ItemStack itemStack = hand == EnumHand.MAIN_HAND ? player.getHeldItemMainhand()
                        : player.getHeldItemOffhand();
                if (targetEntity instanceof EntityLivingBase)
                    f1 = EnchantmentHelper.getModifierForCreature(itemStack,
                            ((EntityLivingBase) targetEntity).getCreatureAttribute());
                else
                    f1 = EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);

                float f2 = player.getCooledAttackStrength(0.5F);
                if (reset)
                    f2 = 1.0f;
                f = f * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;
                if (!reset)
                    player.resetCooldown();

                if (f > 0.0F || f1 > 0.0F) {
                    final boolean flag = f2 > 0.9F;
                    boolean flag1 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockbackModifier(player);

                    if (player.isSprinting() && flag) {
                        player.world.playSound(null, player.posX, player.posY, player.posZ,
                                SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, player.getSoundCategory(), 1.0F, 1.0F);
                        ++i;
                        flag1 = true;
                    }

                    boolean flag2 = flag && player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder()
                            && !player.isInWater() && !player.isPotionActive(MobEffects.BLINDNESS) && !player.isRiding()
                            && targetEntity instanceof EntityLivingBase;
                    flag2 = flag2 && !player.isSprinting();

                    final net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks
                            .getCriticalHit(player, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
                    flag2 = hitResult != null;
                    if (flag2)
                        f *= hitResult.getDamageModifier();

                    f = f + f1;
                    boolean flag3 = false;
                    final double d0 = player.distanceWalkedModified - player.prevDistanceWalkedModified;

                    if (flag && !flag2 && !flag1 && player.onGround && d0 < player.getAIMoveSpeed()) {
                        final ItemStack itemstack = player.getHeldItem(hand);

                        if (itemstack.getItem() instanceof ItemSword)
                            flag3 = true;
                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    final int j = EnchantmentHelper.getFireAspectModifier(player);

                    if (targetEntity instanceof EntityLivingBase) {
                        f4 = ((EntityLivingBase) targetEntity).getHealth();

                        if (j > 0 && !targetEntity.isBurning()) {
                            flag4 = true;
                            targetEntity.setFire(1);
                        }
                    }

                    final double d1 = targetEntity.motionX;
                    final double d2 = targetEntity.motionY;
                    final double d3 = targetEntity.motionZ;
                    final EntityDamageSource ds = hand.equals(EnumHand.MAIN_HAND)
                            ? new EntityDamageSource("player", player)
                            : new EntityDamageSource("playeroffhand", player);
                    final boolean flag5 = targetEntity.attackEntityFrom(ds, f + bonusDamage);

                    if (flag5) {
                        if (i > 0) {
                            if (targetEntity instanceof EntityLivingBase)
                                ((EntityLivingBase) targetEntity).knockBack(player, i * 0.5F,
                                        MathHelper.sin(player.rotationYaw * 0.017453292F),
                                        -MathHelper.cos(player.rotationYaw * 0.017453292F));
                            else
                                targetEntity.addVelocity(-MathHelper.sin(player.rotationYaw * 0.017453292F) * i * 0.5F,
                                        0.1D, MathHelper.cos(player.rotationYaw * 0.017453292F) * i * 0.5F);

                            player.motionX *= 0.6D;
                            player.motionZ *= 0.6D;
                            player.setSprinting(false);
                        }

                        if (flag3) {
                            final float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * f;

                            for (final EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(
                                    EntityLivingBase.class,
                                    targetEntity.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D)))
                                if (entitylivingbase != player && entitylivingbase != targetEntity
                                        && !player.isOnSameTeam(entitylivingbase)
                                        && player.getDistanceSq(entitylivingbase) < 9.0D) {
                                    entitylivingbase.knockBack(player, 0.4F,
                                            MathHelper.sin(player.rotationYaw * 0.017453292F),
                                            -MathHelper.cos(player.rotationYaw * 0.017453292F));
                                    entitylivingbase.attackEntityFrom(ds, f3);
                                }

                            player.world.playSound(null, player.posX, player.posY, player.posZ,
                                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                            player.spawnSweepParticles();
                        }

                        if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
                            ((EntityPlayerMP) targetEntity).connection
                                    .sendPacket(new SPacketEntityVelocity(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.motionX = d1;
                            targetEntity.motionY = d2;
                            targetEntity.motionZ = d3;
                        }

                        if (flag2) {
                            player.world.playSound(null, player.posX, player.posY, player.posZ,
                                    SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
                            player.onCriticalHit(targetEntity);
                        }

                        if (!flag2 && !flag3)
                            if (flag)
                                player.world.playSound(null, player.posX, player.posY, player.posZ,
                                        SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, player.getSoundCategory(), 1.0F, 1.0F);
                            else
                                player.world.playSound(null, player.posX, player.posY, player.posZ,
                                        SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, player.getSoundCategory(), 1.0F, 1.0F);

                        if (f1 > 0.0F)
                            player.onEnchantmentCritical(targetEntity);

                        player.setLastAttackedEntity(targetEntity);

                        if (targetEntity instanceof EntityLivingBase)
                            EnchantmentHelper.applyThornEnchantments((EntityLivingBase) targetEntity, player);

                        EnchantmentHelper.applyArthropodEnchantments(player, targetEntity);
                        final ItemStack itemstack1 = itemStack;
                        Entity entity = targetEntity;

                        if (targetEntity instanceof MultiPartEntityPart) {
                            final IEntityMultiPart ientitymultipart = ((MultiPartEntityPart) targetEntity).parent;

                            if (ientitymultipart instanceof EntityLivingBase)
                                entity = (EntityLivingBase) ientitymultipart;
                        }

                        if (!itemstack1.isEmpty() && entity instanceof EntityLivingBase) {
                            final ItemStack beforeHitCopy = itemstack1.copy();
                            itemstack1.hitEntity((EntityLivingBase) entity, player);

                            if (itemstack1.isEmpty()) {
                                net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, beforeHitCopy,
                                        hand);
                                player.setHeldItem(hand, ItemStack.EMPTY);
                            }
                        }

                        if (targetEntity instanceof EntityLivingBase) {
                            final float f5 = f4 - ((EntityLivingBase) targetEntity).getHealth();
                            player.addStat(StatList.DAMAGE_DEALT, Math.round(f5 * 10.0F));

                            if (j > 0)
                                targetEntity.setFire(j * 4);

                            if (player.world instanceof WorldServer && f5 > 2.0F) {
                                final int k = (int) (f5 * 0.5D);
                                ((WorldServer) player.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR,
                                        targetEntity.posX, targetEntity.posY + targetEntity.height * 0.5F,
                                        targetEntity.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }

                        player.addExhaustion(0.1F);
                    } else {
                        player.world.playSound(null, player.posX, player.posY, player.posZ,
                                SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory(), 1.0F, 1.0F);

                        if (flag4)
                            targetEntity.extinguish();
                    }

                    if (reset)
                        targetEntity.hurtResistantTime = 0;
                }
            }
    }

    private static int tick = 0;
    public static void onClientTick() {
//        if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())
//            onMouseRight();

        final Minecraft mc = Minecraft.getMinecraft();
        if(mc.objectMouseOver == null) return;
        if(!mc.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
            ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), 2, 41);
        }
        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown())
            clickMouse();

        if (timer > 0)
            timer--;

        if(leftClickCounter > 0)
            leftClickCounter--;
    }

    private static EnumHand lastHand = EnumHand.MAIN_HAND;

    public static void clickMouse()
    {
        final Minecraft mc = Minecraft.getMinecraft();
        if (leftClickCounter <= 0)
        {
            if(mc.player.isActiveItemStackBlocking()) {
                return;
            }
            if(mc.player.getCooledAttackStrength(0) != 1.0) return;
            mc.player.resetCooldown();
            EnumHand hand = lastHand.equals(EnumHand.MAIN_HAND) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            Item o = mc.player.getHeldItem(EnumHand.OFF_HAND).getItem();
            Item m = mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem();
            if((!(o instanceof ItemSword) && !(o instanceof ItemTool))
                    && (m instanceof ItemSword || m instanceof ItemTool || m instanceof ItemWand)) {
                hand = EnumHand.MAIN_HAND;
            }
            if((!(m instanceof ItemSword) && !(m instanceof ItemTool))
                    && (o instanceof ItemSword || o instanceof ItemTool || o instanceof ItemWand)) {
                hand = EnumHand.OFF_HAND;
            }
            lastHand = hand;
            System.out.println(hand);
            if (mc.objectMouseOver == null)
            {
                if (mc.playerController.isNotCreative())
                {
                }
            }
            else if (!mc.player.isRowingBoat())
            {
                switch (mc.objectMouseOver.typeOfHit)
                {
                    case ENTITY:
                        AttackEntity(mc.player, mc.objectMouseOver.entityHit, hand);
                        break;
                }

                mc.player.swingArm(hand);
            }
        }
    }

    public static void onMouseRight() {
        if (timer != 0)
            return;
        if (!canAttack)
            return;
        final Minecraft mc = Minecraft.getMinecraft();
        if (!mc.player.world.isRemote)
            return;
        if (!canAttackWith(mc.player.getHeldItemOffhand()) || !canAttackWith(mc.player.getHeldItemMainhand()))
            return;

        if (!mc.player.isRowingBoat()) {
            switch (mc.objectMouseOver.typeOfHit) {
                case ENTITY:
                    final Entity e = mc.objectMouseOver.entityHit;
                    if (e instanceof EntityHorse || e instanceof EntityWolf || e instanceof EntitySheep
                            || e instanceof EntityCow || e instanceof EntityPig || e instanceof EntityChicken
                            || e instanceof EntityRabbit || e instanceof EntityOcelot || e instanceof EntityNPCPlayer)
                        return;
                    AttackEntity(mc.player, mc.objectMouseOver.entityHit, EnumHand.OFF_HAND);
                    timer = 10;
                    break;
                case BLOCK:
                    break;
                case MISS:
                    mc.player.resetCooldown();
                    timer = 10;
                    break;
            }

            mc.player.swingArm(EnumHand.OFF_HAND);
        }
    }

    private static boolean canAttackWith(final ItemStack heldItemOffhand) {
        return heldItemOffhand.getItemUseAction().equals(EnumAction.NONE);

    }

    @SideOnly(Side.CLIENT)
    private static void AttackEntity(final EntityPlayerSP player, final Entity entityHit, final EnumHand hand) {
        MythriaPacketHandler.INSTANCE
                .sendToServer(new AttackEntityPacketCustom(entityHit.getEntityId(), 0, false, hand.ordinal()));
    }

}
