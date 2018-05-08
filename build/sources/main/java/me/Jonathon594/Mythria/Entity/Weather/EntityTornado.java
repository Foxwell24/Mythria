package me.Jonathon594.Mythria.Entity.Weather;


import me.Jonathon594.Mythria.Enum.Season;
import me.Jonathon594.Mythria.Managers.SoundManager;
import me.Jonathon594.Mythria.Module.TornadoModule;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.DPacketSoundEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashMap;

public abstract class EntityTornado extends EntityStorm {
    protected float speed = 0.1f;

    public EntityTornado(World worldIn, float speed) {
        super(worldIn);
        this.speed = speed;
        this.rotationYaw = (float) (Math.random() * Math.PI * 2);
    }

    public static int getWidth(int tier) {
        return 20 + (tier * 6);
    }

    public static int getHeight(int tier) {
        return 82 + (tier * 4);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }

    public abstract int getTier();

    @Override
    public void onUpdate() {
        super.onUpdate();

        //Motion
        posX += Math.cos(rotationYaw) * speed;
        posZ += Math.sin(rotationYaw) * speed;

        for (BlockPos pos = new BlockPos(posX, 256, posZ); TornadoModule.isEdible(world.getBlockState(pos.down()).getBlock(), getTier())
                || world.getBlockState(pos.down()).getBlock().equals(Blocks.AIR); pos = pos.down()) {
            posY = pos.down(2).getY();
        }



        //Destruction
        if (world.isRemote) return;
        for (BlockPos pos : TornadoModule.getBlockShape(getTier())) {
            BlockPos pos1 = pos.add(posX, posY, posZ);
            Block b = world.getBlockState(pos1).getBlock();
            if (TornadoModule.isEdible(b, getTier())) {
                if(Math.random()<0.5) {
                    EntityFallingBlock efb = new EntityFallingBlock(world, pos1.getX()+0.5, pos1.getY()+0.5, pos1.getZ()+0.5, world.getBlockState(pos1));
                    efb.fallTime = 1;
                    world.spawnEntity(efb);
                }
                world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }
        }

        //Sound
        if(Math.random() < 0.1) {
            for (EntityPlayerMP player : world.getMinecraftServer().getPlayerList().getPlayers()) {
                if (player.dimension != dimension) continue;
                if (player.getPositionVector().distanceTo(getPositionVector()) > 1000) continue;
                double volume = player.getPositionVector().distanceTo(getPositionVector());
                SoundManager.playForPlayerOnly(player, SoundEvents.ITEM_ELYTRA_FLYING, (float) volume);
            }
        }

        //Throwing
        for (double h = 0; h < height; h++) {
            double r = (Math.pow(h / height, 2) + 0.04 + (getTier() * 0.02)) * getWidth(getTier());
            for (final Entity entity : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPosition().add(-r, 0 + h, -r), getPosition().add(r, 1 + h, r)))) {
                final double y = entity.posY;
                double factor;
                if (y >= posY && y < posY + h) {
                    factor = (y - posY) / h;
                    factor *= factor * 3;
                    final BlockPos testloc = getPosition();
                    if (testloc.distanceSq(entity.getPosition()) < Math.pow(Math.max(r * factor, 0.1), 3)) {
                        double x, z, vx, vz, mag;
                        double angle;
                        final double vy = 0.1;

                        x = entity.posX - posX;
                        z = entity.posZ - posZ;

                        angle = Math.atan2(z, x) - Math.PI / 2 + Math.PI/3;
                        vx = -Math.cos(angle)/ 10;
                        vz = -Math.sin(angle) / 10;


                        final Vec3d velocity = new Vec3d(vx,vy,vz);
                        velocity.scale(1);
                        entity.addVelocity(velocity.x, velocity.y, velocity.z);
                        entity.fallDistance = 0;
                        entity.velocityChanged = true;
                    }
                }
            }
        }

        //Changing Path
        if(Math.random() < 0.01) {
            final double rand = Math.random();
            double turn = 1;
            if (rand < 0.01) {
                turn = 110;
            } else if (rand < 0.02) {
                turn = 90;
            } else if (rand < 0.05) {
                turn = 60;
            } else if (rand < 0.10) {
                turn = 45;
            } else if (rand < 0.25) {
                turn = 30;
            } else if (rand < 0.50) {
                turn = 15;
            } else if (rand < 0.75) {
                turn = 10;
            } else if (rand < 0.90) {
                turn = 5;
            }
            if (Math.random() < 0.5) {
                turn = -turn;
            }
            turn *= Math.random();
            this.rotationYaw += Math.toRadians(turn);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }
}