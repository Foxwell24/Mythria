package me.Jonathon594.Mythria.Module;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Interface.ILightable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;

import java.util.Random;

public class FireMakingModule {
    public static boolean TryLight(final EntityPlayer player, final EnumHand hand, ILightable lightable) {
        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        final Item i = player.getHeldItemMainhand().getItem();
        if (!i.equals(Items.FLINT_AND_STEEL) && !i.equals(Items.STICK)) return false;
        if(!lightable.hasFuel()) {
            player.sendMessage(new TextComponentString(MythriaConst.FIREMAKING_NO_FUEL));
            return true;
        }
        if (i.equals(Items.FLINT_AND_STEEL)) {
            player.getHeldItemMainhand().attemptDamageItem(1, new Random(), (EntityPlayerMP) player);
            lightable.light();
            return true;
        }
        if (i.equals(Items.STICK)) {
            if (!p.hasFlag(AttributeFlag.FIREMAKING1)) {
                player.sendMessage(new TextComponentString(MythriaConst.NO_PERK));
                return true;
            }
            if (Math.random() < 0.2) {
                player.getHeldItemMainhand().shrink(1);
                player.sendMessage(new TextComponentString(MythriaConst.FIREMAKING_STICK_BROKEN));
            } else
                lightable.addFriction();
            return true;
        }
        return false;
    }
}
