package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CommandPacket implements IMessage {

    private int id;

    public CommandPacket() {
    }

    public CommandPacket(final int i) {
        setId(i);
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        setId(buf.readInt());
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(getId());
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

}
