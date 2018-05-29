package me.Jonathon594.Mythria.Capability.Vessel;

import me.Jonathon594.Mythria.Capability.Food.IFood;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class VesselProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IVessel.class)
    public static final Capability<IVessel> VESSEL_CAP = null;
    private final IVessel instance = VESSEL_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == VESSEL_CAP;
    }

    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        return capability == VESSEL_CAP ? VESSEL_CAP.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return VESSEL_CAP.getStorage().writeNBT(VESSEL_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        VESSEL_CAP.getStorage().readNBT(VESSEL_CAP, instance, null, nbt);
    }

}
