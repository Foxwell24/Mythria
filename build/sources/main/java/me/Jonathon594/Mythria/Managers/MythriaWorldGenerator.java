package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class MythriaWorldGenerator implements IWorldGenerator {

    private WorldGenerator genOreCopper;
    private WorldGenerator genOreTin;
    private WorldGenerator genOreCoal;
    private WorldGenerator genOreIron;
    private WorldGenerator genOreSilver;
    private WorldGenerator genOreGold;
    private WorldGenerator genOreRedstone;
    private WorldGenerator genOreLapis;
    private WorldGenerator genOrePlatinum;
    private WorldGenerator genOreDiamond;
    private WorldGenerator genOreTungsten;
    private WorldGenerator genOreTitanium;

    public MythriaWorldGenerator() {
        genOreCopper = new WorldGenMinable(MythriaBlocks.COPPER_ORE.getDefaultState(), 24);
        genOreTin = new WorldGenMinable(MythriaBlocks.TIN_ORE.getDefaultState(), 14);
        genOreCoal = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 30);
        genOreIron = new WorldGenMinable(MythriaBlocks.IRON_ORE.getDefaultState(), 16);
        genOreSilver = new WorldGenMinable(MythriaBlocks.SILVER_ORE.getDefaultState(), 12);
        genOreGold = new WorldGenMinable(MythriaBlocks.GOLD_ORE.getDefaultState(), 10);
        genOreRedstone = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 8);
        genOreLapis = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 8);
        genOrePlatinum = new WorldGenMinable(MythriaBlocks.PLATINUM_ORE.getDefaultState(), 8);
        genOreDiamond = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 6);
        genOreTitanium = new WorldGenMinable(MythriaBlocks.TITANIUM_ORE.getDefaultState(), 6);
        genOreTungsten = new WorldGenMinable(MythriaBlocks.TUNGSTEN_ORE.getDefaultState(), 6);
    }

    @Override
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
                         final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {

        GenerateOre(genOreCopper, 256, 36, chunkX, random, chunkZ, world, 14);
        GenerateOre(genOreTin, 256, 36, chunkX, random, chunkZ, world, 8);
        GenerateOre(genOreCoal, 128, 0, chunkX, random, chunkZ, world, 16);
        GenerateOre(genOreIron, 128, 16, chunkX, random, chunkZ, world, 14);
        GenerateOre(genOreSilver, 48, 8, chunkX, random, chunkZ, world, 5);
        GenerateOre(genOreGold, 48, 0, chunkX, random, chunkZ, world, 4);
        GenerateOre(genOreRedstone, 32, 0, chunkX, random, chunkZ, world, 6);
        GenerateOre(genOreLapis, 32, 0, chunkX, random, chunkZ, world, 6);
        GenerateOre(genOrePlatinum, 36, 0, chunkX, random, chunkZ, world, 3);
        GenerateOre(genOreDiamond, 12, 0, chunkX, random, chunkZ, world, 2);
        GenerateOre(genOreTitanium, 16, 0, chunkX, random, chunkZ, world, 2);
        GenerateOre(genOreTungsten, 16, 0, chunkX, random, chunkZ, world, 2);
    }


    private void GenerateOre(final WorldGenerator gen, final int maxHeight, final int minHeight, final int chunkX,
                             final Random random, final int chunkZ, final World world, final int chances) {
        final int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chances; i++) {
            final int x = chunkX * 16 + random.nextInt(16);
            final int y = 0 + random.nextInt(heightDiff);
            final int z = chunkZ * 16 + random.nextInt(16);
            gen.generate(world, random, new BlockPos(x, y, z));
        }
    }
}
