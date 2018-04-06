package me.Jonathon594.Mythria.Client;

import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ClientSettings {
    public static void setPreventPickup(boolean preventPickup) {
        ClientSettings.preventPickup = preventPickup;
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(MythriaConst.MAIN_COLOR + "Item pickup is now " + (preventPickup == true ? "enabled.a" : "disabled.")));
    }

    private static boolean preventPickup = false;

    public static boolean isPreventPickup() {
        return preventPickup;
    }
}
