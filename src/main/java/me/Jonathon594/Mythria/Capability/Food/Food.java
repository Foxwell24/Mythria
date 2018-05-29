package me.Jonathon594.Mythria.Capability.Food;

import net.minecraft.nbt.NBTTagCompound;

public class Food implements IFood {
    private int origin = 0;
    private int cooked = 0;
    private double age = 0.0;

    @Override
    public void fromNBT(final NBTTagCompound comp) {
        if (comp == null)
            return;
        origin = comp.getInteger("Origin");
        cooked = comp.getInteger("Cooked");
        age = comp.getDouble("Age");
    }

    @Override
    public double getAge() {
        return age;
    }

    @Override
    public int getCooked() {
        return cooked;
    }

    @Override
    public int getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(final int origin) {
        this.origin = origin;
    }

    @Override
    public NBTTagCompound toNBT() {
        final NBTTagCompound comp = new NBTTagCompound();
        comp.setInteger("Origin", origin);
        comp.setInteger("Cooked", cooked);
        comp.setDouble("Age", age);
        return comp;
    }

    @Override
    public void setCooked(final int cooked) {
        this.cooked = cooked;
    }

    @Override
    public void setAge(final double age) {
        this.age = age;
    }

}
