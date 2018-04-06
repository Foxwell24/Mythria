package me.Jonathon594.Mythria.Capability.NPC;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public interface INPC {

    void fromNBT(NBTTagCompound comp);

    UUID getProfileUUID();

    void setProfileUUID(UUID profileUUID);

    NBTTagCompound toNBT();
}
