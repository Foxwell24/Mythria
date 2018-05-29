package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.StaminaCost;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packets.WallJumpPacket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WallJumpManager {
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (!(event.phase == TickEvent.Phase.END))
            return;

        if (!event.player.world.isRemote)
            return;

        final Minecraft mc = Minecraft.getMinecraft();
        final IProfile profile = mc.player.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (profile.getConsumables().get(Consumable.STAMINA) < StaminaCost.WALL_JUMP_COST)
            return; // Not enough stamina
        if (mc.gameSettings.keyBindJump.isKeyDown())
            if (!mc.player.onGround && !mc.player.isInWater() && mc.player.collidedHorizontally
                    && mc.player.motionY < 0.0D) {
                if (!PerkManager.hasAttributeFlag(mc.player.getCapability(ProfileProvider.PROFILE_CAP, null),
                        AttributeFlag.WALLJUMP1))
                    return;
                final IBlockState blockFacing = getBlockFacing();
                if (blockFacing == null)
                    return;
                if (isProhibited(blockFacing))
                    return;
                MythriaPacketHandler.INSTANCE.sendToServer(new WallJumpPacket());
                wallJump();
            }
    }

    private static IBlockState getBlockFacing() {
        final Minecraft mc = Minecraft.getMinecraft();
        final Vec3d look = mc.player.getLookVec();
        look.crossProduct(new Vec3d(1, 0, 1));
        final BlockPos pos = new BlockPos(mc.player.getPositionVector().add(new Vec3d(0, 1, 0).add(look)));
        return mc.player.world.getBlockState(pos);

    }

    private static boolean isProhibited(final IBlockState blockFacing) {
        if (!blockFacing.isFullBlock())
            return true;
        if (!blockFacing.isOpaqueCube())
            return true;
        return blockFacing.isTranslucent();
    }

    public static void wallJump() {
        final Minecraft mc = Minecraft.getMinecraft();
        final double a = Math.toRadians(mc.player.rotationYawHead + 180f);
        mc.player.motionX = -Math.sin(a) / 2.0D;
        mc.player.motionZ = Math.cos(a) / 2.0D;
        mc.player.motionY = 0.6D;
    }
}
