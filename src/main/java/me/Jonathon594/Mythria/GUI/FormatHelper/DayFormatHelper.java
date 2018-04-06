package me.Jonathon594.Mythria.GUI.FormatHelper;

import net.minecraft.client.gui.GuiSlider.FormatHelper;

public class DayFormatHelper implements FormatHelper {

    @Override
    public String getText(final int id, final String name, final float value) {
        return "Day: " + Math.round(value);
    }

}
