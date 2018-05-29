package me.Jonathon594.Mythria.Capability.NPC;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class NPC implements INPC {

    private UUID profileUUID;

    @Override
    public int getEntityID() {
        return entityID;
    }

    @Override
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    private int entityID;

    @Override
    public void fromNBT(final NBTTagCompound comp) {
        if (comp == null)
            return;
        profileUUID = !comp.hasKey("ProfileUUID") ? null : UUID.fromString(comp.getString("ProfileUUID"));
        entityID = comp.getInteger("entityID");
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
        comp.setInteger("entityID", entityID);
        return comp;
    }

}
