package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.StatType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiHud extends Gui {
    private static final ResourceLocation icons = new ResourceLocation(
            "minecraft:textures/gui/bars.png");

    public static double weightValue = 0.0;
    public static double staminaValue = 0.0;
    public static double thirstValue = 0.0;
    public static double hungerValue = 0.0;
    public static double temperatureValue = 0.0;

    public static double fatigueValue = 0.0;

    public static double physicalEnergyValue = 0.0;
    public static double magicalEnergyValue = 0.0;
    public static double neutralEnergyValue = 0.0;

    public GuiHud(final Minecraft mc) {
        final ScaledResolution scaled = new ScaledResolution(mc);
        final int width = scaled.getScaledWidth();
        final int height = scaled.getScaledHeight();

        mc.getTextureManager().bindTexture(icons);

        final short barWidth = 182;

        final IProfile profile = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);

        hungerValue = profile.getConsumables().get(Consumable.HUNGER) / 20;
        thirstValue = profile.getConsumables().get(Consumable.THIRST) / 20;
        fatigueValue = 1 - profile.getConsumables().get(Consumable.FATIGUE);
        staminaValue = profile.getConsumables().get(Consumable.STAMINA)
                / profile.getStat(StatType.MAX_STAMINA);
        weightValue = profile.getConsumables().get(Consumable.WEIGHT)
                / profile.getStat(StatType.MAX_WEIGHT);
        temperatureValue = profile.getConsumables().get(Consumable.TEMPERATURE) / 20;


        int offsetY = 32;
        int offset = 9;
        drawBar(width - barWidth - offset, height, height - offsetY + 3, (int) (hungerValue * (barWidth + 1)), barWidth, 30,
                true); // HUNGER
        drawBar(width - barWidth - offset, height, height - offsetY + 3 + 5, (int) (thirstValue * (barWidth + 1)), barWidth,
                10, true); // THIRST
        drawBar(width - barWidth - offset, height, height - offsetY + 3 + 10, (int) (fatigueValue * (barWidth + 1)), barWidth,
                60, true); // FATIGUE
        drawBar(width - barWidth - offset, height, height - offsetY + 3 + 10, (int) (staminaValue * (barWidth + 1)), barWidth,
                40, false); // STAMINA
        drawBar(width - barWidth - offset, height, height - offsetY + 3 + 15,
                (int) ((1 - (weightValue - 1) / 3) * (barWidth + 1)), barWidth, 60, true); // WEIGHT
        drawBar(width - barWidth - offset, height, height - offsetY + 3 + 15, (int) ((1 - weightValue) * (barWidth + 1)),
                barWidth, 0, false); // WEIGHT
        drawBar(width - barWidth - offset, height, height - offsetY + 3 + 20, (int) (temperatureValue * (barWidth + 1)),
                barWidth, 50, true); // TEMPERATURE
    }

    private void drawBar(final int width, final int height, final int top, final int filled, final int barWidth,
                         final int yuv, final boolean back) {

        final int left = width;
        if (back)
            drawTexturedModalRect(left, top, 0, yuv, barWidth, 5);
        if (filled > 0)
            drawTexturedModalRect(left, top, 0, yuv + 6, MathHelper.clamp(filled, 0, barWidth), 5);
    }
}
