package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.SPacketSetSelectedDeity;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
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

    public static ArrayList<AttributeFlag> getFlagsGrantedByDeity(Deity deity) {
        ArrayList<AttributeFlag> grantedFlags = new ArrayList<>();
        if(deity == null) return grantedFlags;
        switch(deity) {
            case FELIXIA:
                grantedFlags.add(AttributeFlag.FELIXIA_HEALING);
                grantedFlags.add(AttributeFlag.FELIXIA_INTIMIDATION);
                grantedFlags.add(AttributeFlag.FELIXIA_NO_WITHER);
                break;
            case SELINA:
                grantedFlags.add(AttributeFlag.SELINA_FERTILITY);
                grantedFlags.add(AttributeFlag.SELINA_IMMORTALITY);
                grantedFlags.add(AttributeFlag.SELINA_PROSPERITY);
                break;
            case RAIKA:
                grantedFlags.add(AttributeFlag.RAIKA_ELECTROCUTE);
                grantedFlags.add(AttributeFlag.RAIKA_SMITE);
                grantedFlags.add(AttributeFlag.RAIKA_SPEED);
                break;
            case ELIANA:
                grantedFlags.add(AttributeFlag.ELIANA_FLIGHT);
                grantedFlags.add(AttributeFlag.ELIANA_BREATHING);
                grantedFlags.add(AttributeFlag.ELIANA_NO_FALL);
                break;
            case MELINIAS:
                grantedFlags.add(AttributeFlag.MELINIAS_NO_MOBS);
                grantedFlags.add(AttributeFlag.MELINIAS_WATER_BREATHING);
                grantedFlags.add(AttributeFlag.MELINIAS_WATER_JET);
                break;
            case KASAI:
                grantedFlags.add(AttributeFlag.KASAI_LAVA_JET);
                grantedFlags.add(AttributeFlag.KASAI_NO_FIRE);
                grantedFlags.add(AttributeFlag.KASAI_NO_MOBS);
                break;
            case ASANA:
                grantedFlags.add(AttributeFlag.ASANA_NO_EXLODE);
                grantedFlags.add(AttributeFlag.ASANA_NO_MOBS);
                grantedFlags.add(AttributeFlag.ASANA_EARTH_CRUMPLE);
                break;
            case LILASIA:
                grantedFlags.add(AttributeFlag.LILASIA_SHADOW_HEALING);
                grantedFlags.add(AttributeFlag.LILASIA_NO_MOBS);
                grantedFlags.add(AttributeFlag.LILASIA_REINFORCEMENTS);
                break;
        }
        return grantedFlags;
    }
}
