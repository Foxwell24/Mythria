package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Enum.ChatChannel;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Listener.DiscordListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManager {
    private static JDA jda = null;

    private static Map<EntityPlayerMP, ChatChannel> selectedChannels = new HashMap<>();

    private static HashMap<EntityPlayerMP, Long> lastOOCMap = new HashMap<>();

    @SideOnly(Side.SERVER)
    public static void initilizeDiscord() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("Mjc0NzE5NjgyNDU4NTUwMjcy.DVGQcg.vUrZVFCj8LqHz2OW4mCeGxaEi-M").buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
        List<TextChannel> channels =  jda.getTextChannelsByName("general", true);
        channels.get(0).sendMessage("Server Started");
        jda.addEventListener(new DiscordListener());
    }

    public static TextFormatting getColor(final ChatChannel channel) {
        switch (channel) {
            case LOCAL:
                return TextFormatting.GREEN;
            case OOC:
                return TextFormatting.WHITE;
            case WHISPER:
                return TextFormatting.BLUE;
            case YELL:
                return TextFormatting.RED;
            case PRAY:
                return TextFormatting.YELLOW;
            default:
                return TextFormatting.GREEN;
        }
    }

    public static void handleForgeChat(ServerChatEvent event, EntityPlayerMP sender, IProfile p) {
        final List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                .getPlayers();
        final ChatChannel channel = ChatManager.getChatChannel(sender);
        final int range = ChatManager.getRange(channel);
        if(channel.equals(ChatChannel.OOC)) {
            if(!canSendOOC(sender)) {
                sender.sendMessage(new TextComponentString("You need to wait longer to send an OOC message."));
                return;
            } else {
                lastOOCMap.put(sender, System.currentTimeMillis());
            }
        }
        final TextFormatting tf = ChatManager.getColor(channel);
        for (final EntityPlayerMP player : players) {
            if (!sender.getEntityWorld().equals(player.getEntityWorld()) && range > 0)
                continue;
            if (range == 0 || sender.getDistance(player) <= range) {
                sendMessage(event, sender, p, player, tf);
            }
        }

        if(channel.equals(ChatChannel.PRAY)) {
            for (final EntityPlayerMP player : players) {
                if(player.canUseCommand(2, "")) {
                    sendMessage(event, sender, p, player, tf);
                }
            }
        }

        if(range == 0) {
            List<TextChannel> channels =  jda.getTextChannelsByName("general", true);
            channels.get(0).sendMessage(p.getFirstName() + " " + p.getLastName() + " (" + sender.getName() + "): " +
                    event.getMessage()).queue();
        }
        if(channel == ChatChannel.PRAY) {
            List<TextChannel> channels =  jda.getTextChannelsByName("pray", true);
            channels.get(0).sendMessage(p.getFirstName() + " " + p.getLastName() + " (" + sender.getName() + "): " +
                    event.getMessage() + " " + sender.getPosition().toString()).queue();
        }
    }

    private static boolean canSendOOC(EntityPlayerMP sender) {
        if(!lastOOCMap.containsKey(sender)) return true;
        if(lastOOCMap.get(sender) + 15000 < System.currentTimeMillis()) return true;
        return false;
    }

    public static void sendMessage(ServerChatEvent event, EntityPlayerMP sender, IProfile p, EntityPlayerMP player, TextFormatting tf) {
        String name = sender.getName();
        if (p.getCreated())
            name = TextFormatting.YELLOW + p.getFirstName() + " " + p.getLastName();
        Deity d = DeityManager.getSelectedDeities().get(sender.getEntityId());
        if(d != null) {
            name = DeityManager.getDeityNameString(d);
        }
        String message = name + TextFormatting.WHITE + " -> " + tf + event.getMessage();
        player.sendMessage(
                new TextComponentString(message));
    }

    public static int getRange(final ChatChannel channel) {
        switch (channel) {
            case LOCAL:
                return 20;
            case OOC:
                return 0;
            case WHISPER:
                return 5;
            case YELL:
                return 40;
            case PRAY:
                return 20;
            default:
                return 20;
        }
    }

    public static void setChatChannel(final EntityPlayerMP player, final ChatChannel channel) {
        selectedChannels.put(player, channel);
    }

    public static void ToggleChatChannel(final EntityPlayerMP player) {
        ChatChannel channel = getChatChannel(player);
        if (channel == null)
            channel = ChatChannel.LOCAL;
        switch (channel) {
            case LOCAL:
                channel = ChatChannel.YELL;
                break;
            case OOC:
                channel = ChatChannel.WHISPER;
                break;
            case WHISPER:
                channel = ChatChannel.LOCAL;
                break;
            case YELL:
                channel = ChatChannel.PRAY;
                break;
            case PRAY:
                channel = ChatChannel.OOC;
        }
        selectedChannels.put(player, channel);
    }

    public static ChatChannel getChatChannel(final EntityPlayerMP player) {
        return selectedChannels.get(player);
    }
}
