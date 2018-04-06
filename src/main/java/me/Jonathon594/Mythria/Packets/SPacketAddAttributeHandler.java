package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketAddAttributeHandler implements IMessageHandler<SPacketAddAttribute, IMessage> {

    @Override
    public IMessage onMessage(final SPacketAddAttribute message, final MessageContext ctx) {
        final Minecraft mc = Minecraft.getMinecraft();
        final IProfile profile = mc.player.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.addAttribute(message.getAttribute());

        MythriaUtil.updateClientGuiScreens();
        return null;
    }

}
