package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Managers.TreeManager;
import me.Jonathon594.Mythria.Managers.WorldGenGroundRock;
import me.Jonathon594.Mythria.Managers.WorldGenGroundStick;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class TerrainGenListener {
    @SubscribeEvent
    public static void onDecorate(DecorateBiomeEvent.Decorate event) {
        if(!event.getType().equals(DecorateBiomeEvent.Decorate.EventType.GRASS)) return;
        for(int i = 0; i < 4; i++) {
            new WorldGenGroundStick().generate(event.getWorld(), event.getRand(), event.getPos().add(event.getRand().nextInt(16), 0, event.getRand().nextInt(16)));
            new WorldGenGroundRock().generate(event.getWorld(), event.getRand(), event.getPos());
        }
    }

    @SubscribeEvent
    public static void onSapplingGrowTree (SaplingGrowTreeEvent event) {
        if(event.getWorld().isRemote) return;
        TreeManager.dropSticks(event.getPos(), event.getWorld(), event.getRand());
    }
}
