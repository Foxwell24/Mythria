package me.Jonathon594.Mythria.Module;

import me.Jonathon594.Mythria.Items.MythriaItemChisel;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

public class BlockDropModule {
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Block b = event.getState().getBlock();
        Item toolUsed = event.getPlayer().getHeldItemMainhand().getItem();

        if(b.equals(Blocks.TALLGRASS) || b.equals(Blocks.DOUBLE_PLANT)) {
            if(Math.random() < 0.5) {
                InventoryHelper.spawnItemStack(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                        new ItemStack(MythriaItems.THATCH, 1));
            }
        }
        if(b.equals(Blocks.STONE)) {
            if(toolUsed instanceof MythriaItemChisel) {
                InventoryHelper.spawnItemStack(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                        new ItemStack(Blocks.STONE, 1));
            }
        }
    }
}
