package me.Jonathon594.Mythria.Managers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HealManager {
    private static int tick = 0;

    public static void onTick(TickEvent.ServerTickEvent event) {
        tick++;

        if(tick >= 3000) {
            tick = 0;

            for (final EntityPlayer p : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                    .getPlayers()) {
                p.setHealth(p.getHealth() + 1);
            }
        }
    }
}
