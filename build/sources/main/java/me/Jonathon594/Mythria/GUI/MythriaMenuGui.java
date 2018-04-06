package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.CommandPacket;
import me.Jonathon594.Mythria.Packets.CommandPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MythriaMenuGui extends GuiScreen {
    private GuiButton buttonNewProfile;
    private GuiButton buttonShowProfile;
    private GuiButton buttonShowPersonalities;
    private GuiButton buttonShowTradeSkills;
    private GuiButton buttonShowCombatSkills;
    private GuiButton buttonShowLifeSkills;
    private GuiButton buttonShowAttributes;

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button == buttonNewProfile) {
            mc.displayGuiScreen(null);
            mc.player.openGui(Mythria.instance, MythriaGui.MYTHRIA_PROFILE_CREATION_GUI.ordinal(), mc.world, 0, 0, 0);
        }
        if (button == buttonShowProfile)
            mc.player.openGui(Mythria.instance, MythriaGui.MYTHRIA_SHOW_PROFILE_GUI.ordinal(), mc.world, 0, 0, 0);
        if (button == buttonShowPersonalities) {
            mc.displayGuiScreen(null);
            MythriaPacketHandler.INSTANCE.sendToServer(new CommandPacket(CommandPacketHandler.OPEN_PERSONALITIES_GUI));
        }
        if (button == buttonShowTradeSkills) {
            mc.displayGuiScreen(null);
            MythriaPacketHandler.INSTANCE.sendToServer(new CommandPacket(CommandPacketHandler.OPEN_TRADE_SKILLS_GUI));
        }
        if (button == buttonShowLifeSkills) {
            mc.displayGuiScreen(null);
            MythriaPacketHandler.INSTANCE.sendToServer(new CommandPacket(CommandPacketHandler.OPEN_LIFE_SKILLS_GUI));
        }
        if (button == buttonShowCombatSkills) {
            mc.displayGuiScreen(null);
            MythriaPacketHandler.INSTANCE.sendToServer(new CommandPacket(CommandPacketHandler.OPEN_LIFE_COMBAT_GUI));
        }
        if (button == buttonShowAttributes) {
            mc.displayGuiScreen(null);
            MythriaPacketHandler.INSTANCE.sendToServer(new CommandPacket(CommandPacketHandler.OPEN_ATTRIBUTE_GUI));
        }
    }

    @Override
    public void initGui() {
        if (!Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null).getCreated()) {
            buttonList.add(buttonNewProfile = new GuiButton(0, width / 2 - 100, height / 2 - 100, "New Profile"));
            return;
        }
        int pos = 100;
        if (Math.random() < 0.1)
            pos = 99;
        if (Math.random() < 0.1)
            pos = 101;
        buttonList.add(buttonShowProfile = new GuiButton(1, width / 2 - pos, height / 2 - 125, "Show Profile"));
        buttonList.add(buttonShowPersonalities = new GuiButton(1, width / 2 - pos, height / 2 - 100, "Show Personalities"));
        buttonList.add(buttonShowTradeSkills = new GuiButton(2, width / 2 - 100, height / 2 - 75, "Show Trade Skills"));
        buttonList
                .add(buttonShowCombatSkills = new GuiButton(3, width / 2 - 100, height / 2 - 50, "Show Combat Skills"));
        buttonList.add(buttonShowLifeSkills = new GuiButton(4, width / 2 - 100, height / 2 - 25, "Show Life Skills"));
        buttonList.add(buttonShowAttributes = new GuiButton(5, width / 2 - 100, height / 2, "Show Attributes"));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
