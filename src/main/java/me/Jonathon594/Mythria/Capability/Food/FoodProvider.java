package me.Jonathon594.Mythria.Capability.Food;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FoodProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IFood.class)
    public static final Capability<IFood> FOOD_CAP = null;
    private final IFood instance = FOOD_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == FOOD_CAP;
    }

    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        return capability == FOOD_CAP ? FOOD_CAP.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return FOOD_CAP.getStorage().writeNBT(FOOD_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        FOOD_CAP.getStorage().readNBT(FOOD_CAP, instance, null, nbt);
    }

}
