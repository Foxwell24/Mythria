package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.DeityFavor.DeityFavorProvider;
import me.Jonathon594.Mythria.Capability.DeityFavor.IDeityFavor;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketSetFavor implements IMessage {

    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public Integer getDeityIndex() {
        return deityIndex;
    }

    private Integer deityIndex;

    public SPacketSetFavor(Integer entityID, Integer deityIndex) {
        this.amount = entityID;
        this.deityIndex = deityIndex;
    }

    public SPacketSetFavor() {

    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        deityIndex = buf.readInt();
        amount = buf.readInt();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(deityIndex);
        buf.writeInt(amount);
    }

    public static class SPacketSetFavorHandler implements IMessageHandler<SPacketSetFavor, IMessage> {
        @Override
        public IMessage onMessage(SPacketSetFavor message, MessageContext ctx) {
            IDeityFavor df = Minecraft.getMinecraft().player.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
            DeityManager.setFavor(df, Deity.values()[message.deityIndex], message.amount);
            return null;
        }
    }
}
