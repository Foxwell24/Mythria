package me.Jonathon594.Mythria.Module;

import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class InteractModule {
    public static void placePitKiln(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        EnumHand hand = event.getHand();
        ItemStack is = player.getHeldItem(hand);
        Item i = is.getItem();
        World world = player.world;

        if(world.isRemote) return;
        if(hand.equals(EnumHand.OFF_HAND)) return;



        if(!i.equals(MythriaItems.THATCH)) return;

        BlockPos pos = event.getPos().up();

        if(!MythriaBlocks.PIT_KILN.canPlaceBlockAt(world, pos)) return;

        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (!p.hasFlag(AttributeFlag.SMELTING)) {
            player.sendMessage(new TextComponentString(MythriaConst.NO_PERK));
            return;
        }
        world.setBlockState(pos, MythriaBlocks.PIT_KILN.getDefaultState());
        is.shrink(1);
    }

}
