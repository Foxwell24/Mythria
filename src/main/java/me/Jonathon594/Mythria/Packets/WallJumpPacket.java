package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Event.WallJumpEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WallJumpPacket implements IMessage {

    public WallJumpPacket() {
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
    }

    @Override
    public void toBytes(final ByteBuf buf) {
    }

    public void setId(final int id) {
    }

    public static class WallJumpPacketHandler implements IMessageHandler<WallJumpPacket, IMessage> {

        @Override
        public IMessage onMessage(final WallJumpPacket message, final MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                final EntityPlayer player = ctx.getServerHandler().player;
                final WallJumpEvent event = new WallJumpEvent(player);
                MinecraftForge.EVENT_BUS.post(event);
            });
            return null;
        }

    }
}
