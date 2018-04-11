package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Managers.CarpentryManager;
import me.Jonathon594.Mythria.TileEntity.TileEntitySawHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerSawhorse extends Container {

    private final IItemHandler inventory;
    private final TileEntitySawHorse sawHorse;

    public ContainerSawhorse(TileEntitySawHorse sawHorse, InventoryPlayer inventoryPlayer) {
        inventory = sawHorse.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.sawHorse = sawHorse;
        this.addSlotToContainer(new SawhorseWoodSlot(inventory, 0, 87, 12));
        this.addSlotToContainer(new SawhotseSawSlot(inventory, 1, 105, 12));
        this.addSlotToContainer(new OutputSlot(inventory, 2, 96, 34));

        int row;
        int col;

        for (row = 0; row < 9; ++row) {
            this.addSlotToContainer(new Slot(inventoryPlayer, row, 24 + row * 18, 140));
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col)
                this.addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, 24 + col * 18, 82 + row * 18));

        }
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if(slotId == 2) {
            takeResult(player);
        }

        ItemStack is = super.slotClick(slotId, dragType, clickTypeIn, player);

        inputChanged(player);

        return is;
    }

    private void inputChanged(EntityPlayer player) {
        ItemStack input = inventory.getStackInSlot(0);
        ItemStack saw = this.inventorySlots.get(1).getStack();
        if(input != null && input != ItemStack.EMPTY && !input.getItem().equals(Items.AIR) &&
            saw != null && saw != ItemStack.EMPTY && !saw.getItem().equals(Items.AIR)) {
                IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
                CarpentryManager.SawResult result = CarpentryManager.getSawResult(input);
                if (result != null && profile.hasFlag(AttributeFlag.CARPENTRY)) {
                    ItemStack output = result.getOutput();
                    this.inventorySlots.get(2).putStack(output);
                }
        } else {
            this.inventorySlots.get(2).putStack(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(index == 2) {
            takeResult(playerIn);
        }

        if (slot != null && slot.getHasStack())
        {
            ItemStack copy = slot.getStack();
            original = copy.copy();

            if (index > 2)
            {
                if (this.inventorySlots.get(0).isItemValid(copy) ) {
                    if (!this.mergeItemStack(copy, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.inventorySlots.get(1).isItemValid(copy) ){
                    if (!this.mergeItemStack(copy, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(index < 16) {
                        if (!this.mergeItemStack(copy, 12, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 3, 12, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy,3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (copy.getCount() == 0)
            {
                slot.putStack((ItemStack)ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (copy.getCount() == original.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, copy);
        }

        inputChanged(playerIn);
        return original;
    }

    private void takeResult(EntityPlayer player) {
        ItemStack saw = this.inventorySlots.get(1).getStack();
        ItemStack output = this.inventorySlots.get(2).getStack();
        if(output == null || output.equals(ItemStack.EMPTY) || output.getItem().equals(Items.AIR)) return;
        this.inventorySlots.get(0).getStack().shrink(1);
        getSawHorse().damageSaw(saw, output.getCount());
        getSawHorse().addPlayerXP(player, output.getCount());
    }

    public TileEntitySawHorse getSawHorse() {
        return sawHorse;
    }
}

