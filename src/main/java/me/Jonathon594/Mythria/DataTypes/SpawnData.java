package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.Season;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class SpawnData {
    public SpawnData(double baseChance) {
        this.baseChance = baseChance;
    }

    private double baseChance;
    private HashMap<Biome, Double> biomeMod = new HashMap<>();
    private HashMap<Season, Double> seasonMod = new HashMap<>();

    public boolean needsRain() {
        return needsRain;
    }

    public void setNeedsRain(boolean needsRain) {
        this.needsRain = needsRain;
    }

    boolean needsRain = true;

    public SpawnData addBiomeModifier(Biome b, double v) {
        biomeMod.put(b, v);
        return this;
    }

    public SpawnData addSeasonModifier(Season s, double v) {
        seasonMod.put(s, v);
        return this;
    }

    public double getBiomeModifer(Biome b) {
        return biomeMod.containsKey(b) ? biomeMod.get(b) : 1.0;
    }

    public double getSeasonModifier(Season s) {
        return seasonMod.containsKey(s) ? seasonMod.get(s) : 1.0;
    }

    public double getSpawnChance(Biome b, Season s) {
        return getBaseChance() * getBiomeModifer(b) * getSeasonModifier(s);
    }

    private double getBaseChance() {
        return baseChance;
    }
}
