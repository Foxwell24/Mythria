package me.Jonathon594.Mythria.Capability.DeityFavor;

import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.Managers.DeityManager;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;
import java.util.Map.Entry;

public class DeityFavor implements IDeityFavor {

    private HashMap<Deity, Integer> favorLevels = new HashMap<>();

    @Override
    public void setFavor(Deity d, int favor) {
        favorLevels.put(d, favor);
    }

    @Override
    public int getFavor(Deity d) {
        return favorLevels.containsKey(d) ? favorLevels.get(d) : 0;
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        for(Entry<Deity, Integer> e : favorLevels.entrySet()) {
            compound.setInteger(e.getKey().name(), e.getValue());
        }
        return compound;
    }

    @Override
    public void fromNBT(NBTTagCompound compound) {
        Set<String> tags = compound.getKeySet();
        for(String s : tags) {
            Deity d = Deity.valueOf(s);
            int v = compound.getInteger(s);
            favorLevels.put(d, v);
        }
    }

    @Override
    public boolean hasBlessing(BlessingType blessingType) {
        switch(blessingType) {
            case ELIANA_FLIGHT:
                return getFavor(Deity.ELIANA) >= 1000000;
            case FELIXIA_NO_WITHER:
                return getFavor(Deity.FELIXIA) >= 10000;
            case FELIXIA_HEALING:
                return getFavor(Deity.FELIXIA) >= 100000;
            case FELIXIA_INTIMIDATION:
                return getFavor(Deity.FELIXIA) >= 1000000;
            case FELIXIA_BURN:
                return getFavor(Deity.FELIXIA) <= -1000000;
            case ELIANA_NO_FALL:
                return getFavor(Deity.ELIANA) >= 100000;
            case ELIANA_BREATHING:
                return getFavor(Deity.ELIANA) >= 10000;
            case ELIANA_SUFFOCATE:
                return getFavor(Deity.ELIANA) <= -1000000;
            case SELINA_PROSPERITY:
                return getFavor(Deity.SELINA) >= 100000;
            case SELINA_IMMORTALITY:
                return getFavor(Deity.SELINA) >= 1000000;
            case SELINA_INFERTILITY:
                return getFavor(Deity.SELINA) <= -1000000;
            case RAIKA_SPEED:
                return getFavor(Deity.RAIKA) >= 10000;
            case RAIKA_ELECTROCUTE:
                return getFavor(Deity.RAIKA) >= 100000;
            case RAIKA_SMITE:
                return getFavor(Deity.RAIKA) >= 1000000;
            case RAIKA_WRATH:
                return getFavor(Deity.RAIKA) <= -1000000;
            case MELINIAS_NO_MOBS:
                return getFavor(Deity.MELINIAS) >= 10000;
            case MELINIAS_WATER_BREATHING:
                return getFavor(Deity.MELINIAS) >= 100000;
            case MELINIAS_WATER_JET:
                return getFavor(Deity.MELINIAS) >= 1000000;
            case MELINIAS_WATER_CURSE:
                return getFavor(Deity.MELINIAS) <= -1000000;
            case KASAI_NO_MOBS:
                return getFavor(Deity.KASAI) >= 10000;
            case KASAI_NO_FIRE:
                return getFavor(Deity.KASAI) >= 100000;
            case KASAI_LAVA_JET:
                return getFavor(Deity.KASAI) >= 1000000;
            case KASAI_NETHER_CURSE:
                return getFavor(Deity.KASAI) <= -1000000;
            case ASANA_NO_MOBS:
                return getFavor(Deity.ASANA) >= 10000;
            case ASANA_NO_EXLODE:
                return getFavor(Deity.ASANA) >= 100000;
            case ASANA_EARTH_CRUMPLE:
                return getFavor(Deity.ASANA) >= 1000000;
            case ASANA_EARTH_POISON:
                return getFavor(Deity.ASANA) <= -1000000;
            case LILASIA_NO_MOBS:
                return getFavor(Deity.LILASIA) >= 10000;
            case LILASIA_SHADOW_HEALING:
                return getFavor(Deity.LILASIA) >= 100000;
            case LILASIA_REINFORCEMENTS:
                return getFavor(Deity.LILASIA) >= 1000000;
            case LILASIA_SHADOW_CURSE:
                return getFavor(Deity.LILASIA) <= -1000000;
            case SELINA_NO_NATURE_MOBS:
                return getFavor(Deity.SELINA) >= 10000;
        }
        return false;
    }
}
