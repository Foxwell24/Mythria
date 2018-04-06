package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketUpdateConsumablesHandler implements IMessageHandler<SPacketUpdateConsumables, IMessage> {

    @Override
    public IMessage onMessage(final SPacketUpdateConsumables message, final MessageContext ctx) {
        final IProfile profile = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
        profile.setConsumable(message.getConsumable(), message.getValue());
        return null;
    }

}
