package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.SpawnData;
import me.Jonathon594.Mythria.Entity.Weather.*;
import me.Jonathon594.Mythria.Enum.Season;
import me.Jonathon594.Mythria.Enum.StormType;
import me.Jonathon594.Mythria.Module.TornadoModule;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WeatherManager {
    private static HashMap<StormType, SpawnData> stormSpawnData = new HashMap<>();

    public static void initialize() {
        TornadoModule.createBlockShapes();

        createSpawnData();
    }

    private static void createSpawnData() {
        //Tornadoes
        stormSpawnData.put(StormType.TORNADOF1,
                new SpawnData(0.002)
                        .addBiomeModifier(Biomes.DESERT, 2)
                        .addBiomeModifier(Biomes.SAVANNA, 2)
                        .addBiomeModifier(Biomes.PLAINS, 4)
                        .addBiomeModifier(Biomes.ICE_PLAINS, 2)
                        .addSeasonModifier(Season.SUMMER, 0.5)
                        .addSeasonModifier(Season.WINTER, 0.5));
        stormSpawnData.put(StormType.TORNADOF2,
                new SpawnData(0.0016)
                        .addBiomeModifier(Biomes.DESERT, 2)
                        .addBiomeModifier(Biomes.SAVANNA, 2)
                        .addBiomeModifier(Biomes.PLAINS, 4)
                        .addBiomeModifier(Biomes.ICE_PLAINS, 2)
                        .addSeasonModifier(Season.SUMMER, 0.5)
                        .addSeasonModifier(Season.WINTER, 0.5));
        stormSpawnData.put(StormType.TORNADOF3,
                new SpawnData(0.0012)
                        .addBiomeModifier(Biomes.DESERT, 2)
                        .addBiomeModifier(Biomes.SAVANNA, 2)
                        .addBiomeModifier(Biomes.PLAINS, 4)
                        .addBiomeModifier(Biomes.ICE_PLAINS, 2)
                        .addSeasonModifier(Season.SUMMER, 0.5)
                        .addSeasonModifier(Season.WINTER, 0.5));
        stormSpawnData.put(StormType.TORNADOF4,
                new SpawnData(0.0008)
                        .addBiomeModifier(Biomes.DESERT, 2)
                        .addBiomeModifier(Biomes.SAVANNA, 2)
                        .addBiomeModifier(Biomes.PLAINS, 4)
                        .addBiomeModifier(Biomes.ICE_PLAINS, 2)
                        .addSeasonModifier(Season.SUMMER, 0.5)
                        .addSeasonModifier(Season.WINTER, 0.5));
        stormSpawnData.put(StormType.TORNADOF5,
                new SpawnData(0.0004)
                        .addBiomeModifier(Biomes.DESERT, 2)
                        .addBiomeModifier(Biomes.SAVANNA, 2)
                        .addBiomeModifier(Biomes.PLAINS, 4)
                        .addBiomeModifier(Biomes.ICE_PLAINS, 2)
                        .addSeasonModifier(Season.SUMMER, 0.5)
                        .addSeasonModifier(Season.WINTER, 0.5));
    }

    public static void onServerTick() {
        List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        if (players.size() == 0) return;
        Collections.shuffle(players);
        EntityPlayerMP player = players.get(0);
        if (player.world.getWorldTime() % 1200 != 0) return;

        if (player.dimension != 0) return;
        BlockPos pos = MythriaUtil.getRandomLocationInRadius(128, player.getPosition(), player.world);
        Biome b = player.world.getBiome(pos);
        Season s = TimeManager.getCurrentDate().getSeason();

        for (StormType type : StormType.values()) {
            double chance = getSpawnChance(type, b, s, player.world);
            if(Math.random() < chance) {
                float d = (float) (Math.random() * Math.PI * 2);
                EntityStorm storm = getNewStormEntityFromType(type, player.world);
                storm.rotationYaw = d;
                storm.setPosition(pos.getX(), pos.getY(), pos.getZ());
                player.world.spawnEntity(storm);
                for(EntityPlayerMP p : players) {
                    p.sendMessage(new TextComponentString(type + " has spawned!"));
                }
            }
        }
    }

    private static double getSpawnChance(StormType type, Biome b, Season s, World worldIn) {
        SpawnData data = getSpawnData(type);
        if(data.needsRain() && !worldIn.isThundering()) return 0.0;
        return data.getSpawnChance(b, s);
    }

    private static SpawnData getSpawnData(StormType type) {
        return stormSpawnData.get(type);
    }

    private static EntityStorm getNewStormEntityFromType(StormType type, World world) {
        switch (type) {
            case TORNADOF1:
                return new EntityTornadoF1(world);
            case TORNADOF2:
                return new EntityTornadoF2(world);
            case TORNADOF3:
                return new EntityTornadoF3(world);
            case TORNADOF4:
                return new EntityTornadoF4(world);
            case TORNADOF5:
                return new EntityTornadoF5(world);
        }
        return null;
    }
}
