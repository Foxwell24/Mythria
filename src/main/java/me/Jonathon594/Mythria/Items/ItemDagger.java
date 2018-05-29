package me.Jonathon594.Mythria.Items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemDagger extends MythriaItemSword implements IItemData {

    private double weight;

    public ItemDagger(final String name, final ToolMaterial material, double weight) {
        super(name, material, weight);
        this.weight = weight;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(final EntityEquipmentSlot equipmentSlot) {
        final Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getAttackDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1, 0));
        }

        return multimap;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
