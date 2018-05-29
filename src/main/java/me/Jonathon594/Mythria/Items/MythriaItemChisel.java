package me.Jonathon594.Mythria.Items;

import com.google.common.collect.Sets;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import me.Jonathon594.Mythria.Interface.IItemData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Set;

public class MythriaItemChisel extends ItemTool implements IItemData {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.STONE);
    private final double weight;

    public MythriaItemChisel(float damage, float speed, ToolMaterial material, String name, double weight) {
        super(damage, speed, material, EFFECTIVE_ON);
        setMaxStackSize(1);
        setUnlocalizedName(name);
        setRegistryName(getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.TOOLS);
        this.weight = weight;

        MythriaItems.addItem(this);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if(state.getMaterial().equals(Material.ROCK)) {
            return this.efficiency;
        }
        return 0f;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack chisel = playerIn.getHeldItem(handIn);
        if(playerIn.world.isRemote) return new ActionResult<ItemStack>(EnumActionResult.PASS, chisel);
        for(int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {
            ItemStack is = playerIn.inventory.getStackInSlot(i);

            if(is.getItem().equals(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE))) {
                Random random = new Random();
                int count = random.nextInt(2)+1;
                count = Math.min(count, chisel.getMaxDamage()-chisel.getItemDamage());
                chisel.setItemDamage(chisel.getItemDamage()+count);
                if(chisel.getItemDamage() > chisel.getMaxDamage()) {
                    chisel = ItemStack.EMPTY;
                }
                is.shrink(1);

                for(int index = 0; index < count; index++) {
                    ItemStack brick = new ItemStack(MythriaItems.STONE_BRICK, 1);
                    if (!playerIn.inventory.addItemStackToInventory(brick)) {
                        InventoryHelper.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, brick);
                    }
                }
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, chisel);
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, chisel);
    }
}
