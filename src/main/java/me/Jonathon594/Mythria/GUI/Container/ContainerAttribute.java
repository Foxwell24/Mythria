package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.AttributeType;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Managers.AttributeManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public class ContainerAttribute extends Container {

    private final InventoryBasic inventory;
    private final EntityPlayer opener;

    public ContainerAttribute(final InventoryBasic inventory, final EntityPlayer player) {
        addSlotToContainer(new Slot(inventory, 0, 69, 15));
        addSlotToContainer(new Slot(inventory, 1, 91, 15));
        addSlotToContainer(new Slot(inventory, 2, 99, 37));
        addSlotToContainer(new Slot(inventory, 3, 91, 59));
        addSlotToContainer(new Slot(inventory, 4, 69, 59));
        addSlotToContainer(new Slot(inventory, 5, 61, 37));


        this.inventory = inventory;
        opener = player;
        setContents();
    }

    public void setContents() {
        IProfile profile = opener.getCapability(ProfileProvider.PROFILE_CAP, null);

        for(int i = 0; i < 6; i++) {
            Attribute att = Attribute.values()[i];
            int a = profile.getAttribute(att);
            Item icon = getIconForAttribute(att);

            ItemStack is = new ItemStack(icon, 1);
            is.setStackDisplayName(TextFormatting.GREEN + MythriaUtil.Capitalize(att.name()));
            ArrayList<String> lines = new ArrayList<>();
            lines.add(MythriaConst.CONT_COLOR + "Current Value: " + a + " / 100");
            lines.add(MythriaConst.CONT_COLOR + "Current Attribute Points: " + profile.getAttributePoints());
            lines.add(MythriaConst.MAIN_COLOR + getHelpTextForAttribute(att));
            MythriaUtil.addLoreToItemStack(is, lines);
            inventory.setInventorySlotContents(i, is);
        }
    }

    private String getHelpTextForAttribute(Attribute att) {
        switch(att) {
            case VIGOR:
                return "Increases health.";
            case STRENGTH:
                return "Increases carry weight and melee damage.";
            case DEXTERITY:
                return "Increases movement speed and damage with certain weapons.";
            case ENDURANCE:
                return "Increases stamina.";
            case INTELLIGENCE:
                return "Increases mana.";
            case ATTUNEMENT:
                return "Increases mana regeneration.";
        }
        return "";
    }

    private Item getIconForAttribute(Attribute att) {
        switch(att) {
            case VIGOR:
                return Items.GOLDEN_APPLE;
            case STRENGTH:
                return MythriaItems.IRON_HAMMER;
            case DEXTERITY:
                return Items.BOW;
            case ENDURANCE:
                return Items.IRON_CHESTPLATE;
            case INTELLIGENCE:
                return Items.WRITABLE_BOOK;
            case ATTUNEMENT:
                return Items.ENDER_PEARL;
        }
        return null;
    }

    @Override
    public ItemStack slotClick(final int slotId, final int dragType, final ClickType clickTypeIn,
                               final EntityPlayer player) {
        if (slotId == -999)
            return new ItemStack(Items.AIR);
        if (player.getEntityWorld().isRemote)
            return new ItemStack(Items.AIR);
        if(!clickTypeIn.equals(ClickType.PICKUP)){
            return new ItemStack(Items.AIR);
        }

        IProfile profile = opener.getCapability(ProfileProvider.PROFILE_CAP, null);
        Attribute att = Attribute.valueOf(TextFormatting.getTextWithoutFormattingCodes(getInventory().get(slotId).getDisplayName().toUpperCase()));
        AttributeManager.spendAttribute(player, att, profile);

        setContents();

        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return true;
    }

}
