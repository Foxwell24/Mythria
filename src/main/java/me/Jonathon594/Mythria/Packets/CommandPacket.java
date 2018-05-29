package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.GUI.MythriaGui;
import me.Jonathon594.Mythria.Managers.ChatManager;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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

    public static class CommandPacketHandler implements IMessageHandler<CommandPacket, IMessage> {

        public static final int TOGGLE_CHAT_ID = 0;
        public static final int OPEN_TRADE_SKILLS_GUI = 2;
        public static final int OPEN_LIFE_SKILLS_GUI = 3;
        public static final int OPEN_LIFE_COMBAT_GUI = 4;
        public static final int PROFILE_SYNC_REQUEST = 5;
        public static final int OPEN_MAGIC_SKILLS_GUI = 6;
        public static final int OPEN_PERSONALITIES_GUI = 7;
        public static final int OPEN_ATTRIBUTE_GUI = 8;

        @Override
        public IMessage onMessage(final CommandPacket message, final MessageContext ctx) {
            final EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

            final int id = message.getId();
            if (id == TOGGLE_CHAT_ID) {
                ChatManager.ToggleChatChannel(serverPlayer);
                serverPlayer.sendMessage(new TextComponentString(
                        String.format(MythriaConst.CHAT_TOGGLED, ChatManager.getChatChannel(serverPlayer).name())));
            }
            if (id == OPEN_TRADE_SKILLS_GUI)
                serverPlayer.openGui(Mythria.instance, MythriaGui.MYTHRIA_SHOW_TRADESKILLS_GUI.ordinal(),
                        serverPlayer.world, 0, 0, 0);
            if (id == OPEN_LIFE_SKILLS_GUI)
                serverPlayer.openGui(Mythria.instance, MythriaGui.MYTHRIA_SHOW_LIFESKILLS_GUI.ordinal(), serverPlayer.world,
                        0, 0, 0);
            if (id == OPEN_LIFE_COMBAT_GUI)
                serverPlayer.openGui(Mythria.instance, MythriaGui.MYTHRIA_SHOW_COMBATSKILLS_GUI.ordinal(),
                        serverPlayer.world, 0, 0, 0);
            if (id == PROFILE_SYNC_REQUEST)
                return new ClientProfileDataCachePacket(
                        serverPlayer.getCapability(ProfileProvider.PROFILE_CAP, null).toNBT());
            if (id == OPEN_MAGIC_SKILLS_GUI)
                serverPlayer.openGui(Mythria.instance, MythriaGui.MYTHRIA_SHOW_MAGICSKILLS_GUI.ordinal(),
                        serverPlayer.world, 0, 0, 0);
            if (id == OPEN_PERSONALITIES_GUI)
                serverPlayer.openGui(Mythria.instance, MythriaGui.MYTHRIA_SHOW_PERSONALITIES_GUI.ordinal(),
                        serverPlayer.world, 0, 0, 0);
            if (id == OPEN_ATTRIBUTE_GUI)
                serverPlayer.openGui(Mythria.instance, MythriaGui.MYTHRIA_SHOW_ATTRIBUTE_GUI.ordinal(),
                        serverPlayer.world, 0, 0, 0);
            return null;
        }

    }
}
