package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketAddExperienceHandler implements IMessageHandler<CPacketAddExperience, IMessage> {

    @Override
    public IMessage onMessage(final CPacketAddExperience message, final MessageContext ctx) {
        final IProfile profile = ctx.getServerHandler().player.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.addSkillExperience(message.getType(), message.getValue(), ctx.getServerHandler().player);
        return null;
    }

}
