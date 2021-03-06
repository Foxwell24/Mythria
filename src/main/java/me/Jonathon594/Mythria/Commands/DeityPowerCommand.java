package me.Jonathon594.Mythria.Commands;

import me.Jonathon594.Mythria.Enum.Deity;
import me.Jonathon594.Mythria.Managers.DeityManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class DeityPowerCommand implements ICommand {

    @Override
    public int compareTo(final ICommand arg0) {
        return 0;
    }

    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return sender.canUseCommand(3, getName());
    }


    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        double totalPower = 0;
        for (Deity deity : Deity.values()) {
            totalPower += DeityManager.getDeityPower(deity);
        }
        for (Deity deity : Deity.values()) {
            TextFormatting color = DeityManager.getDeityColor(deity);
            int dp = DeityManager.getDeityPower(deity);
            double prop = totalPower > 0 ? (double) dp / totalPower : 0;

            String message = color + MythriaUtil.Capitalize(deity.name()) + ": " + (int) Math.round(prop * 800) + "% (" + dp + ") Total Gains: "
                    + DeityManager.getTotalWorshipGains(deity) + " Total Losses: " + DeityManager.getTotalBlessingLosses(deity);

            sender.sendMessage(new TextComponentString(message));
        }
    }

    @Override
    public List<String> getAliases() {
        final List<String> alias = new ArrayList<>();
        alias.add("deitypower");
        alias.add("dp");
        return alias;
    }

    @Override
    public String getName() {
        return "DeityPower";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender,
                                          final String[] args, final BlockPos targetPos) {
        return new ArrayList<String>();
    }

    @Override
    public String getUsage(final ICommandSender sender) {
        return "dp";
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        // TODO Auto-generated method stub
        return false;
    }

}
