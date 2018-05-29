package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Capability.Metal.IMetal;
import me.Jonathon594.Mythria.Capability.Metal.MetalProvider;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Interface.IWorkable;
import me.Jonathon594.Mythria.Items.ItemToolHead;
import me.Jonathon594.Mythria.Items.MythriaItemHammer;
import me.Jonathon594.Mythria.Managers.SmithingManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityAnvil extends TileEntity {
    private int tier;
    protected final ItemStackHandler inventory = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            if(slot == 0) {
                updateCraftingOptions();
            }
            super.onContentsChanged(slot);
        }
    };

    //Temporary

    public TileEntityAnvil(int tier) {
        this.tier = tier;
        inventory.setSize(23);
    }

    public TileEntityAnvil() {
        this(0);
    }

    private void updateCraftingOptions() {
        reset();
        ItemStack is = inventory.getStackInSlot(0);
        if(is.isEmpty()) {
            inventory.setStackInSlot(1, ItemStack.EMPTY);
            return;
        }
        Item i = is.getItem();
        if(!(i instanceof  IWorkable)) return;
        ArrayList<SmithingManager.SmithingRecipe> recipes = SmithingManager.getRecipeFor((IWorkable) i);
        int index = 0;
        for(SmithingManager.SmithingRecipe recipe : recipes) {
            if(index >= 9) break;
            ItemStack resultItem = new ItemStack(recipe.getResultItem(), recipe.getCount());
            inventory.setStackInSlot(index + 7, resultItem);
            index++;
        }
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound comp = new NBTTagCompound();
        return this.writeToNBT(comp);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getState(), getState(), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
    }

    private void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getState(), getState(), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tier = compound.getInteger("tier");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tier", tier);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState,
                                 final IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
            return true;
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
            return (T) inventory;
        return super.getCapability(capability, facing);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public void setResultItem(int selectedSlot) {
        if(selectedSlot == -1) return;
        ItemStack is = inventory.getStackInSlot(selectedSlot+7);
        if(is.isEmpty()) return;
        Item i = is.getItem();
        ItemStack result = new ItemStack(i, 1);
        inventory.setStackInSlot(1, result);
        updateExtraRequirements();
        sendUpdates();
    }

    private void updateExtraRequirements() {
        SmithingManager.SmithingRecipe recipe = getCurrentRecipe();
        Item[] extra = recipe.getExtraIngredients();
        int index = 0;
        for(Item i : extra) {
            if(index > extra.length-1) break;
            if(i == null) break;

            inventory.setStackInSlot(index + 19, new ItemStack(i,1));

            if(index == 2) break;
            index++;
        }
    }

    public void buttonPressed(int index, boolean server, EntityPlayerMP player) {
        if(!hasHammer()) return;

        Item req1 = inventory.getStackInSlot(19).isEmpty() ? null : inventory.getStackInSlot(19).getItem();
        Item req2 = inventory.getStackInSlot(20).isEmpty() ? null : inventory.getStackInSlot(20).getItem();
        Item req3 = inventory.getStackInSlot(21).isEmpty() ? null : inventory.getStackInSlot(21).getItem();

        Item put1 = inventory.getStackInSlot(16).isEmpty() ? null : inventory.getStackInSlot(16).getItem();
        Item put2 = inventory.getStackInSlot(17).isEmpty() ? null : inventory.getStackInSlot(17).getItem();
        Item put3 = inventory.getStackInSlot(18).isEmpty() ? null : inventory.getStackInSlot(18).getItem();

        if(req1 != null && !req1.equals(put1)) {
            return;
        }
        if(req2 != null && !req2.equals(put2)) {
            return;
        }
        if(req3 != null && !req3.equals(put3)) {
            return;
        }

        if(SmithingManager.EnumHitType.values().length-1 < index) {
            if (server) {
                ItemStack is1 = inventory.getStackInSlot(2);
                ItemStack is2 = inventory.getStackInSlot(3);
                if(is1.isEmpty() || is2.isEmpty()) return;
                if(!is1.getItem().equals(is2.getItem())) return;
                IWorkable workable = (IWorkable) is1.getItem();
                if(!SmithingManager.canSmithMetal(workable.getMetalType(), tier)) return;
                SmithingManager.WeldingRecipe recipe = SmithingManager.getWeldingRecipeForInput(workable);
                if(recipe == null) return;
                ItemStack result = new ItemStack(recipe.getResultItem(), 1);
                damageHammer(12);
                inventory.setStackInSlot(2, ItemStack.EMPTY);
                inventory.setStackInSlot(3, ItemStack.EMPTY);
                inventory.setStackInSlot(4, result);
                addExperienceToAllNearby(10);
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1f, 1f);
            }
        } else {
            if(getCurrentRecipe() == null) return;
            SmithingManager.EnumHitType hitType = SmithingManager.EnumHitType.values()[index];
            ItemStack is = inventory.getStackInSlot(0);
            IMetal metal = is.getCapability(MetalProvider.METAL_CAP, null);
            if (metal == null) return;
            if(!SmithingManager.canSmithMetal(((IWorkable) is.getItem()).getMetalType(), tier)) return;
            metal.hit(hitType);

            if (server) {
                damageHammer(1);
                updateMaterial(hitType, metal, player);
                checkResult(metal, player);

                addExperienceToAllNearby(1);
            }
        }
    }

    private void addExperienceToAllNearby(int v) {
        for(Entity e : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-5,-5,-5), pos.add(5,5,5)))) {
            if(e instanceof EntityPlayer) {
                IProfile profile = e.getCapability(ProfileProvider.PROFILE_CAP, null);
                if(profile.hasFlag(AttributeFlag.BASIC_SMITHING)) profile.addSkillExperience(MythicSkills.SMITHING, v, (EntityPlayerMP) e);
            }
        }
    }

    private void updateMaterial(SmithingManager.EnumHitType hitType, IMetal metal, EntityPlayerMP player) {
        double movement = 0;
        boolean green = false;
        switch (hitType) {
            case SOFT_HIT:
                movement = -0.02;
                break;
            case MEDIUM_HIT:
                movement = -0.05;
                break;
            case HEAVY_HIT:
                movement = -0.08;
                break;
            case DRAW:
                movement = -0.1;
                break;
            case PUNCH:
                movement = 0.02;
                green = true;
                break;
            case BEND:
                movement = 0.05;
                green = true;
                break;
            case UPSET:
                movement = 0.08;
                green = true;
                break;
            case SHRINK:
                movement = 0.1;
                green = true;
                break;
        }

        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        int smithing = profile.getSkillLevel(MythicSkills.SMITHING);
        double prop = MathHelper.clamp((double) smithing / 100.0, 0, 1);
        double stability = 1-prop;
        movement += stability * (movement * (Math.random() - 0.5));

        int difficulty = getDifficultyForCurrentRecipe();
        double dev = SmithingManager.getMaxDeviation(difficulty);
        if(green) {
            metal.setSmithingProgress1(metal.getSmithingProgress1()+movement);
        } else {
            metal.setSmithingProgress2(metal.getSmithingProgress2()+movement);
        }

        sendUpdates();
        if(metal.getSmithingProgress1() >= metal.getSmithingProgress2() + dev || metal.getSmithingProgress2() <= metal.getSmithingProgress1() - dev) {
            //Scrap
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1f, 1f);
        }
    }

    public IMetal getMetal() {
        ItemStack is = inventory.getStackInSlot(0);
        IMetal metal = is.getCapability(MetalProvider.METAL_CAP, null);
        if(metal == null) return null;
        return metal;
    }

    private boolean hasHammer() {
        return !inventory.getStackInSlot(5).isEmpty() && inventory.getStackInSlot(5).getItem() instanceof MythriaItemHammer;
    }

    private void damageHammer(int amount) {
        ItemStack hammer = inventory.getStackInSlot(5);
        if(hammer.isEmpty()) return;
        hammer.setItemDamage(hammer.getItemDamage() + amount);
        if(hammer.getItemDamage() > hammer.getItem().getMaxDamage()) inventory.setStackInSlot(5, ItemStack.EMPTY);
    }

    private void checkResult(IMetal metal, EntityPlayerMP player) {
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1f, 1f);
        int difficulty = getDifficultyForCurrentRecipe();
        SmithingManager.EnumHitType[] hits = getFinalHitsForCurrentRecipe();
        double dev = SmithingManager.getMaxDeviation(difficulty);
        double off = Math.abs(metal.getSmithingProgress2() - metal.getSmithingProgress1());
        if(off > dev) return;
        for(int i = 0; i < 3; i++) {
            SmithingManager.EnumHitType hit = metal.getSmithingHits()[i];
            if(hit == null) return;
            if(hits == null) return;
            if(hit != hits[i]) return;
        }

        double mean = (metal.getSmithingProgress1() + metal.getSmithingProgress2()) / 2;
        if(mean < 0.25 || mean > 0.75) {
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1f, 1f);
        }

        double quality = Math.abs(MathHelper.clamp((mean - 0.5), -0.25, 0.25) * 4);

        //Success
        ItemStack result = new ItemStack(getCurrentRecipe().getResultItem(), getCurrentRecipe().getCount());
        int dura = (int) (result.getMaxDamage() * quality);
        if(result.getMaxDamage() > 0)result.setItemDamage(dura);
        if(result.getCount() > 1) {
            int count = (int)Math.ceil((double)result.getCount() * (1-quality));
            result.setCount(count);
        }
        IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        String name = profile.getFirstName() + " " + profile.getMiddleName() + " " + profile.getLastName();
        ArrayList<String> lines = new ArrayList<>();
        lines.add(MythriaConst.CONT_COLOR + "Forged by " + name);
        if(result.getItem() instanceof ItemToolHead ||
                result.getItem() instanceof ItemArmor)
            MythriaUtil.addLoreToItemStack(result, lines);
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        inventory.setStackInSlot(6, result);
        inventory.setStackInSlot(16, ItemStack.EMPTY);
        inventory.setStackInSlot(17, ItemStack.EMPTY);
        inventory.setStackInSlot(18, ItemStack.EMPTY);
        addExperienceToAllNearby((int)((100 * difficulty) * (1-quality)));
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1f, 1f);
    }

    public SmithingManager.SmithingRecipe getCurrentRecipe() {
        ItemStack is = inventory.getStackInSlot(1);
        if(is.isEmpty()) return null;
        SmithingManager.SmithingRecipe recipe = SmithingManager.getRecipe(is.getItem());
        if(recipe == null);
        return recipe;
    }

    public int getDifficultyForCurrentRecipe() {
        SmithingManager.SmithingRecipe recipe = getCurrentRecipe();
        if(recipe == null) return 0;
        return SmithingManager.getDifficultyForMetal(recipe.getMetalType());
    }

    public SmithingManager.EnumHitType[] getFinalHitsForCurrentRecipe() {
        SmithingManager.SmithingRecipe recipe = getCurrentRecipe();
        if(recipe == null) return null;
        SmithingManager.EnumHitType[] finalHits = SmithingManager.getRequiredFinalHitsForMetalShape(recipe.getOutputShape());
        return finalHits;
    }

    public SmithingManager.EnumHitType[] getCurrentHits() {
        ItemStack is = inventory.getStackInSlot(0);
        if(is.isEmpty()) return new SmithingManager.EnumHitType[3];
        IMetal metal = is.getCapability(MetalProvider.METAL_CAP, null);
        return metal.getSmithingHits();
    }

    public void reset() {
        inventory.setStackInSlot(1, ItemStack.EMPTY);
        for(int i = 0; i < 9; i++) {
            inventory.setStackInSlot(7+i, ItemStack.EMPTY);
        }

        for(int i = 0; i < 3; i++) {
            inventory.setStackInSlot(19+i, ItemStack.EMPTY);
        }
    }
}
