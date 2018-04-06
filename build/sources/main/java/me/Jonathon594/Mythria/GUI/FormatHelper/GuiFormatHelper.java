package me.Jonathon594.Mythria.GUI.FormatHelper;

import net.minecraft.client.gui.GuiSlider.FormatHelper;

public class GuiFormatHelper implements FormatHelper {

    @Override
    public String getText(final int id, final String name, final float value) {
        final int v = Math.round(value);

        if (v == 0)
            return "Gender: Male";
        else
            return "Gender: Female";
    }

}
