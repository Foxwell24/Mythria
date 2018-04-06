package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Managers.PerkManager;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerkCommand extends CommandBase implements ICommand {

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

        if(args.length == 3) {
            EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
            if(entityplayer == null) {
                throw new CommandException("commands.perk.playererror", new Object[] {});
            }
            Perk perk = PerkManager.getAttribute(args[1]);
            boolean add = false;
            if(args[2].equalsIgnoreCase("add")) add = true;
            else if(args[2].equalsIgnoreCase("remove")) add = false;
            else return;

            IProfile profile = entityplayer.getCapability(ProfileProvider.PROFILE_CAP, null);
            if(add) profile.addAttribute(perk);
            else profile.removeAttribute(perk);

            notifyCommandListener(sender, this, "commands.perk.success", new Object[] {add == true ? "added" : "removed", perk.getName(), entityplayer.getName()});
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("perk");
        return alias;
    }

    @Override
    public String getName() {
        return "Perk";
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
            return getListOfStringsMatchingLastWord(args, PerkManager.getAttributes());
        }
        else if (args.length == 3)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"add", "remove"});
        }
        return Collections.emptyList();
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
