package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class AttributeManager {
    public static void spendAttribute(EntityPlayer player, Attribute att, IProfile profile) {
        if(profile.getAttributePoints() <= 0) {
            player.sendMessage(new TextComponentString(MythriaConst.MAIN_COLOR + MythriaConst.NOT_ENOUGH_ATTRIBUTE));
            return;
        }

        if (profile.getAttribute(att) >= 100) {
            player.sendMessage(new TextComponentString(MythriaConst.MAIN_COLOR + MythriaConst.ATTRIBUTE_MAXED));
            return;
        }

        profile.getAttributes().put(att, profile.getAttribute(att) + 1);
        profile.setAttributePoints(profile.getAttributePoints()-1);
        profile.syncToClient();
        StatManager.UpdateStats((EntityPlayerMP) player);
    }
}
