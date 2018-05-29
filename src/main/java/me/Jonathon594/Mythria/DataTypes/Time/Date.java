package me.Jonathon594.Mythria.DataTypes.Time;

import me.Jonathon594.Mythria.Enum.Season;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Managers.TimeManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.util.text.TextFormatting;

public class Date {
    private int MGD = 1;

    public Date() {
    }

    public Date(final int integer) {
        MGD = integer;
    }

    public String GetDateString() {
        final TextFormatting mc = MythriaConst.MAIN_COLOR;
        final TextFormatting cc = MythriaConst.CONT_COLOR;
        final int Year = getYear();
        String yearString;
        if (Year >= 0)
            yearString = Year + " C.E";
        else
            yearString = Math.abs(Year) + "B.C.E";
        final int month = getMonth();
        final String monthName = TimeManager.getMonths().get(month).getName();
        final int monthDay = getDayInMonth();
        final String dayName = getDayName();
        return cc + dayName + mc + ", " + cc + monthDay + mc + "-" + cc + monthName + mc + "-" + cc + yearString;
    }

    public int getYear() {
        return Math.floorDiv(MGD, TimeManager.getDaysPerYear());
    }

    public int getMonth() {
        int monthIndex = 0;
        for (int i = 0; i < TimeManager.getMonths().size(); i++) {
            final int daysToMonthX = getDaysToMonthX(i);
            if (getYearDay() > daysToMonthX)
                monthIndex = i;
        }
        return monthIndex;
    }

    public int getDayInMonth() {
        return getYearDay() - getDaysToMonthX(getMonth());
    }

    private String getDayName() {
        final int index = MythriaUtil.WrapInt(MGD, 1, TimeManager.getDayNames().size());
        return TimeManager.getDayNames().get(index - 1);
    }

    private int getDaysToMonthX(final int x) {
        int daysToMonthX = 0;
        for (int i = 0; i < x; i++)
            daysToMonthX += TimeManager.getMonths().get(i).getLength();
        return daysToMonthX;
    }

    public int getYearDay() {
        return MythriaUtil.WrapInt(MGD, 1, TimeManager.getDaysPerYear());
    }

    public int getYearsFromCurrent() {
        final int NewMGD = TimeManager.getCurrentDate().getMGD() - getMGD();
        final Date newDate = new Date();
        newDate.setMGD(NewMGD);
        return newDate.getYear();
    }

    public int getMGD() {
        return MGD;
    }

    public void setMGD(final int mGD) {
        MGD = mGD;
    }

    public void IncDay() {
        MGD += 1;
    }

    public void setMGDFromDayMonthYear(final int day, final int month, final int year) {
        int mGD = 0;
        mGD += TimeManager.getDaysPerYear() * (year - 1);
        mGD += getDaysToMonthX(month);
        mGD += day;
        setMGD(mGD);
    }

    public Season getSeason() {
        int dayInYear = getYearDay();
        if(dayInYear > TimeManager.getDaysPerYear() * 0.75) return Season.FALL;
        else if(dayInYear > TimeManager.getDaysPerYear() * 0.5) return Season.SUMMER;
        else if(dayInYear > TimeManager.getDaysPerYear() * 0.25) return Season.SPRING;
        else return Season.WINTER;
    }
}
