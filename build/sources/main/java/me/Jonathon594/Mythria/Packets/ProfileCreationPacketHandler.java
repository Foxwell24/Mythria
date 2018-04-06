package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Managers.StatManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ProfileCreationPacketHandler implements IMessageHandler<ProfileCreationPacket, IMessage> {

    @Override
    public IMessage onMessage(final ProfileCreationPacket message, final MessageContext ctx) {
        final EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        final IProfile profile = serverPlayer.getCapability(ProfileProvider.PROFILE_CAP, null);

        if (profile.getCreated()) {
            serverPlayer.sendMessage(new TextComponentString(MythriaConst.ALREADY_PROFILE));
            return null;
        }

        profile.fromNBT(message.getToSend());
        profile.setPlayer(serverPlayer);

        StatManager.applyInitialStats(profile);
        profile.setCreated(true);
        return new ClientProfileDataCachePacket(profile.toNBT());
    }

}
