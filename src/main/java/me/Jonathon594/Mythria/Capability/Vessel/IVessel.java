package me.Jonathon594.Mythria.Capability.Vessel;

import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public interface IVessel {

    ItemStackHandler getInventory();

    void fromNBT(NBTTagCompound comp);
    NBTTagCompound toNBT();

    void setMetal(SmeltingRecipe.EnumMetal metal);

    boolean hasMetal();

    SmeltingRecipe.EnumMetal getMetal();

    void setMetalCount(int amount);

    double getTemp();

    void setTemp(double ambientTemp);

    int getMetalCount();

    void update(double ambientTemp, ItemStack stack);
}
