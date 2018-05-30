package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.DataTypes.Time.Date;
import me.Jonathon594.Mythria.DataTypes.Time.Month;
import me.Jonathon594.Mythria.GUI.FormatHelper.AgeFormatHelper;
import me.Jonathon594.Mythria.GUI.FormatHelper.DayFormatHelper;
import me.Jonathon594.Mythria.GUI.FormatHelper.GuiFormatHelper;
import me.Jonathon594.Mythria.GUI.FormatHelper.MonthFormatHelper;
import me.Jonathon594.Mythria.GUI.Responder.AgeSliderGuiResponder;
import me.Jonathon594.Mythria.GUI.Responder.DaySliderGuiResponder;
import me.Jonathon594.Mythria.GUI.Responder.GenderSliderGuiResponder;
import me.Jonathon594.Mythria.GUI.Responder.MonthSliderGuiResponder;
import me.Jonathon594.Mythria.Managers.TimeManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.CPacketProfileCreation;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

public class MythriaProfileCreationGui extends GuiScreen {
    private GuiTextField txtFirstName;
    private GuiTextField txtMiddleName;
    private GuiTextField txtLastName;
    private GuiSlider sldMonth;
    private GuiSlider sldDay;
    private GuiSlider sldAge;
    private GuiSlider sldGender;
    private GuiButton btnCreate;

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        drawDefaultBackground();

        txtFirstName.drawTextBox();
        txtMiddleName.drawTextBox();
        txtLastName.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(final char par1, final int par2) throws IOException {
        super.keyTyped(par1, par2);
        txtFirstName.textboxKeyTyped(par1, par2);
        txtMiddleName.textboxKeyTyped(par1, par2);
        txtLastName.textboxKeyTyped(par1, par2);
    }

    @Override
    protected void mouseClicked(final int x, final int y, final int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        txtFirstName.mouseClicked(x, y, btn);
        txtMiddleName.mouseClicked(x, y, btn);
        txtLastName.mouseClicked(x, y, btn);
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button == btnCreate) {
            final String fName = MythriaUtil.Capitalize(txtFirstName.getText());
            final String mName = MythriaUtil.Capitalize(txtMiddleName.getText());
            final String lName = MythriaUtil.Capitalize(txtLastName.getText());

            final int month = Math.round(sldMonth.getSliderValue());
            int day = Math.round(sldDay.getSliderValue());
            final int gender = Math.round(sldGender.getSliderValue());
            final int age = Math.round(sldAge.getSliderValue());

            if (fName.length() < 5 || mName.length() < 5 || lName.length() < 5) {
                mc.player.sendMessage(new TextComponentString(MythriaConst.NAME_SHORT));
                return;
            }
            if (fName.contains(" ") || mName.contains(" ") || lName.contains(" ")) {
                mc.player.sendMessage(new TextComponentString(MythriaConst.NAMES_NO_SPACES));
                return;
            }

            final Month m = TimeManager.getMonths().get(month);
            if (day > m.getLength())
                day = m.getLength();

            final Date d = MythriaUtil.getDateFromAgeMonthDay(age, month, day);

            final IProfile p = new Profile();
            p.setFirstName(fName);
            p.setMiddleName(mName);
            p.setLastName(lName);
            p.setBirthDay(d);
            p.setGender(gender);
            mc.displayGuiScreen(null);

            MythriaPacketHandler.INSTANCE.sendToServer(new CPacketProfileCreation(p.toNBT()));
        }
    }

    @Override
    public void initGui() {
        txtFirstName = new GuiTextField(0, fontRenderer, width / 2 - 100, height / 2 - 100, 200, 20);
        txtFirstName.setMaxStringLength(10);
        txtFirstName.setText("First");
        txtFirstName.setFocused(true);

        txtMiddleName = new GuiTextField(1, fontRenderer, width / 2 - 100, height / 2 - 75, 200, 20);
        txtMiddleName.setMaxStringLength(10);
        txtMiddleName.setText("Middle");

        txtLastName = new GuiTextField(2, fontRenderer, width / 2 - 100, height / 2 - 50, 200, 20);
        txtLastName.setMaxStringLength(10);
        txtLastName.setText("Last");

        sldMonth = new GuiSlider(new MonthSliderGuiResponder(), 3, width / 2 - 100, height / 2 - 25, "Month", 0, 11, 0,
                new MonthFormatHelper());
        sldDay = new GuiSlider(new DaySliderGuiResponder(), 4, width / 2 - 100, height / 2, "Day", 1, 31, 1,
                new DayFormatHelper());
        sldGender = new GuiSlider(new GenderSliderGuiResponder(), 5, width / 2 - 100, height / 2 + 25, "Gender", 0, 1,
                0, new GuiFormatHelper());
        sldAge = new GuiSlider(new AgeSliderGuiResponder(), 6, width / 2 - 100, height / 2 + 50, "Age", 12, 38, 12,
                new AgeFormatHelper());

        sldMonth.setWidth(200);
        sldDay.setWidth(200);
        sldGender.setWidth(200);
        sldAge.setWidth(200);

        buttonList.add(sldMonth);
        buttonList.add(sldDay);
        buttonList.add(sldGender);
        buttonList.add(sldAge);

        buttonList.add(btnCreate = new GuiButton(7, width / 2 - 100, height / 2 + 75, 200, 20, "Create Profile"));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        txtFirstName.updateCursorCounter();
        txtMiddleName.updateCursorCounter();
        txtLastName.updateCursorCounter();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
