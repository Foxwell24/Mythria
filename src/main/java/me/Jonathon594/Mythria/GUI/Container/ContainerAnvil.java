package me.Jonathon594.Mythria.GUI.Container;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Managers.SmithingManager;
import me.Jonathon594.Mythria.TileEntity.TileEntityAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerAnvil extends Container {

    private final IItemHandler inventory;
    private final TileEntityAnvil anvil;

    public ContainerAnvil(TileEntityAnvil anvil, InventoryPlayer inventoryPlayer) {
        inventory = anvil.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.anvil = anvil;
        this.anvil.setResultItem(-1);
        this.addSlotToContainer(new AnvilIngotSlot(inventory, 0, 87, 46));
        this.addSlotToContainer(new AnvilResultSlot(inventory, 1, 105, 46));
        this.addSlotToContainer(new AnvilIngotSlot(inventory, 2, 14, 12));
        this.addSlotToContainer(new AnvilIngotSlot(inventory, 3, 32, 12));
        this.addSlotToContainer(new OutputSlot(inventory, 4, 23, 34));
        this.addSlotToContainer(new AnvilHammerSlot(inventory, 5, 7, 95));
        this.addSlotToContainer(new OutputSlot(inventory, 6, 185, 95));

        int row;
        int col;

        for (row = 0; row < 9; ++row) {
            this.addSlotToContainer(new AnvilSlotSelect(inventory, row + 7, 24 + row * 18, 119));
        }

        for (col = 0; col < 2; ++col) {
            for (row = 0; row < 3; ++row) {
                if(col == 0) {
                    this.addSlotToContainer(new AnvilSlot(inventory, row + col * 3 + 16, 160 + col * 18, 12 + row * 18));
                } else {
                    this.addSlotToContainer(new AnvilResultSlot(inventory, row + col * 3 + 16, 160 + col * 18, 12 + row * 18));
                }
            }
        }

        for (row = 0; row < 9; ++row) {
            this.addSlotToContainer(new Slot(inventoryPlayer, row, 24 + row * 18, 200));
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col)
                this.addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, 24 + col * 18, 142 + row * 18));

        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);

        anvil.reset();
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if(slotId > 6 && slotId < 16) {
            if(player.world.isRemote || inventory.getStackInSlot(slotId) == null) return super.slotClick(slotId, dragType, clickTypeIn, player);
            IProfile profile = player.getCapability(ProfileProvider.PROFILE_CAP, null);
            SmithingManager.SmithingRecipe recipe = SmithingManager.getRecipe(inventory.getStackInSlot(slotId).getItem());
            if(recipe.getRequiredFlag() != null && profile.hasFlag(recipe.getRequiredFlag())) {
                anvil.setResultItem(slotId - 7);
            } else {
                player.sendMessage(new TextComponentString(MythriaConst.NO_PERK));
            }
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
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

        if (slot != null && slot.getHasStack())
        {
            ItemStack copy = slot.getStack();
            original = copy.copy();

            if (index > 21)
            {
                if (this.inventorySlots.get(0).isItemValid(copy) ){
                    if (!this.mergeItemStack(copy, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.inventorySlots.get(5).isItemValid(copy) ) {
                    if (!this.mergeItemStack(copy, 5, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(index < 30) {
                        if (!this.mergeItemStack(copy, 30, 58, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 21, 30, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy,21, 58, true))
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
        return original;
    }

    public TileEntityAnvil getAnvil() {
        return anvil;
    }
}

