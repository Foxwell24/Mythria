package me.Jonathon594.Mythria.Capability.Vessel;

import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;

public class Vessel implements IVessel {

    ItemStackHandler inventory = new ItemStackHandler();

    private SmeltingRecipe.EnumMetal metal = null;
    private int metalCount;
    private double temperature;
    private long lastUpdateTime;

    public Vessel() {
        inventory.setSize(11);
    }

    @Override
    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void fromNBT(NBTTagCompound comp) {
        inventory.deserializeNBT(comp.getCompoundTag("inventory"));
        try {
            metal = SmeltingRecipe.EnumMetal.valueOf(comp.getString("metal"));
        } catch (IllegalArgumentException e) {
            metal = null;
        }
        metalCount = comp.getInteger("metalCount");
        temperature = comp.getInteger("temperature");
        lastUpdateTime = comp.getLong("lastUpdateTime");
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound comp = new NBTTagCompound();
        comp.setTag("inventory", inventory.serializeNBT());
        if (metal != null) comp.setString("metal", metal.toString());
        if (metalCount > 0) comp.setInteger("metalCount", metalCount);
        comp.setDouble("temperature", temperature);
        comp.setLong("lastUpdateTime", lastUpdateTime);
        return comp;
    }

    @Override
    public void setMetal(SmeltingRecipe.EnumMetal metal) {
        this.metal = metal;
    }

    @Override
    public boolean hasMetal() {
        return metal != null && metalCount > 0;
    }

    @Override
    public SmeltingRecipe.EnumMetal getMetal() {
        return metal;
    }

    @Override
    public void setMetalCount(int amount) {
        metalCount = amount;
    }

    @Override
    public double getTemp() {
        return temperature;
    }

    @Override
    public void setTemp(double temp) {
        temperature = temp;
    }

    @Override
    public int getMetalCount() {
        return metalCount;
    }

    @Override
    public void update(double ambientTemp, ItemStack stack) {
        long currentTime = System.currentTimeMillis();
        long lastTime = getLastUpdateTime();
        long delta = currentTime - lastTime;

        long seconds = delta / 1000;

        double temp = getTemp();
        double newTemp = Math.max(temp - 0.5 * seconds, ambientTemp);
        setTemp(newTemp);

        final ArrayList<String> lines = new ArrayList<>();
        String state = "Empty";
        if(hasMetal()) {
            double melt = SmeltingManager.getRecipe(getMetal()).getMeltingPoint();
            state = "Cold ";
            if (temp > melt * 0.25) state = "Warm ";
            if (temp > melt * 0.5) state = "Hot ";
            if (temp > melt * 0.75) state = "Very Hot ";
            if (temp > melt) state = "Molten ";
            state = state + MythriaUtil.Capitalize(getMetal().toString());
        }
        lines.add(MythriaConst.CONT_COLOR + state);
        lines.add(MythriaConst.CONT_COLOR + "" + metalCount + " mL");
        MythriaUtil.addLoreToItemStack(stack, lines);

        lastUpdateTime = System.currentTimeMillis();
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
}
