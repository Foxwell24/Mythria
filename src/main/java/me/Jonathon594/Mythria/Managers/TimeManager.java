package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.DataTypes.Time.Month;
import me.Jonathon594.Mythria.Event.NewDayEvent;
import me.Jonathon594.Mythria.Storage.GlobalSaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

import java.util.ArrayList;
import java.util.List;

public class TimeManager {
    private final static List<String> DayNames = new ArrayList<>();
    private final static List<Month> Months = new ArrayList<>();
    public static float ticks = 0;
    private static Date currentDate;
    private static int daysPerYear = 0;
    private static int tick;

    public static Date getCurrentDate() {
        return currentDate;
    }

    public static List<String> getDayNames() {
        return DayNames;
    }

    public static int getDaysPerYear() {
        return daysPerYear;
    }

    public static List<Month> getMonths() {
        return Months;
    }

    public static void Initialize() {
        currentDate = new Date(1);

        DayNames.add("Ras");
        DayNames.add("Mayark");
        DayNames.add("Tosalias");
        DayNames.add("Judios");
        DayNames.add("Amara");
        DayNames.add("Kaias");
        DayNames.add("Oast");

        Months.add(new Month("Infio", 31));
        Months.add(new Month("Amo", 30));
        Months.add(new Month("Ver", 28));
        Months.add(new Month("Inder", 31));
        Months.add(new Month("Granum", 30));
        Months.add(new Month("Ignis", 30));
        Months.add(new Month("Sitis", 31));
        Months.add(new Month("Casus", 30));
        Months.add(new Month("Vicis", 31));
        Months.add(new Month("Neto", 30));
        Months.add(new Month("Gelu", 31));
        Months.add(new Month("Exeo", 31));

        for (final Month m : Months)
            daysPerYear += m.getLength();
    }

    public static void onTick(final ServerTickEvent event) {
        tick++;
        if (tick == 200) {
            tick = 1;
            final World w = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
            final long time = w.getWorldTime() % 24000;
            if (time < 200) {
                currentDate.IncDay();
                final NewDayEvent nde = new NewDayEvent();
                MinecraftForge.EVENT_BUS.post(nde);
                GlobalSaveData.get(w).markDirty();
                for (final EntityPlayer p : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                        .getPlayers()) {
                    p.sendMessage(new TextComponentString(currentDate.GetDateString()));
                    FoodManager.UpdatePlayerInventory(p);
                }
            }
        }
    }
}
