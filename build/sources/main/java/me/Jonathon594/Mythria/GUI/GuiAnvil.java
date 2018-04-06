package me.Jonathon594.Mythria.GUI;

import me.Jonathon594.Mythria.GUI.Container.ContainerAnvil;
import me.Jonathon594.Mythria.Managers.SmithingManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.CPacketAnvilButton;
import me.Jonathon594.Mythria.TileEntity.TileEntityAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;

public class GuiAnvil extends GuiContainer {

    private final ResourceLocation gui_tex;
    private final TileEntityAnvil anvil;
    private final ContainerAnvil container;

    private GuiButton weldButton;
    private GuiButton smithHitLight;
    private GuiButton smithHitMedium;
    private GuiButton smithHitHeavy;
    private GuiButton smithDraw;
    private GuiButton smithPunch;
    private GuiButton smithBend;
    private GuiButton smithUpset;
    private GuiButton smithShrink;

    private ArrayList<GuiButton> planButtons;
    private final ResourceLocation anvil_icons;

    public GuiAnvil(final ContainerAnvil container, final TileEntityAnvil anvil) {
        super(container);
        this.container = container;
        gui_tex = new ResourceLocation("mythria:textures/gui/container/gui_anvil.png");
        anvil_icons = new ResourceLocation("mythria:textures/gui/container/anvilicons.png");
        this.anvil = anvil;
        xSize = 208;
        ySize = 224;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.equals(smithHitLight)) {
            hitSmithButton(SmithingManager.EnumHitType.SOFT_HIT);
        }
        if(button.equals(smithHitMedium)) {
            hitSmithButton(SmithingManager.EnumHitType.MEDIUM_HIT);
        }
        if(button.equals(smithHitHeavy)) {
            hitSmithButton(SmithingManager.EnumHitType.HEAVY_HIT);
        }
        if(button.equals(smithDraw)) {
            hitSmithButton(SmithingManager.EnumHitType.DRAW);
        }
        if(button.equals(smithPunch)) {
            hitSmithButton(SmithingManager.EnumHitType.PUNCH);
        }
        if(button.equals(smithBend)) {
            hitSmithButton(SmithingManager.EnumHitType.BEND);
        }
        if(button.equals(smithUpset)) {
            hitSmithButton(SmithingManager.EnumHitType.UPSET);
        }
        if(button.equals(smithShrink)) {
            hitSmithButton(SmithingManager.EnumHitType.SHRINK);
        }
        if(button.equals(weldButton)) {
            MythriaPacketHandler.INSTANCE.sendToServer(new CPacketAnvilButton(8));
            anvil.buttonPressed(8, false, null);
        }
    }

    private void hitSmithButton(SmithingManager.EnumHitType type) {
        MythriaPacketHandler.INSTANCE.sendToServer(new CPacketAnvilButton(type.ordinal()));
        anvil.buttonPressed(type.ordinal(), false, null);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(weldButton = new GuiButton(0, guiLeft + 13, guiTop + 53, 36, 20, "WELD"));
        this.buttonList.add(smithHitLight = new SmithButton(1, guiLeft + 69, guiTop + 64, 16, 16, SmithingManager.EnumHitType.SOFT_HIT));
        this.buttonList.add(smithHitMedium = new SmithButton(2, guiLeft + 87, guiTop + 64, 16, 16, SmithingManager.EnumHitType.MEDIUM_HIT));
        this.buttonList.add(smithHitHeavy = new SmithButton(3, guiLeft + 69, guiTop + 82, 16, 16, SmithingManager.EnumHitType.HEAVY_HIT));
        this.buttonList.add(smithDraw = new SmithButton(4, guiLeft + 87, guiTop + 82, 16, 16, SmithingManager.EnumHitType.DRAW));

        this.buttonList.add(smithPunch = new SmithButton(5, guiLeft + 105, guiTop + 64, 16, 16, SmithingManager.EnumHitType.PUNCH));
        this.buttonList.add(smithBend = new SmithButton(6, guiLeft + 123, guiTop + 64, 16, 16, SmithingManager.EnumHitType.BEND));
        this.buttonList.add(smithUpset = new SmithButton(7, guiLeft + 105, guiTop + 82, 16, 16, SmithingManager.EnumHitType.UPSET));
        this.buttonList.add(smithShrink = new SmithButton(8, guiLeft + 123, guiTop + 82, 16, 16, SmithingManager.EnumHitType.SHRINK));
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        mc.getTextureManager().bindTexture(gui_tex);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        SmithingManager.SmithingRecipe recipe = anvil.getCurrentRecipe();
        SmithingManager.EnumHitType[] hitsForRecipe = new SmithingManager.EnumHitType[3];
        if(recipe != null) {
            hitsForRecipe = SmithingManager.getRequiredFinalHitsForMetalShape(recipe.getOutputShape());
        }

        SmithingManager.EnumHitType[] currentHits = anvil.getCurrentHits();

        if(anvil.getMetal() == null) return;
        if(anvil.getCurrentRecipe() == null) return;

        for(int i = 0; i < 3; i++) {
            SmithingManager.EnumHitType hit = currentHits[i];
            if(hit != null) {
                drawHitIcon(77 + (19 * i), 7, hit, anvil_icons, 0.5f, 1.0f, 0.5f);
            }

            SmithingManager.EnumHitType hit2 = hitsForRecipe[i];
            if(hit2 == null) continue;
            drawHitIcon(77 + (19 * i), 28, hit2, anvil_icons, 1.0f, 1.0f, 0.5f);
        }

        mc.getTextureManager().bindTexture(gui_tex);
        if(anvil.getMetal()!= null) {
            drawTexturedModalRect((int) (26 + (178 - 26) * anvil.getMetal().getSmithingProgress2()), 103, 213, 10, 5, 6);
            drawTexturedModalRect((int) (26 + (178 - 26) * anvil.getMetal().getSmithingProgress1()), 108, 208, 10, 5, 6);
        }
    }

    public class SmithButton extends GuiButton {
        private SmithingManager.EnumHitType hitType;

        public SmithButton(int buttonId, int x, int y, int widthIn, int heightIn, SmithingManager.EnumHitType hitType) {
            super(buttonId, x, y, widthIn, heightIn, "");
            this.hitType = hitType;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            GuiAnvil.drawHitIcon(x, y, hitType, anvil_icons, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void drawRectWithSizeAndUV(int x, int y, int height, int width, double u, double v, double vSize, double uSize) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

        bufferbuilder.pos((double) x + 1, (double) (y + height - 1), 0.0D)
                .tex(u, v + vSize).endVertex();

        bufferbuilder.pos((double) (x + width - 1), (double) (y + height - 1), 0.0D)
                .tex(u + uSize, v + vSize).endVertex();

        bufferbuilder.pos((double) (x + width - 1), (double) y + 1, 0.0D)
                .tex(u + uSize, v).endVertex();

        bufferbuilder.pos((double) x + 1, (double) y + 1, 0.0D)
                .tex(u, v).endVertex();

        tessellator.draw();
    }

    private static void drawHitIcon(int x, int y, SmithingManager.EnumHitType hitType, ResourceLocation texture, float r, float g, float b) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        double u = 0;
        double v = 0;
        switch (hitType) {
            case SOFT_HIT:
                u = 0;
                v = 0;
                break;
            case MEDIUM_HIT:
                u = 0.25;
                v = 0;
                break;
            case HEAVY_HIT:
                u = 0.5;
                v = 0;
                break;
            case DRAW:
                u = 0.75;
                v = 0;
                break;
            case PUNCH:
                u = 0;
                v = 0.5;
                break;
            case BEND:
                u = 0.25;
                v = 0.5;
                break;
            case UPSET:
                u = 0.5;
                v = 0.5;
                break;
            case SHRINK:
                u = 0.75;
                v = 0.5;
                break;
        }

        GlStateManager.color(r, g, b, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        drawRectWithSizeAndUV(x, y, 16, 16, u, v, 0.5, 0.25);
    }
}
