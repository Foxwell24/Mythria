package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Managers.PerkManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketAddAttribute implements IMessage {

    private Perk pa;

    public SPacketAddAttribute() {
        // TODO Auto-generated constructor stub
    }

    public SPacketAddAttribute(final Perk pa) {
        super();
        this.pa = pa;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        final NBTTagCompound comp = ByteBufUtils.readTag(buf);
        pa = PerkManager.getAttribute(comp.getString("name"));
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        final NBTTagCompound comp = new NBTTagCompound();
        comp.setString("name", pa.getName());
        ByteBufUtils.writeTag(buf, comp);
    }

    public Perk getAttribute() {
        return pa;
    }

}
