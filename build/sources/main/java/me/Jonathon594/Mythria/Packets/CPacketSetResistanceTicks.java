package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CPacketSetResistanceTicks implements IMessage {

    private int ticks;

    public CPacketSetResistanceTicks() {
        // TODO Auto-generated constructor stub
    }

    public CPacketSetResistanceTicks(final int ticks) {
        super();
        this.ticks = ticks;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        ticks = buf.readInt();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(ticks);
    }

    public int getTicks() {
        return ticks;
    }

}
