package me.Jonathon594.Mythria.Capability.NPC;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class NPCStorage implements IStorage<INPC> {
    @Override
    public NBTBase writeNBT(final Capability<INPC> capability, final INPC instance, final EnumFacing side) {
        final NBTTagCompound comp = instance.toNBT();
        return comp;
    }

    @Override
    public void readNBT(final Capability<INPC> capability, final INPC instance, final EnumFacing side,
                        final NBTBase nbt) {
        final NBTTagCompound comp = (NBTTagCompound) nbt;
        instance.fromNBT(comp);
    }
}
