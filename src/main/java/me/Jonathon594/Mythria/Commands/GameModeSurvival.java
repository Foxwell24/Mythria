package me.Jonathon594.Mythria.Commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class GameModeSurvival implements ICommand {

    @Override
    public int compareTo(final ICommand arg0) {
        return 0;
    }    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return sender.canUseCommand(2, getName());
    }



    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        final World world = sender.getEntityWorld();
        if (world.isRemote)
            return;

        if (sender instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) sender;
            if (args.length == 0)
                player.setGameType(GameType.SURVIVAL);
            if (args.length == 1) {
                final EntityPlayer p = server.getPlayerList().getPlayerByUsername(args[0]);
                if (p != null)
                    p.setGameType(GameType.SURVIVAL);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("gms");
        alias.add("gm0");
        return alias;
    }

    @Override
    public String getName() {
        return "GameModeSurvival";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return Arrays.asList(server.getPlayerList().getOnlinePlayerNames());
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "gms [<username>]";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        // TODO Auto-generated method stub
        return false;
    }

}
