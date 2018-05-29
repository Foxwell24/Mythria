package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ProfileShowGui extends GuiScreen {

    private GuiButton buttonBack;

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button == buttonBack) {
            mc.displayGuiScreen(null);
            mc.player.openGui(Mythria.instance, MythriaGui.MYTHRIA_MENU_GUI.ordinal(), mc.world, 0, 0, 0);
        }
    }

    @Override
    public void initGui() {
        final IProfile p = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 2 - 100, 200, 20,
                "Name: " + p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName()));
        String g = "Male";
        if (p.getGender() == 1)
            g = "Female";
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 2 - 75, 200, 20, "Gender: " + g));
        buttonList.add(new GuiButton(2, width / 2 - 100, height / 2 - 50, 200, 20,
                "Age: " + p.getBirthDay().getYearsFromCurrent()));
        buttonList
                .add(new GuiButton(3, width / 2 - 100, height / 2 - 25, 200, 20, "Perk Points: " + p.getAttributePoints()));
        buttonList.add(new GuiButton(5, width / 2 - 100, height / 2, 200, 20, "Level: " + p.getPlayerLevel()));
        buttonList.add(buttonBack = new GuiButton(6, width / 2 - 100, height / 2 + 50, "Back"));
    }
}
