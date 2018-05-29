package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Capability.DeityFavor.DeityFavorProvider;
import me.Jonathon594.Mythria.Capability.DeityFavor.IDeityFavor;
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavorCommand extends CommandBase implements ICommand {

    @Override
    public int compareTo(final ICommand arg0) {
        return 0;
    }    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return sender.canUseCommand(2, getName());
    }



    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        final World world = sender.getEntityWorld();
        if (world.isRemote)
            return;

        if(args.length == 4) {
            EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
            if(entityplayer == null) {
                throw new CommandException("commands.playererror", new Object[] {});
            }

            IDeityFavor df = entityplayer.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
            if(args[1].equalsIgnoreCase("add")) {
                try {
                    Deity d = Deity.valueOf(args[2].toUpperCase());
                    if (d == null) {
                        throw new CommandException("commands.error.nodeity");
                    }

                    int amount = Integer.parseInt(args[3]);
                    DeityManager.setFavor(df, (EntityPlayerMP) entityplayer, d, amount + df.getFavor(d));
                    notifyCommandListener(sender, this, "commands.favor.success",
                            new Object[]{entityplayer.getName(), amount, MythriaUtil.Capitalize(d.name())});
                } catch (NumberFormatException e) {
                    throw new CommandException("commands.numberformat");
                }
            }
        } else if (args.length == 2 && args[1].equalsIgnoreCase("show")) {
            EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
            if (entityplayer == null) {
                throw new CommandException("commands.playererror", new Object[]{});
            }

            IDeityFavor df = entityplayer.getCapability(DeityFavorProvider.DEITY_FAVOR_CAP, null);
            for (Deity d : Deity.values()) {
                int i = df.getFavor(d);
                TextFormatting color = DeityManager.getDeityColor(d);

                sender.sendMessage(new TextComponentString(color + "" + MythriaUtil.Capitalize(d.name()) + ": " + i));
            }
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("favor");
        return alias;
    }

    @Override
    public String getName() {
        return "favor";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        else if (args.length == 2)
        {
            return Collections.emptyList();
        }
        else if (args.length == 3)
        {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "favor <username> <show/add> [<deity>] [<amount>]";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return index == 0;
    }

}
