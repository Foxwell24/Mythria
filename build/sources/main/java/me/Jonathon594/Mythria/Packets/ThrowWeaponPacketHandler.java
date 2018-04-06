package me.Jonathon594.Mythria.Packets;

import me.Jonathon594.Mythria.Managers.WeaponThrowingManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ThrowWeaponPacketHandler implements IMessageHandler<ThrowWeaponPacket, IMessage> {

    @Override
    public IMessage onMessage(final ThrowWeaponPacket message, final MessageContext ctx) {
        final EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> {
            WeaponThrowingManager.onPacketReceived(player);
        });
        return null;
    }

}
