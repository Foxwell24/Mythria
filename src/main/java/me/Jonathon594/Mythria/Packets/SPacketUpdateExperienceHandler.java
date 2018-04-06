package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketUpdateExperienceHandler implements IMessageHandler<SPacketUpdateExperience, IMessage> {

    @Override
    public IMessage onMessage(final SPacketUpdateExperience message, final MessageContext ctx) {
        final IProfile profile = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.getSkillLevels().put(message.getType(), message.getValue());
        return null;
    }

}
