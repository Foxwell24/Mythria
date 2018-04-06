package me.Jonathon594.Mythria.Listener;

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OreGenListener {
    @SubscribeEvent
    public static void onOreGeneration(OreGenEvent.GenerateMinable event) {
        event.setResult(Event.Result.DENY);
    }
}
