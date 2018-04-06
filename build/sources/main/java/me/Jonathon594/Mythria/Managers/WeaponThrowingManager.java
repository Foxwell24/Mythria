package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Entity.EntityThrowingWeapon;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.ThrowWeaponPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class WeaponThrowingManager {

    public static void onPacketReceived(final EntityPlayerMP player) {
        final EntityThrowingWeapon tw = getThrowingWeaponEntity(player);
        if (tw == null)
            return;
        tw.shoot(player, player.rotationPitch, player.rotationYaw, 0.0f, 1.5f, 0.1f);
        player.world.spawnEntity(tw);
        player.getHeldItemMainhand().shrink(1);
    }

    private static EntityThrowingWeapon getThrowingWeaponEntity(final EntityPlayerMP player) {
        double damage = 0;
        final ItemStack item = player.getHeldItemMainhand();
        if(item.getItem() instanceof ItemBlock) return null;
        for (final AttributeModifier e : item.getAttributeModifiers(EntityEquipmentSlot.MAINHAND)
                .get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()))
            damage += e.getAmount();
        final EntityThrowingWeapon tw = new EntityThrowingWeapon(player.world, player);
        ItemStack is = item.copy();
        if(!(is.getItem() instanceof ItemTool) && !(is.getItem() instanceof ItemSword)) {
            return null;
        }
        is.setCount(1);
        tw.setItem(is);
        tw.setDamage((int) damage);

        tw.getItem();
        return tw;
    }

    public static void throwWeapon() {
        if (Minecraft.getMinecraft().player.isSwingInProgress)
            return;
        MythriaPacketHandler.INSTANCE.sendToServer(new ThrowWeaponPacket());
    }

}
