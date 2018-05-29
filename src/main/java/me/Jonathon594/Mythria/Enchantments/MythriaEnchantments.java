package me.Jonathon594.Mythria.Enchantments;

import me.Jonathon594.Mythria.Items.MythriaItemHammer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class MythriaEnchantments {
    public static final EnumEnchantmentType HAMMER = EnumHelper.addEnchantmentType("hammer", (item) -> item instanceof MythriaItemHammer);
    public static final EnumEnchantmentType THROWABLE = EnumHelper.addEnchantmentType("throwable", (item) -> item instanceof ItemTool);

    public static final List<Enchantment> ENCHANTMENT_LIST = new ArrayList<>();

    public static final Enchantment STRIKING = addEnchantment(new EnchantmentStriking(), "striking");
    public static final Enchantment RETURNING = addEnchantment(new EnchantmentReturning(), "returning");
    public static final Enchantment EXPLODING = addEnchantment(new EnchantmentExploding(), "exploding");

    private static Enchantment addEnchantment(Enchantment enchantment, String name) {
        ENCHANTMENT_LIST.add(enchantment);
        return enchantment.setRegistryName(name).setName(name);
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        for(Enchantment enchantment: ENCHANTMENT_LIST)
            event.getRegistry().register(enchantment);
    }
}
