package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketUpdateAttributeHandler implements IMessageHandler<SPacketUpdateAttribute, IMessage> {

    @Override
    public IMessage onMessage(final SPacketUpdateAttribute message, final MessageContext ctx) {
        final IProfile profile = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.getAttributes().put(message.getStat(), message.getValue());
        return null;
    }

}
