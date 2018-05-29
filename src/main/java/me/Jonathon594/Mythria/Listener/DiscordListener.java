package me.Jonathon594.Mythria.Listener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        if (event.getChannel().getName().equalsIgnoreCase("general")) {
            final List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                    .getPlayers();
            for (final EntityPlayerMP player : players) {
                String message = TextFormatting.GREEN + "Discord " + TextFormatting.WHITE + "(" +
                        TextFormatting.BLUE + event.getAuthor().getName() + TextFormatting.WHITE + "): " +
                        event.getMessage().getStrippedContent();
                player.sendMessage(new TextComponentString(message));
            }
        }
    }
}
