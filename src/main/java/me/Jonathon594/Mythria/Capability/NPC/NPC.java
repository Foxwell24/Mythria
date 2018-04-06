package me.Jonathon594.Mythria.Capability.NPC;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class NPC implements INPC {

    UUID profileUUID;

    @Override
    public void fromNBT(final NBTTagCompound comp) {
        if (comp == null)
            return;
        profileUUID = !comp.hasKey("ProfileUUID") ? null : UUID.fromString(comp.getString("ProfileUUID"));
    }

    @Override
    public UUID getProfileUUID() {
        return profileUUID;
    }

    @Override
    public void setProfileUUID(final UUID profileUUID) {
        this.profileUUID = profileUUID;
    }

    @Override
    public NBTTagCompound toNBT() {
        final NBTTagCompound comp = new NBTTagCompound();
        if (profileUUID != null)
            comp.setString("ProfileUUID", profileUUID.toString());
        return comp;
    }

}
