package me.Jonathon594.Mythria.Capability.DeityFavor;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DeityFavorProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IDeityFavor.class)
    public static final Capability<IDeityFavor> DEITY_FAVOR_CAP = null;

    private final IDeityFavor instance = DEITY_FAVOR_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == DEITY_FAVOR_CAP;
    }

    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        return capability == DEITY_FAVOR_CAP ? DEITY_FAVOR_CAP.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DEITY_FAVOR_CAP.getStorage().writeNBT(DEITY_FAVOR_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        DEITY_FAVOR_CAP.getStorage().readNBT(DEITY_FAVOR_CAP, instance, null, nbt);
    }

}
