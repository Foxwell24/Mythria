package me.Jonathon594.Mythria.Storage;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Entity.Weather.EntityEarthQuake;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ProfileArchive {
    private static NBTTagCompound profileArchiveData = new NBTTagCompound();

    public static void loadData(NBTTagCompound nbt) {
        profileArchiveData = nbt.getCompoundTag("ProfileArchiveData");
    }

    public static void saveData(NBTTagCompound compound) {
        compound.setTag("ProfileArchiveData", profileArchiveData);
    }

    public static void setLastProfile(EntityPlayer player, IProfile profile) {
        NBTTagCompound profileTag = profile.toNBT();
        profileArchiveData.setTag(player.getUniqueID().toString(), profileTag);
    }

    public static NBTTagCompound getLastProfile(EntityPlayer player) {
        if(!profileArchiveData.hasKey(player.getUniqueID().toString())) return null;
        NBTTagCompound profileTag = profileArchiveData.getCompoundTag(player.getUniqueID().toString());
        return profileTag;
    }
}
