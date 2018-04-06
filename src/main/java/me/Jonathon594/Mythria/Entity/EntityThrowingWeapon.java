package me.Jonathon594.Mythria.Entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowingWeapon extends EntityThrowable {
    private static final DataParameter<ItemStack> itemData = EntityDataManager.createKey(EntityThrowingWeapon.class,
            DataSerializers.ITEM_STACK);
    private static final DataParameter<Float> damageData = EntityDataManager.createKey(EntityThrowingWeapon.class,
            DataSerializers.FLOAT);

    public EntityThrowingWeapon(final World worldIn, final EntityLivingBase throwerIn) {
        this(worldIn, throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ);
        thrower = throwerIn;
    }

    public EntityThrowingWeapon(final World worldIn, final double x, final double y, final double z) {
        this(worldIn);
        setPosition(x, y, z);
    }

    public EntityThrowingWeapon(final World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(damageData, 0.0f);
        dataManager.register(itemData, new ItemStack(Items.IRON_AXE));
    }

    @Override
    public void shoot(final Entity entityThrower, final float rotationPitchIn, final float rotationYawIn,
                      final float pitchOffset, final float velocity, final float inaccuracy) {
        final float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        final float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        final float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.shoot(f, f1, f2, velocity, inaccuracy);
        motionX += entityThrower.motionX;
        motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround)
            motionY += entityThrower.motionY;
    }

    @Override
    protected void onImpact(final RayTraceResult result) {
        if (!world.isRemote) {
            if (result.entityHit != null) {
                final int i = (int) (double) dataManager.get(damageData);

                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), i);
            }

            final EntityItem item = new EntityItem(world, posX, posY, posZ, dataManager.get(itemData));
            item.setPickupDelay(20);
            world.spawnEntity(item);
            world.setEntityState(this, (byte) 3);
            setDead();
        }
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        final NBTTagCompound item = new NBTTagCompound();
        dataManager.get(itemData).writeToNBT(item);
        compound.setString("item", item.toString());
        compound.setInteger("damage", (int) (double) dataManager.get(damageData));
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagCompound item = new NBTTagCompound();
        try {
            item = JsonToNBT.getTagFromJson(compound.getString("item"));
        } catch (final NBTException e) {
            e.printStackTrace();
        }

        dataManager.set(itemData, new ItemStack(item));
        dataManager.set(damageData, (float) compound.getInteger("damage"));
    }

    public ItemStack getItem() {
        return dataManager.get(itemData);
    }

    public void setItem(final ItemStack item) {
        dataManager.set(itemData, item);
    }

    public void setDamage(final int damage) {
        dataManager.set(damageData, (float) damage);
    }
}
