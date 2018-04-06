package me.Jonathon594.Mythria.Capability.Metal;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MetalProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IMetal.class)
    public static final Capability<IMetal> METAL_CAP = null;
    private final IMetal instance = METAL_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == METAL_CAP;
    }

    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        return capability == METAL_CAP ? METAL_CAP.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return METAL_CAP.getStorage().writeNBT(METAL_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        METAL_CAP.getStorage().readNBT(METAL_CAP, instance, null, nbt);
    }

}
