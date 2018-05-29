package me.Jonathon594.Mythria.Capability.Metal;

import me.Jonathon594.Mythria.Managers.SmithingManager;
import net.minecraft.nbt.NBTTagCompound;

public interface IMetal {
    void fromNBT(NBTTagCompound comp);
    NBTTagCompound toNBT();
    double getTemperature();
    SmithingManager.EnumHitType[] getSmithingHits();
    double getSmithingProgress1();
    void hit(SmithingManager.EnumHitType hitType);
    long getLastUpdate();
    void update(double ambientTemp);

    double getSmithingProgress2();

    void setSmithingProgress1(double v);
    void setSmithingProgress2(double v);
}
