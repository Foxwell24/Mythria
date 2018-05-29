package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import me.Jonathon594.Mythria.Managers.PerkManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorshipCommand extends CommandBase implements ICommand {

    @Override
    public int compareTo(final ICommand arg0) {
        return 0;
    }    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        final World world = sender.getEntityWorld();
        if (world.isRemote)
            return;

        if(sender instanceof EntityPlayer) {
            if (args.length == 1) {
                Deity d = Deity.valueOf(args[0].toUpperCase());
                if (d == null) {
                    throw new CommandException("commands.error.nodeity");
                }
                DeityManager.addWorshiper((EntityPlayer) sender, d);
                notifyCommandListener(sender, this, "commands.worship.start", MythriaUtil.Capitalize(d.name()));
            } else {
                DeityManager.clearWorshiper((EntityPlayer) sender);
                notifyCommandListener(sender, this, "commands.worship.end");
            }
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("worship");
        return alias;
    }

    @Override
    public String getName() {
        return "Worship";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return new ArrayList<String>();
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "perk <username> <attribute name> <add / remove>";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return index == 0;
    }

}
