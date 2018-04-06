package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.GUI.Container.ContainerAnvil;
import me.Jonathon594.Mythria.TileEntity.TileEntityAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketAnvilButton implements IMessage{
    public int getIndex() {
        return index;
    }

    private int index;

    public CPacketAnvilButton() {

    }

    public CPacketAnvilButton(int index) {
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(index);
    }

    public static class CPacketAnvilButtonHandler implements IMessageHandler<CPacketAnvilButton, IMessage> {

        @Override
        public IMessage onMessage(CPacketAnvilButton message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            Container container = player.openContainer;
            if(container == null || !(container instanceof ContainerAnvil)) return null;
            ContainerAnvil anvilContainer = (ContainerAnvil) container;
            TileEntityAnvil tea = anvilContainer.getAnvil();
            tea.buttonPressed(message.getIndex(), true, ctx.getServerHandler().player);
            return null;
        }
    }
}
