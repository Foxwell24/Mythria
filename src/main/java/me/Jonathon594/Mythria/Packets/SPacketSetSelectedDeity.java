package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketSetSelectedDeity implements IMessage {

    private Integer entityID;

    public Integer getEntityID() {
        return entityID;
    }

    public Integer getDeityIndex() {
        return deityIndex;
    }

    private Integer deityIndex;

    public SPacketSetSelectedDeity(Integer entityID, Integer deityIndex) {
        this.entityID = entityID;
        this.deityIndex = deityIndex;
    }

    public SPacketSetSelectedDeity() {

    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        entityID = buf.readInt();
        deityIndex = buf.readInt();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeInt(deityIndex);
    }

    public static class SPacketSetSelectedDeityHandler implements IMessageHandler<SPacketSetSelectedDeity, IMessage> {

        @Override
        public IMessage onMessage(SPacketSetSelectedDeity message, MessageContext ctx) {
            DeityManager.setDeity(message.getEntityID(), message.getDeityIndex() == -1 ? null : Deity.values()[message.getDeityIndex()], false);
            return null;
        }
    }
}
