package me.Jonathon594.Mythria.Capability.Food;

import net.minecraft.nbt.NBTTagCompound;

public interface IFood {

    void fromNBT(NBTTagCompound comp);

    double getAge();

    void setAge(double age);

    int getCooked();

    void setCooked(int cooked);

    int getOrigin();

    void setOrigin(int origin);

    NBTTagCompound toNBT();
}
