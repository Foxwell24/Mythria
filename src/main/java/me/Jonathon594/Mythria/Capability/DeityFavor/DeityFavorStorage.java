package me.Jonathon594.Mythria.Capability.DeityFavor;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class DeityFavorStorage implements IStorage<IDeityFavor> {

    @Override
    public NBTBase writeNBT(final Capability<IDeityFavor> capability, final IDeityFavor instance, final EnumFacing side) {
        final NBTTagCompound comp = instance.toNBT();
        return comp;
    }

    @Override
    public void readNBT(final Capability<IDeityFavor> capability, final IDeityFavor instance, final EnumFacing side,
                        final NBTBase nbt) {
        final NBTTagCompound comp = (NBTTagCompound) nbt;
        instance.fromNBT(comp);
    }

}
