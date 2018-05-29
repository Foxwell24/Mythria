package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.GUI.Container.ContainerCustomWorkbench;
import me.Jonathon594.Mythria.Managers.MaterialRequirementsManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.List;

public class ConditionalRecipe implements IRecipe {
    private final IRecipe recipe;

    public ConditionalRecipe(final IRecipe recipe) {
        super();
        this.recipe = recipe;
    }

    @Override
    public boolean matches(final InventoryCrafting inv, final World worldIn) {
        if (!recipe.matches(inv, worldIn))
            return false;
        final Object eventHandler = ReflectionHelper.getPrivateValue(InventoryCrafting.class, inv, 3);
        EntityPlayer player = null;
        if (eventHandler instanceof ContainerWorkbench) {
            final ContainerWorkbench cw = (ContainerWorkbench) eventHandler;
            final SlotCrafting sc = (SlotCrafting) cw.getSlot(0);
            player = ReflectionHelper.getPrivateValue(SlotCrafting.class, sc, 1);
        }
        if (eventHandler instanceof ContainerCustomWorkbench) {
            final ContainerCustomWorkbench cw = (ContainerCustomWorkbench) eventHandler;
            final SlotCrafting sc = (SlotCrafting) cw.getSlot(0);
            player = ReflectionHelper.getPrivateValue(SlotCrafting.class, sc, 1);
        }
        if (eventHandler instanceof ContainerPlayer) {
            final ContainerPlayer cp = (ContainerPlayer) eventHandler;
            player = ReflectionHelper.getPrivateValue(ContainerPlayer.class, cp, 4);
        }
        if (player == null)
            return false;
        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        if (p == null)
            return false;

        final List<Perk> list = MaterialRequirementsManager.AttributesForCrafting
                .get(getRecipeOutput().getItem());
        if (list == null)
            return true;
        for (final Perk pa : list)
            if (p.getPlayerSkills().contains(pa))
                return true;

        return false;
    }

    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        return recipe.getCraftingResult(inv);
    }

    @Override
    public boolean canFit(final int width, final int height) {
        return recipe.canFit(width, height);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipe.getRecipeOutput();

    }

    @Override
    public IRecipe setRegistryName(final ResourceLocation name) {
        return recipe.setRegistryName(name);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getRegistryName();
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return recipe.getRegistryType();
    }
}
