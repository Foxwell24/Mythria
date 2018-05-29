package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LevelCommand extends CommandBase implements ICommand {

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
            MythicSkills skill = MythicSkills.valueOf(args[1].toUpperCase());
            int amount = 0;
            if(entityplayer == null) {
                throw new CommandException("commands.playererror", new Object[] {});
            }
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {

            }

            IProfile profile = entityplayer.getCapability(ProfileProvider.PROFILE_CAP, null);
            profile.addSkillExperience(skill, amount, (EntityPlayerMP) entityplayer);


            notifyCommandListener(sender, this, "commands.level.success", new Object[] {entityplayer.getName(), amount, skill.name()});
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("level");
        return alias;
    }

    @Override
    public String getName() {
        return "Level";
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
            return getListOfStringsMatchingLastWord(args, Arrays.asList(MythicSkills.values()));
        }
        return Collections.emptyList();
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "level <username> <skill> <amount>";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return index == 0;
    }

}
