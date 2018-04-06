package me.Jonathon594.Mythria.Entity.Magic;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityEarthBlast extends EntityMagicBolt {

    public static DataParameter<String> BLOCK_NAME = EntityDataManager.createKey(EntityEarthBlast.class, DataSerializers.STRING);

    public EntityEarthBlast(World worldIn, EntityPlayerMP throwerIn, double x, double y, double z) {
        this(worldIn, x, y, z);
        thrower = throwerIn;
    }

    public EntityEarthBlast(final World worldIn, final double x, final double y, final double z) {
        this(worldIn);
        setPosition(x, y, z);
    }

    public EntityEarthBlast(final World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    protected SoundEvent getLoopSound() {
        return SoundEvents.BLOCK_SAND_BREAK;
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        dataManager.register(BLOCK_NAME, Blocks.STONE.getUnlocalizedName().substring(5));
    }

    public Block getBlock() {
        Block b = Block.getBlockFromName(dataManager.get(BLOCK_NAME));
        return b;
    }

    public void setBlock(Block b) {
        dataManager.set(BLOCK_NAME, b.getUnlocalizedName().substring(5));
    }

    @Override
    protected SoundEvent getSpawnSound() {
        return SoundEvents.ENTITY_GHAST_SHOOT;
    }

    @Override
    protected int getLifeTime() {
        return 240;
    }

    @Override
    protected int getDamage() {
        return 4;
    }

    @Override
    protected float getGravityWhenFalling() {
        return 0.08f;
    }

    @Override
    protected int getGravityTime() {
        return 40;
    }
}
