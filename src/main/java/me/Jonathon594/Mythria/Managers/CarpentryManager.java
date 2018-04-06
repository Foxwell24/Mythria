package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class CarpentryManager {
    private static ArrayList<SawResult> sawResults = new ArrayList<>();

    public static void Initialize() {
        new SawResult(new ItemStack(MythriaItems.LOG, 1, 0), new ItemStack(MythriaItems.PLANK, 2, 0));
        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG), 1, 0), new ItemStack(MythriaItems.PLANK, 16, 0));
        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG2), 1, 0), new ItemStack(MythriaItems.PLANK, 16, 0));
    }

    public static SawResult getSawResult(ItemStack input) {
        ItemStack copy = input.copy();
        copy.setCount(1);
        for(SawResult result : sawResults) {
            if(result.getInput().isItemEqual(input)) {
                return result;
            }
        }
        return null;
    }

    public static class SawResult {
        private ItemStack input;
        private ItemStack output;

        public SawResult(ItemStack input, ItemStack output) {
            this.input = input;
            this.output = output;

            CarpentryManager.addResult(this);
        }


        public ItemStack getInput() {
            return input.copy();
        }

        public ItemStack getOutput() {
            return output.copy();
        }
    }

    private static void addResult(SawResult sawResult) {
        sawResults.add(sawResult);
    }
}
