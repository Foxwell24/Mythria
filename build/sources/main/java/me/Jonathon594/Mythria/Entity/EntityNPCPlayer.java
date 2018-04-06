package me.Jonathon594.Mythria.Entity;

import me.Jonathon594.Mythria.Entity.AI.EntityAIHurtByTargetNPC;
import me.Jonathon594.Mythria.Event.NPCDeathEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public abstract class EntityNPCPlayer extends EntityCreature {

    /**
     * Inventory of the player
     */
    // public InventoryPlayer inventory = new InventoryNPC(this);
    public EntityNPCPlayer(final World worldIn) {
        super(worldIn);
        enablePersistence();
    }

    public abstract ResourceLocation getNPCTexture();

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityZombie.class, 6.0F, 1.0D, 1.2D));
        tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntitySkeleton.class, 6.0F, 1.0D, 1.2D));
        tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityCreeper.class, 6.0F, 1.0D, 1.2D));
        tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityEnderman.class, 6.0F, 1.0D, 1.2D));
        tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        applyEntityAI();
    }

    protected void applyEntityAI() {
        targetTasks.addTask(0, new EntityAIHurtByTargetNPC(this, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    /**
     * Tests if this entity should pickup a weapon or an armor. Entity drops current
     * weapon or armor if the new one is better.
     */
    @Override
    protected void updateEquipmentIfNeeded(final EntityItem itemEntity) {
        final ItemStack itemstack = itemEntity.getItem();
        final EntityEquipmentSlot entityequipmentslot = getSlotForItemStack(itemstack);
        boolean flag = true;
        final ItemStack itemstack1 = getItemStackFromSlot(entityequipmentslot);

        if (!itemstack1.isEmpty())
            if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.HAND) {
                if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword))
                    flag = true;
                else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
                    final ItemSword itemsword = (ItemSword) itemstack.getItem();
                    final ItemSword itemsword1 = (ItemSword) itemstack1.getItem();

                    if (itemsword.getAttackDamage() == itemsword1.getAttackDamage())
                        flag = itemstack.getMetadata() > itemstack1.getMetadata()
                                || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                    else
                        flag = itemsword.getAttackDamage() > itemsword1.getAttackDamage();
                } else if (itemstack.getItem() instanceof ItemBow && itemstack1.getItem() instanceof ItemBow)
                    flag = itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                else
                    flag = false;
            } else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor))
                flag = true;
            else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor
                    && !EnchantmentHelper.hasBindingCurse(itemstack1)) {
                final ItemArmor itemarmor = (ItemArmor) itemstack.getItem();
                final ItemArmor itemarmor1 = (ItemArmor) itemstack1.getItem();

                if (itemarmor.damageReduceAmount == itemarmor1.damageReduceAmount)
                    flag = itemstack.getMetadata() > itemstack1.getMetadata()
                            || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                else
                    flag = itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount;
            } else
                flag = false;

        if (flag && canEquipItem(itemstack)) {
            double d0;

            switch (entityequipmentslot.getSlotType()) {
                case HAND:
                    d0 = inventoryHandsDropChances[entityequipmentslot.getIndex()];
                    break;
                case ARMOR:
                    d0 = inventoryArmorDropChances[entityequipmentslot.getIndex()];
                    break;
                default:
                    d0 = 0.0D;
            }

            if (!itemstack1.isEmpty() && rand.nextFloat() - 0.1F < d0)
                entityDropItem(itemstack1, 0.0F);

            setItemStackToSlot(entityequipmentslot, itemstack);

            switch (entityequipmentslot.getSlotType()) {
                case HAND:
                    inventoryHandsDropChances[entityequipmentslot.getIndex()] = 2.0F;
                    break;
                case ARMOR:
                    inventoryArmorDropChances[entityequipmentslot.getIndex()] = 2.0F;
            }

            onItemPickup(itemEntity, itemstack.getCount());
            itemEntity.setDead();
        }
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getItemStackFromSlot(final EntityEquipmentSlot slotIn) {
        // TODO Auto-generated method stub
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(final EntityEquipmentSlot slotIn, final ItemStack stack) {
        // TODO Auto-generated method stub

    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

    @Override
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);

        final NPCDeathEvent event = new NPCDeathEvent(this, cause);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Override
    public boolean attackEntityAsMob(final Entity entityIn) {
        attackTargetEntityWithCurrentItem(entityIn);
        return true;
    }

    public void attackTargetEntityWithCurrentItem(final Entity targetEntity) {
        if (targetEntity.canBeAttackedWithItem())
            if (!targetEntity.hitByEntity(this)) {
                float f = (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                float f1;

                if (targetEntity instanceof EntityLivingBase)
                    f1 = EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(),
                            ((EntityLivingBase) targetEntity).getCreatureAttribute());
                else
                    f1 = EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(),
                            EnumCreatureAttribute.UNDEFINED);

                final float f2 = 1;
                f = f * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;

                if (f > 0.0F || f1 > 0.0F) {
                    final boolean flag = f2 > 0.9F;
                    boolean flag1 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockbackModifier(this);

                    if (isSprinting() && flag) {
                        world.playSound(null, posX, posY, posZ,
                                SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, getSoundCategory(), 1.0F, 1.0F);
                        ++i;
                        flag1 = true;
                    }

                    boolean flag2 = flag && fallDistance > 0.0F && !onGround && !isOnLadder() && !isInWater()
                            && !isPotionActive(MobEffects.BLINDNESS) && !isRiding()
                            && targetEntity instanceof EntityLivingBase;
                    flag2 = flag2 && !isSprinting();

                    f = f + f1;
                    boolean flag3 = false;
                    final double d0 = distanceWalkedModified - prevDistanceWalkedModified;

                    if (flag && !flag2 && !flag1 && onGround && d0 < getAIMoveSpeed()) {
                        final ItemStack itemstack = getHeldItem(EnumHand.MAIN_HAND);

                        if (itemstack.getItem() instanceof ItemSword)
                            flag3 = true;
                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    final int j = EnchantmentHelper.getFireAspectModifier(this);

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
                    final boolean flag5 = targetEntity.attackEntityFrom(DamageSource.causeMobDamage(this), f);

                    if (flag5) {
                        if (i > 0) {
                            if (targetEntity instanceof EntityLivingBase)
                                ((EntityLivingBase) targetEntity).knockBack(this, i * 0.5F,
                                        MathHelper.sin(rotationYaw * 0.017453292F),
                                        -MathHelper.cos(rotationYaw * 0.017453292F));
                            else
                                targetEntity.addVelocity(-MathHelper.sin(rotationYaw * 0.017453292F) * i * 0.5F, 0.1D,
                                        MathHelper.cos(rotationYaw * 0.017453292F) * i * 0.5F);

                            motionX *= 0.6D;
                            motionZ *= 0.6D;
                            setSprinting(false);
                        }

                        if (flag3) {
                            final float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this) * f;

                            for (final EntityLivingBase entitylivingbase : world.getEntitiesWithinAABB(
                                    EntityLivingBase.class,
                                    targetEntity.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D)))
                                if (entitylivingbase != this && entitylivingbase != targetEntity
                                        && !isOnSameTeam(entitylivingbase)
                                        && this.getDistanceSq(entitylivingbase) < 9.0D) {
                                    entitylivingbase.knockBack(this, 0.4F, MathHelper.sin(rotationYaw * 0.017453292F),
                                            -MathHelper.cos(rotationYaw * 0.017453292F));
                                    entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this), f3);
                                }

                            world.playSound(null, posX, posY, posZ,
                                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, getSoundCategory(), 1.0F, 1.0F);
                            // this.spawnSweepParticles();
                        }

                        if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
                            ((EntityPlayerMP) targetEntity).connection
                                    .sendPacket(new SPacketEntityVelocity(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.motionX = d1;
                            targetEntity.motionY = d2;
                            targetEntity.motionZ = d3;
                        }

                        if (flag2)
                            world.playSound(null, posX, posY, posZ,
                                    SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, getSoundCategory(), 1.0F, 1.0F);
                        // this.onCriticalHit(targetEntity);

                        if (!flag2 && !flag3)
                            if (flag)
                                world.playSound(null, posX, posY, posZ,
                                        SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, getSoundCategory(), 1.0F, 1.0F);
                            else
                                world.playSound(null, posX, posY, posZ,
                                        SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, getSoundCategory(), 1.0F, 1.0F);

                        if (f1 > 0.0F) {
                            // this.onEnchantmentCritical(targetEntity);
                        }

                        setLastAttackedEntity(targetEntity);

                        if (targetEntity instanceof EntityLivingBase)
                            EnchantmentHelper.applyThornEnchantments((EntityLivingBase) targetEntity, this);

                        EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
                        final ItemStack itemstack1 = getHeldItemMainhand();
                        Entity entity = targetEntity;

                        if (targetEntity instanceof MultiPartEntityPart) {
                            final IEntityMultiPart ientitymultipart = ((MultiPartEntityPart) targetEntity).parent;

                            if (ientitymultipart instanceof EntityLivingBase)
                                entity = (EntityLivingBase) ientitymultipart;
                        }

                        if (!itemstack1.isEmpty() && entity instanceof EntityLivingBase) {
                            itemstack1.copy();

                            if (itemstack1.isEmpty())
                                setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                        }

                        if (targetEntity instanceof EntityLivingBase) {
                            final float f5 = f4 - ((EntityLivingBase) targetEntity).getHealth();

                            if (j > 0)
                                targetEntity.setFire(j * 4);

                            if (world instanceof WorldServer && f5 > 2.0F) {
                                final int k = (int) (f5 * 0.5D);
                                ((WorldServer) world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR,
                                        targetEntity.posX, targetEntity.posY + targetEntity.height * 0.5F,
                                        targetEntity.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }
                    } else {
                        world.playSound(null, posX, posY, posZ,
                                SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, getSoundCategory(), 1.0F, 1.0F);

                        if (flag4)
                            targetEntity.extinguish();
                    }
                }
            }
    }
}
