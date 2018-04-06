package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketAddHealthConditionInstanceHandler
        implements IMessageHandler<SPacketAddHealthConditionInstance, IMessage> {

    @Override
    public IMessage onMessage(final SPacketAddHealthConditionInstance message, final MessageContext ctx) {
        final Minecraft mc = Minecraft.getMinecraft();
        final IProfile profile = mc.player.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.addHealthCondition(message.getAttribute());
        return null;
    }

}
