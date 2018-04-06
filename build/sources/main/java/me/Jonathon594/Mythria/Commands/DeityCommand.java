package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class DeityCommand implements ICommand {

    @Override
    public int compareTo(final ICommand arg0) {
        return 0;
    }    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return sender.canUseCommand(3, getName());
    }



    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        final World world = sender.getEntityWorld();
        if (world.isRemote)
            return;

        if (sender instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP) sender;
            if (args.length == 0)
                DeityManager.setDeity(player.getEntityId(), null, true);
            if(args.length == 1) {
                try {
                    Deity d = Deity.valueOf(args[0].toUpperCase());
                    DeityManager.setDeity(player.getEntityId(), d, true);
                    player.sendMessage(new TextComponentString("You are now " + d.name()));
                    if(d.equals(Deity.ELIANA)) {
                        player.inventory.setInventorySlotContents(38, new ItemStack(Items.ELYTRA, 1));
                    }
                } catch (IllegalArgumentException e) {
                    return;
                }
            }
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("deity");
        alias.add("d");
        return alias;
    }

    @Override
    public String getName() {
        return "Deity";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return Arrays.asList(server.getPlayerList().getOnlinePlayerNames());
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "d <deity name>";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        // TODO Auto-generated method stub
        return false;
    }

}
