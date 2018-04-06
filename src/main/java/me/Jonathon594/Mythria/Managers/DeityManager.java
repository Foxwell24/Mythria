package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.SPacketSetSelectedDeity;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;

public class DeityManager {
    public static HashMap<Integer, Deity> getSelectedDeities() {
        return selectedDeities;
    }

    private static HashMap<Integer, Deity> selectedDeities = new HashMap<>();

    public static void setDeity(Integer entityID, Deity deity, boolean packet) {
        if(deity == null) selectedDeities.remove(entityID);
        selectedDeities.put(entityID, deity);

        if(packet) MythriaPacketHandler.INSTANCE.sendToAll(new SPacketSetSelectedDeity(entityID, deity == null ? -1: deity.ordinal()));
    }

    public static String getDeityNameString(Deity d) {
        TextFormatting color = getDeityColor(d);
        String name = MythriaUtil.Capitalize(d.name());
        return color + "" + TextFormatting.OBFUSCATED + name;
    }

    public static TextFormatting getDeityColor(Deity d) {
        switch(d) {
            case FELIXIA:
                return TextFormatting.WHITE;
            case SELINA:
                return TextFormatting.LIGHT_PURPLE;
            case RAIKA:
                return TextFormatting.YELLOW;
            case ELIANA:
                return TextFormatting.GRAY;
            case MELINIAS:
                return TextFormatting.BLUE;
            case KASAI:
                return TextFormatting.RED;
            case ASANA:
                return TextFormatting.DARK_GRAY;
            case LILASIA:
                return TextFormatting.BLACK;
        }
        return TextFormatting.WHITE;
    }
}
