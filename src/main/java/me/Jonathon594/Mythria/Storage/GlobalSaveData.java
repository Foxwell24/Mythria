package me.Jonathon594.Mythria.Storage;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import me.Jonathon594.Mythria.Managers.TimeManager;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class GlobalSaveData extends WorldSavedData {

    private static final String DATA_NAME = Mythria.MODID + "_global_data";

    public GlobalSaveData() {
        super(DATA_NAME);
    }

    public GlobalSaveData(final String name) {
        super(name);
    }

    public static GlobalSaveData get(final World world) {
        final MapStorage storage = world.getMapStorage();
        GlobalSaveData instance = (GlobalSaveData) storage.getOrLoadData(GlobalSaveData.class, DATA_NAME);

        if (instance == null) {
            instance = new GlobalSaveData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        TimeManager.getCurrentDate().setMGD(nbt.getInteger("CurrentDate"));
        for(Deity deity : Deity.values()) {
            int power = nbt.getInteger("DeityPower." + MythriaUtil.Capitalize(deity.name()));
            DeityManager.setDeityPower(deity, power);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setInteger("CurrentDate", TimeManager.getCurrentDate().getMGD());
        for(Deity deity : Deity.values()) {
            compound.setInteger("DeityPower." + MythriaUtil.Capitalize(deity.name()), DeityManager.getDeityPower(deity));
        }
        return compound;
    }

}
