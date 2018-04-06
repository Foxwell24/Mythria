package me.Jonathon594.Mythria.Capability.Profile;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProfileProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IProfile.class)
    public static final Capability<IProfile> PROFILE_CAP = null;

    private final IProfile instance = PROFILE_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == PROFILE_CAP;
    }

    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        return capability == PROFILE_CAP ? PROFILE_CAP.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return PROFILE_CAP.getStorage().writeNBT(PROFILE_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        PROFILE_CAP.getStorage().readNBT(PROFILE_CAP, instance, null, nbt);
    }

}
