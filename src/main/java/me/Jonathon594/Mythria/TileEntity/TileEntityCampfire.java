package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.BlockCampfire;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Managers.FoodManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCampfire extends TileEntityCookingFireBasic implements ITickable {

    public TileEntityCampfire() {
        super(3, 5.0);
    }

    @Override
    public void update() {
        if (world.isRemote)
            return;
        if (lit) {
            ticksLeft--;
            if (ticksLeft <= 0) {
                world.setBlockToAir(pos);
                for (int i = 0; i < cookItems.getSlots(); i++) {
                    final ItemStack is = cookItems.getStackInSlot(i);
                    cookItems.setStackInSlot(i, ItemStack.EMPTY);
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), is);
                }
            }
            tick++;
            if (tick == 20) {
                tick = 0;
                FoodManager.Cook(cookItems, 5);
                for(Entity e : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-5,-5,-5), pos.add(5,5,5)))) {
                    if(e instanceof EntityPlayer) {
                        IProfile profile = e.getCapability(ProfileProvider.PROFILE_CAP, null);
                        if(profile.hasFlag(AttributeFlag.COOKING1))profile.addSkillExperience(MythicSkills.COOKING, 1, (EntityPlayerMP) e);
                    }
                }
            }
        } else if (friction > 1)
            friction -= 1;
    }

    @Override
    public void light() {
        setLit(true);
    }

    @Override
    public void addFriction() {
        this.friction += Math.random()*20;
        if(friction > 100) light();
    }

    @Override
    public boolean hasFuel() {
        return true;
    }

    public void setLit(boolean lit) {
        this.lit = lit;
        world.setBlockState(pos, MythriaBlocks.CAMPFIRE.getDefaultState().withProperty(BlockCampfire.BURNING, lit));
        sendUpdates();
    }
}
