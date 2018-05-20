package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.DataTypes.DeityAbilities.DeityAbility;
import me.Jonathon594.Mythria.Managers.DeityManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class DeityAbilityCommand implements ICommand {

    @Override
    public int compareTo(final ICommand arg0) {
        return 0;
    }    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return sender.canUseCommand(3, getName());
    }

    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        if(!(sender instanceof EntityPlayer)){
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(new TextComponentString("You need to specify an a deity ability name."));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;

        Deity selectedDeity = DeityManager.getSelectedDeities().get(player.getEntityId());

        DeityAbility da = DeityManager.getDeityAbility(args[0]);
        if(da == null) {
            sender.sendMessage(new TextComponentString("That is not a valid ability."));
            return;
        }

        if(selectedDeity == null || selectedDeity != da.getOwner()) {
            sender.sendMessage(new TextComponentString("You need to be " + MythriaUtil.Capitalize(da.getOwner().name()) + " to do that."));
            return;
        }

        if(!da.canChargeCost()) {
            sender.sendMessage(new TextComponentString("You do not have enough deity power."));
            return;
        }

        EntityPlayer target = args.length > 1 ? player.world.getPlayerEntityByName(args[1]) : null;

        if(!da.execute(player, target)) {
            return;
        }

        da.chargeCost();
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("deityability");
        alias.add("da");
        return alias;
    }

    @Override
    public String getName() {
        return "DeityAbility";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return Arrays.asList(server.getPlayerList().getOnlinePlayerNames());
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "da <ability> [target]";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        // TODO Auto-generated method stub
        return false;
    }

}
