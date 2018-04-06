package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Interface.ISlotData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class MythriaItemArmor extends ItemArmor implements IItemData, ISlotData {
    private double weight;
    private int backpackSlots;
    private int hotbarSlots;

    public MythriaItemArmor(final String name, final ArmorMaterial mat, final int renderIndexIn,
                            final EntityEquipmentSlot equipmentSlotIn, double weight, int backpackSlots, int hotbarSlots) {
        super(mat, renderIndexIn, equipmentSlotIn);
        this.weight = weight;
        this.backpackSlots = backpackSlots;
        this.hotbarSlots = hotbarSlots;
        setMaxStackSize(1);
        setUnlocalizedName(name);
        setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.COMBAT);

        MythriaItems.addItem(this);
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getAdditionalBackpackSlots() {
        return backpackSlots;
    }

    @Override
    public int getAdditionalHotbarSlots() {
        return hotbarSlots;
    }
}
