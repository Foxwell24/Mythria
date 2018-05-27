package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandKill;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class DeityPowerModCommand extends CommandBase implements ICommand {

    @Override
    public int compareTo(final ICommand arg0) {
        return 0;
    }    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return sender.canUseCommand(3, getName());
    }

    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        if(args.length != 2) return;

        Deity d = Deity.valueOf(args[0].toUpperCase());
        if(d == null) {
            notifyCommandListener(sender, this, "commands.deitypowermod.nodeity");
            return;
        }

        int amount = 0;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            notifyCommandListener(sender, this, "commands.numberformat");
            return;
        }

        DeityManager.setDeityPower(d, DeityManager.getDeityPower(d) + amount);
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("deitypowermod");
        alias.add("dpm");
        return alias;
    }

    @Override
    public String getName() {
        return "DeityPowerModify";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return Arrays.asList(server.getPlayerList().getOnlinePlayerNames());
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "dpm <deity name> <amount>";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        // TODO Auto-generated method stub
        return false;
    }

}
