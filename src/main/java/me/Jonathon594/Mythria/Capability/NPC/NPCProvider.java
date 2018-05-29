package me.Jonathon594.Mythria.Capability.NPC;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class NPCProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(INPC.class)
    public static final Capability<INPC> NPC_CAP = null;
    private final INPC instance = NPC_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == NPC_CAP;
    }

    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        return capability == NPC_CAP ? NPC_CAP.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return NPC_CAP.getStorage().writeNBT(NPC_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        NPC_CAP.getStorage().readNBT(NPC_CAP, instance, null, nbt);
    }

}
