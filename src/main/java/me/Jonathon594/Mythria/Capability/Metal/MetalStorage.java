package me.Jonathon594.Mythria.Capability.Metal;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MetalStorage implements IStorage<IMetal> {
    @Override
    public NBTBase writeNBT(final Capability<IMetal> capability, final IMetal instance, final EnumFacing side) {
        final NBTTagCompound comp = instance.toNBT();
        return comp;
    }

    @Override
    public void readNBT(final Capability<IMetal> capability, final IMetal instance, final EnumFacing side,
                        final NBTBase nbt) {
        final NBTTagCompound comp = (NBTTagCompound) nbt;
        instance.fromNBT(comp);
    }
}
