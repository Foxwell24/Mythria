package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class BlessItemCommand implements ICommand {

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
            if (args.length == 1) {
                String name = args[0];
                Deity d = DeityManager.getSelectedDeities().get(player.getEntityId());
                if(d == null) {
                    player.sendMessage(new TextComponentString("You need to select a deity to bless an item."));
                    return;
                }
                ItemStack is = player.getHeldItemMainhand();
                if(is == null || is.isEmpty()) {
                    player.sendMessage(new TextComponentString("Please hold an item to bless."));
                }

                is.setStackDisplayName(DeityManager.getDeityColor(d) + name);
                ArrayList<String> lines = new ArrayList<>();
                lines.add(DeityManager.getDeityColor(d) + "Blessed by " + DeityManager.getDeityNameString(d));
                MythriaUtil.addLoreToItemStack(is, lines);
            } else {
                player.sendMessage(new TextComponentString(getUsage(sender)));
            }
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("b");
        alias.add("bless");
        return alias;
    }

    @Override
    public String getName() {
        return "Bless";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return Arrays.asList(server.getPlayerList().getOnlinePlayerNames());
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "bless <display name>";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        // TODO Auto-generated method stub
        return false;
    }

}
