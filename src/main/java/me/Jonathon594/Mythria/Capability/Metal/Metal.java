package me.Jonathon594.Mythria.Capability.Metal;

import me.Jonathon594.Mythria.Managers.SmithingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class Metal implements IMetal {

    private SmithingManager.EnumHitType[] smithingHits = new SmithingManager.EnumHitType[3];
    private double smithingProgress1 = 0.1;
    private double smithingProgress2 = 0.9;
    private double temperature;
    private long lastUpdate;

    @Override
    public void fromNBT(NBTTagCompound comp) {
        String hits = comp.getString("smithingHits");
        String[] parts = hits.split(" ");
        for(int i = 0; i < 3; i++) {
            try {
                smithingHits[i] = SmithingManager.EnumHitType.valueOf(parts[i]);
            } catch (IllegalArgumentException e) {

            }
        }
        smithingProgress1 = comp.getDouble("smithingProgress1");
        smithingProgress2 = comp.getDouble("smithingProgress2");
        temperature = comp.getDouble("temperature");
        lastUpdate = comp.getLong("lastUpdate");
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound comp = new NBTTagCompound();
        String hits = "";
        for(int i = 0; i < 3; i++) {
            hits+= smithingHits[i] == null ? "empty " : smithingHits[i].toString() + " ";
        }
        comp.setString("smithingHits", hits);
        comp.setDouble("smithingProgress1", smithingProgress1);
        comp.setDouble("smithingProgress2", smithingProgress2);
        comp.setDouble("temperature", temperature);
        comp.setLong("lastUpdate", lastUpdate);
        return comp;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public SmithingManager.EnumHitType[] getSmithingHits() {
        return smithingHits;
    }

    @Override
    public double getSmithingProgress1() {
        return smithingProgress1;
    }

    @Override
    public void hit(SmithingManager.EnumHitType hitType) {
        smithingHits[0] = smithingHits[1];
        smithingHits[1] = smithingHits[2];
        smithingHits[2] = hitType;
    }

    @Override
    public long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public void update(double ambientTemp) {
        long currentTime = System.currentTimeMillis();
        long lastTime = getLastUpdate();
        long delta = currentTime - lastTime;

        long seconds = delta / 1000;

        double temp = getTemperature();
        double newTemp = Math.max(temp - 2 * seconds, ambientTemp);
        setTemperature(newTemp);

        lastUpdate = System.currentTimeMillis();
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public double getSmithingProgress2() {
        return smithingProgress2;
    }

    @Override
    public void setSmithingProgress1(double v) {
        smithingProgress1 = MathHelper.clamp(v, 0.0, 1.0);
    }

    @Override
    public void setSmithingProgress2(double v) {
        smithingProgress2 = MathHelper.clamp(v, 0.0, 1.0);
    }
}
