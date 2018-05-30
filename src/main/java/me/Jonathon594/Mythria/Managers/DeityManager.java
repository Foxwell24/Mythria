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

    public static HashMap<Deity, HashMap<EntityPlayer, Integer>> worshipGains = new HashMap<>();
    public static HashMap<Deity, HashMap<EntityPlayer, Integer>> blessingLosses = new HashMap<>();

    public static void initialize() {
        for (Deity d : Deity.values()) {
            worshipGains.put(d, new HashMap<EntityPlayer, Integer>());
            blessingLosses.put(d, new HashMap<EntityPlayer, Integer>());
        }
    }

    public static void addWorshipGain(Deity d, EntityPlayer p, int i) {
        HashMap<EntityPlayer, Integer> deityMap = worshipGains.get(d);
        deityMap.put(p, deityMap.containsKey(p) ? deityMap.get(p) + i : i);
        worshipGains.put(d, deityMap);
    }

    public static void addBlessingLoss(Deity d, EntityPlayer p, int i) {
        HashMap<EntityPlayer, Integer> deityMap = blessingLosses.get(d);
        deityMap.put(p, deityMap.containsKey(p) ? deityMap.get(p) + i : i);
        blessingLosses.put(d, deityMap);
    }

    public static int getTotalWorshipGains(Deity d) {
        int i = 0;
        for(Map.Entry<EntityPlayer, Integer> e : worshipGains.get(d).entrySet()) {
            i += e.getValue();
        }
        return i;
    }

    public static int getTotalBlessingLosses(Deity d) {
        int i = 0;
        for(Map.Entry<EntityPlayer, Integer> e : blessingLosses.get(d).entrySet()) {
            i += e.getValue();
        }
        return i;
    }

    public static HashMap<EntityPlayer, Integer> getIndividualWorshipGains(Deity d) {
        return worshipGains.get(d);
    }

    public static HashMap<EntityPlayer, Integer> getIndividualBlessingLosses(Deity d) {
        return blessingLosses.get(d);
    }

    public static void clearCounters() {
        for (Deity d : Deity.values()) {
            worshipGains.put(d, new HashMap<EntityPlayer, Integer>());
            blessingLosses.put(d, new HashMap<EntityPlayer, Integer>());
        }
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
            MythriaPacketHandler.INSTANCE.sendTo(new SPacketSetFavor(value, deity.ordinal()), playerMP);
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
                if(totalPower < 8000000) {
                    DeityManager.setDeityPower(d, DeityManager.getDeityPower(d) + 1);
                }
                for(Map.Entry<EntityPlayer, Deity> e : worshipers.entrySet()) {
                    if(!e.getValue().equals(d)) continue;
                    int max = 8000000 - totalPower;
                    if(max == 0) continue;

                    int a = Math.min(5, max);

                    setDeityPower(d, getDeityPower(d) + a);
                    addWorshipGain(d, e.getKey(), a);
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

    public static void consumePower(Deity d, int power, EntityPlayer player) {
        setDeityPower(d, getDeityPower(d) - power);
        addBlessingLoss(d, player, power);
    }
}
