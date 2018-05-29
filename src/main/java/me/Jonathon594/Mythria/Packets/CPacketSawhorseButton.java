package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.GUI.Container.ContainerSawhorse;
import me.Jonathon594.Mythria.TileEntity.TileEntitySawHorse;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketSawhorseButton implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class CPacketSawhorseButtonHandler implements IMessageHandler<CPacketSawhorseButton, IMessage> {

        @Override
        public IMessage onMessage(CPacketSawhorseButton message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                Container container = player.openContainer;
                if (container == null || !(container instanceof ContainerSawhorse)) return;
                ContainerSawhorse sawHorseContainer = (ContainerSawhorse) container;
                TileEntitySawHorse tea = sawHorseContainer.getSawHorse();
                tea.buttonPressed(ctx.getServerHandler().player);
            });
            return null;
        }
    }
}
