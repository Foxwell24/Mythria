package me.Jonathon594.Mythria.Capability.Profile;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ProfileStorage implements IStorage<IProfile> {

    @Override
    public NBTBase writeNBT(final Capability<IProfile> capability, final IProfile instance, final EnumFacing side) {
        final NBTTagCompound comp = instance.toNBT();
        return comp;
    }

    @Override
    public void readNBT(final Capability<IProfile> capability, final IProfile instance, final EnumFacing side,
                        final NBTBase nbt) {
        final NBTTagCompound comp = (NBTTagCompound) nbt;
        instance.fromNBT(comp);
    }

}
