package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.GUI.Container.GuiLockedSlot;
import me.Jonathon594.Mythria.GUI.Container.SlotLocked;
import me.Jonathon594.Mythria.Interface.ISlotData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MythriaInventoryManager {

    private static boolean doStackMatch(final ItemStack iStack, final ItemStack item) {
        boolean flag = false;
        if (!iStack.isEmpty() && !item.isEmpty())
            flag = iStack.isItemEqual(item) && ItemStack.areItemsEqual(iStack, item);
        return flag;
    }

    private static int findNextEmptySlot(final EntityPlayer p) {
        final NonNullList<ItemStack> items = p.inventory.mainInventory;
        for (int i = 0; i < items.size(); i++) {
            final ItemStack current = p.inventory.getStackInSlot(i);
            if (current.isEmpty() && isSlotOpen(p, i))
                return i;
        }
        return -1;
    }

    public static boolean isSlotOpen(final EntityPlayer p, final int i) {
        if (p.isCreative()) return true;
        final ArrayList<Integer> freeSlots = getOpenSlots(p);
        if (freeSlots.contains(i))
            return true;
        return i > 35;
    }

    @SideOnly(Side.CLIENT)
    public static void onOpenGui(final GuiOpenEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final EntityPlayer p = mc.player;
        final GuiScreen gui = event.getGui();
        if (event.getGui() instanceof GuiContainer) {
            final GuiContainer guiContainer = (GuiContainer) gui;
            if (!isAtMaxSlots(p)) {
                final List<Slot> slotList = guiContainer.inventorySlots.inventorySlots;
                if (slotList != null && slotList.size() > 0)
                    for (int i = 0; i < slotList.size(); i++) {
                        final Slot slot = guiContainer.inventorySlots.getSlot(i);

                        if (slot != null && slot.isHere(p.inventory, slot.getSlotIndex())) {
                            final InventoryPlayer ip = p.inventory;
                            if (!isSlotOpen(p, slot.getSlotIndex())) {
                                final SlotLocked replacementSlot = new SlotLocked(ip, slot.getSlotIndex(), slot.xPos,
                                        slot.yPos);
                                replacementSlot.slotNumber = slot.slotNumber;
                                guiContainer.inventorySlots.inventorySlots.set(i, replacementSlot);
                            } else {
                                final Slot normalSlot = new Slot(ip, slot.getSlotIndex(), slot.xPos, slot.yPos);
                                normalSlot.slotNumber = slot.slotNumber;
                                guiContainer.inventorySlots.inventorySlots.set(i, normalSlot);
                            }
                        }
                    }
            }
        }
    }

    public static void onPlayerPickupItem(final EntityItemPickupEvent event) {
        if (event.isCanceled())
            return;
        final EntityPlayer p = event.getEntityPlayer();

        if (p.inventory.isEmpty())
            return;
        final EntityItem eItem = event.getItem();
        final ItemStack iStack = eItem.getItem();
        if (iStack.isEmpty())
            return;

        final int slot = searchForPossibleSlot(iStack, p);
        event.setCanceled(slot == -1 || !isSlotOpen(p, slot));
    }

    public static void onPlayerTickEvent(final PlayerTickEvent event) {
        final EntityPlayer p = event.player;

        if (isAtMaxSlots(p))
            return;
        if (p.world.isRemote)
            return;

        final NonNullList<ItemStack> items = p.inventory.mainInventory;
        for (int i = 0; i < items.size(); i++) {
            final ItemStack current = p.inventory.getStackInSlot(i);
            if (current.getCount() > current.getMaxStackSize()) {
                p.entityDropItem(current, 0.0f);
                p.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }
            if (!isSlotOpen(p, i)) {
                final int slot = findNextEmptySlot(p);
                if (slot <= -1) {
                    // SOMETHING
                    p.entityDropItem(current, 0.0f);
                    // something
                    p.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                } else
                    p.inventory.setInventorySlotContents(slot, p.inventory.removeStackFromSlot(i));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onPostScreenEventGui(final Post event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (event.getGui() instanceof GuiContainer) {
            final GuiScreen guiScreen = event.getGui();
            final GuiContainer gui = (GuiContainer) guiScreen;
            if (mc.player != null) {
                final EntityPlayer player = mc.player;
                if (!player.isCreative() && !isAtMaxSlots(player))
                    event.getButtonList().add(new GuiLockedSlot(gui));
            }
        }
    }

    private static boolean isAtMaxSlots(final EntityPlayer p) {
        return getOpenSlots(p).size() == 36 || p.isCreative();
    }

    private static ArrayList<Integer> getOpenSlots(final EntityPlayer p) {
        final ArrayList<Integer> slots = new ArrayList<>();
        final int openSlotsHotbar = 1+getSlotAmounts(p, false);
        for (int i = 0; i < openSlotsHotbar; i++)
            slots.add(i);
        final int openSlotsBody = getSlotAmounts(p, true);
        for (int i = 9; i < openSlotsBody + 9; i++)
            slots.add(i);
        return slots;
    }

    private static int getSlotAmounts(EntityPlayer p, boolean backpack) {
        int i = 0;
        Iterator<ItemStack> iter = p.getArmorInventoryList().iterator();
        while (iter.hasNext()) {
            ItemStack is = iter.next();
            if(is == null || is.isEmpty()) continue;
            i += getEquipSlotsGrantedFromItem(is)[backpack ? 0 : 1];
        }
        return i;
    }

    private static int[] getEquipSlotsGrantedFromItem(final ItemStack item) {
        if (item.getItem().equals(Items.LEATHER_LEGGINGS))
            return new int[]{3, 6};
        if (item.getItem().equals(Items.CHAINMAIL_LEGGINGS))
            return new int[]{0, 4};
        if (item.getItem().equals(Items.IRON_LEGGINGS))
            return new int[]{3, 3};
        if (item.getItem().equals(Items.GOLDEN_LEGGINGS))
            return new int[]{0, 3};
        if (item.getItem().equals(Items.DIAMOND_LEGGINGS))
            return new int[]{3, 3};
        if (item.getItem().equals(Items.LEATHER_CHESTPLATE))
            return new int[]{3, 0};
        if (item.getItem().equals(Items.CHAINMAIL_CHESTPLATE))
            return new int[]{0, 0};
        if (item.getItem().equals(Items.IRON_CHESTPLATE))
            return new int[]{3, 0};
        if (item.getItem().equals(Items.GOLDEN_CHESTPLATE))
            return new int[]{0, 0};
        if (item.getItem().equals(Items.DIAMOND_CHESTPLATE))
            return new int[]{3, 0};
        if (item.getItem().equals(Items.LEATHER_BOOTS))
            return new int[]{0, 0};

        if (item.getItem() instanceof ISlotData) {
            ISlotData data = (ISlotData) item.getItem();
            return new int[] {data.getAdditionalBackpackSlots(), data.getAdditionalHotbarSlots()};
        }
        return new int[] {0,0};
    }

    private static int searchForPossibleSlot(final ItemStack iStack, final EntityPlayer p) {
        final NonNullList<ItemStack> iStacks = p.inventory.mainInventory;
        for (int i = 0; i < iStacks.size(); i++) {
            final ItemStack item = p.inventory.getStackInSlot(i);
            if (isSlotOpen(p, i)) {
                if (item.isEmpty())
                    return i;
                if (doStackMatch(iStack, item) && item.getCount() + iStack.getCount() <= item.getMaxStackSize())
                    return i;
            }
        }
        return -1;
    }
}
