package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class CarpentryManager {
    private static ArrayList<SawResult> sawResults = new ArrayList<>();

    public static void Initialize() {
        new SawResult(new ItemStack(MythriaItems.LOG, 1, 0), new ItemStack(MythriaItems.PLANK, 2, 0));
        new SawResult(new ItemStack(MythriaItems.LOG, 1, 1), new ItemStack(MythriaItems.PLANK, 2, 1));
        new SawResult(new ItemStack(MythriaItems.LOG, 1, 2), new ItemStack(MythriaItems.PLANK, 2, 2));
        new SawResult(new ItemStack(MythriaItems.LOG, 1, 3), new ItemStack(MythriaItems.PLANK, 2, 3));
        new SawResult(new ItemStack(MythriaItems.LOG, 1, 4), new ItemStack(MythriaItems.PLANK, 2, 4));
        new SawResult(new ItemStack(MythriaItems.LOG, 1, 5), new ItemStack(MythriaItems.PLANK, 2, 5));

        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG), 1, 0), new ItemStack(MythriaItems.PLANK, 16, 0));
        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG), 1, 1), new ItemStack(MythriaItems.PLANK, 16, 1));
        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG), 1, 2), new ItemStack(MythriaItems.PLANK, 16, 2));
        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG), 1, 3), new ItemStack(MythriaItems.PLANK, 16, 3));

        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG2), 1, 0), new ItemStack(MythriaItems.PLANK, 16, 4));
        new SawResult(new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG2), 1, 1), new ItemStack(MythriaItems.PLANK, 16, 5));
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
