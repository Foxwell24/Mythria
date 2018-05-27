package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Storage.ProfileArchive;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class ReviveCommand extends CommandBase implements ICommand {

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

        EntityPlayer target = server.getPlayerList().getPlayerByUsername(args[0]);
        if(target == null) {
            throw new CommandException("commands.playererror", new Object[] {});
        }

        IProfile profile = target.getCapability(ProfileProvider.PROFILE_CAP, null);
        if(profile.getCreated()) {
            throw new CommandException("commands.revive.hasProfile", new Object[] {});
        }

        NBTTagCompound compound = ProfileArchive.getLastProfile(target);
        if(compound == null) {
            throw new CommandException("commands.revive.noLastProfile", new Object[] {});
        }
        profile.fromNBT(compound);
        profile.setPlayer(target);
        profile.syncToClient();
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("revive");
        return alias;
    }

    @Override
    public String getName() {
        return "Revive";
    }

    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "revive [<username>]";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return true;
    }

}
