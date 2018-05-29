package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.DeityFavor.IDeityFavor;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.SPacketSetFavor;
import me.Jonathon594.Mythria.Packets.SPacketSetSelectedDeity;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeityManager {
    private static HashMap<Integer, Deity> selectedDeities = new HashMap<>();
    private static HashMap<Deity, Integer> deityPower = new HashMap<>();

    private static HashMap<EntityPlayer, Deity> worshipers = new HashMap<>();

    public static HashMap<Integer, Deity> getSelectedDeities() {
        return selectedDeities;
    }

    public static void setDeityPower(Deity deity, int power) {
        deityPower.put(deity, power);
    }

    public static int getDeityPower(Deity deity) {
        return deityPower.containsKey(deity) ? deityPower.get(deity) : 0;
    }

    public static void setDeity(Integer entityID, Deity deity, boolean packet) {
        if (deity == null) selectedDeities.remove(entityID);
        selectedDeities.put(entityID, deity);

        if (packet)
            MythriaPacketHandler.INSTANCE.sendToAll(new SPacketSetSelectedDeity(entityID, deity == null ? -1 : deity.ordinal()));
    }

    public static void addWorshiper(EntityPlayer player, Deity deity) {
        worshipers.put(player, deity);
    }

    public static void clearWorshiper(EntityPlayer player) {
        if(worshipers.containsKey(player)) worshipers.remove(player);
    }

    public static String getDeityNameString(Deity d) {
        TextFormatting color = getDeityColor(d);
        String name = MythriaUtil.Capitalize(d.name());
        return color + "" + TextFormatting.OBFUSCATED + name;
    }

    public static TextFormatting getDeityColor(Deity d) {
        switch (d) {
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

    public static void setFavor(IDeityFavor favor, @Nullable EntityPlayerMP playerMP, Deity deity, int value) {
        favor.setFavor(deity, value);

        if(playerMP != null) {
            MythriaPacketHandler.INSTANCE.sendTo(new SPacketSetFavor(deity.ordinal(), value), playerMP);
        }
    }

    public static void setFavor(IDeityFavor favor, Deity deity, int value) {
        setFavor(favor, null, deity, value);
    }

    public static void onServerTick() {
        int tick = FMLCommonHandler.instance().getMinecraftServerInstance().getTickCounter();
        if (tick % 200 == 0) {
            for(EntityPlayer player : worshipers.keySet()) {
                player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 220, 4, false, false));
                player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1200, 4, false, false));
            }

            ArrayList<Deity> deities = new ArrayList<>();
            int totalPower = 0;
            for (Deity deity : Deity.values()) {
                deities.add(deity);
                totalPower += DeityManager.getDeityPower(deity);
            }
            Collections.shuffle(deities);

            for (Deity d : deities) {
                int amount = getWorshippers(d) * 5;
                int canGive = 8000000 - totalPower;
                if(canGive > 0) {
                    DeityManager.setDeityPower(d, DeityManager.getDeityPower(d) + Math.min(1 + amount, canGive));
                }
            }
        }
    }

    private static int getWorshippers(Deity d) {
        int c = 0;
        for(Map.Entry<EntityPlayer, Deity> e : worshipers.entrySet()) {
            if(e.getValue().equals(d)) {
                c++;
            }
        }
        return c;
    }

    public static boolean hasPower(Deity d, int power) {
        return getDeityPower(d) >= power;
    }

    public static void consumePower(Deity d, int power) {
        setDeityPower(d, getDeityPower(d) - power);
    }
}
