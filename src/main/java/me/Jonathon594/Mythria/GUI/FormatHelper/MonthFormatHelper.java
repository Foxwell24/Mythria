package me.Jonathon594.Mythria.GUI.FormatHelper;

import me.Jonathon594.Mythria.DataTypes.Time.Month;
import me.Jonathon594.Mythria.Managers.TimeManager;
import net.minecraft.client.gui.GuiSlider.FormatHelper;

public class MonthFormatHelper implements FormatHelper {

    @Override
    public String getText(final int id, final String name, final float value) {
        final Month m = TimeManager.getMonths().get(Math.round(value));
        return "Month: " + m.getName() + " (" + m.getLength() + " days)";
    }

}
