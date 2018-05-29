package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.AttributeType;
import me.Jonathon594.Mythria.Managers.PerkManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PerkMenuGuiContainer extends Container {

    private final InventoryBasic inventory;
    private final AttributeType type;
    private final EntityPlayer opener;

    public PerkMenuGuiContainer(final InventoryBasic inventory, final AttributeType type, final EntityPlayer player) {
        for (int y = 0; y < 6; y++)
            for (int x = 0; x < 9; x++)
                addSlotToContainer(new Slot(inventory, x + y * 9, 8 + x * 18, 18 + y * 18));

        this.inventory = inventory;
        this.type = type;
        opener = player;
        setContents();
    }

    public void setContents() {
        final List<Perk> attributes = new ArrayList<>();
        switch (type) {
            case COMBAT:
                attributes.addAll(PerkManager.getCombatList());
                break;
            case LIFE:
                attributes.addAll(PerkManager.getLifeList());
                break;
            case TRADE:
                attributes.addAll(PerkManager.getTradeList());
                break;
            case PERSONALITY:
                attributes.addAll(PerkManager.getPersonalityList());
                break;
            case MAGIC:
                attributes.addAll(PerkManager.getMagicList());
                break;
            default:
                break;
        }

        for (final Perk pa : attributes) {
            final int i = (pa.getMenuY() - 1) * 9 + pa.getMenuX() - 1;
            inventory.setInventorySlotContents(i, pa.getAttributeItem(opener));
            // inventory.setInventorySlotContents(i, ItemStack.EMPTY);
        }
    }

    @Override
    public ItemStack slotClick(final int slotId, final int dragType, final ClickType clickTypeIn,
                               final EntityPlayer player) {
        if (slotId == -999)
            return new ItemStack(Items.AIR);
        if (player.getEntityWorld().isRemote)
            return new ItemStack(Items.AIR);

        final Perk pa = PerkManager.getAttribute(getInventory().get(slotId).getDisplayName());
        PerkManager.AttemptBuy(player, pa);
        setContents();

        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        // TODO Auto-generated method stub
        return true;
    }

}
