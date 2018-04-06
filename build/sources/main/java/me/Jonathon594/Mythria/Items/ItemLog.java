package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Enum.Wood;
import me.Jonathon594.Mythria.Interface.IMetaLookup;
import me.Jonathon594.Mythria.Interface.IVariantData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ItemLog extends MythriaItem implements IVariantData {
    public ItemLog(String name, double weight) {
        super(name,4, weight);
        setMaxDamage(0);
        this.hasSubtypes = true;
        this.setMaxDamage(0);
        addPropertyOverride(new ResourceLocation("type"), new SubItemPropertyGetter());
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 6; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + getDefaultVariant().getByOrdinal(stack.getItemDamage()).name().toLowerCase();
    }

    @Override
    public IMetaLookup getDefaultVariant() {
        return Wood.OAK;
    }
}
