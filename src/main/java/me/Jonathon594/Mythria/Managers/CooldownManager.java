package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.Cooldown;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

public class CooldownManager {
    public static ArrayList<Cooldown> cooldowns = new ArrayList<>();

    public static void checkCooldowns() {
        if(System.currentTimeMillis() % 1000L < 50) {
            ArrayList<Cooldown> removeQue = new ArrayList<>();

            for (Cooldown c : cooldowns) {
                if (c.getEndTime() < System.currentTimeMillis()) removeQue.add(c); // Remove it
                if (c.getPlayer().equals(null)) removeQue.add(c); // Remove it
            }
            cooldowns.removeAll(removeQue);
        }
    }

    public static boolean hasCooldown(Cooldown.CooldownType type, EntityPlayer player) {
        Cooldown c = getCooldown(type, player);
        if(c == null) return false;
        else if(c.getEndTime() > System.currentTimeMillis()) return true;
        return false;
    }

    private static Cooldown getCooldown(Cooldown.CooldownType type, EntityPlayer player) {
        for(Cooldown c : cooldowns) {
            if(c.getPlayer().equals(player) && c.getType().equals(type)) return c;
        }
        return null;
    }

    public static void addCooldown(EntityPlayer player, Cooldown.CooldownType type, long ms) {
        Cooldown c = new Cooldown(type, ms + System.currentTimeMillis(), player);
        cooldowns.add(c);
    }
}
