package me.Jonathon594.Mythria.Enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentReturning extends Enchantment {
    protected EnchantmentReturning() {
        super(Rarity.RARE, MythriaEnchantments.THROWABLE, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 40;
    }
}
