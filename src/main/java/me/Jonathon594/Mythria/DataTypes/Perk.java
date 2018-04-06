package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.*;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Managers.PerkManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Perk {
    final HashMap<StatType, Double> statModifiers = new HashMap<>();
    private final String name;
    private final List<Item> craftable = new ArrayList<>();
    private final List<Block> placable = new ArrayList<>();
    private final List<Block> breakable = new ArrayList<>();
    private final String[] requiredPerks;
    private final HashMap<MythicSkills, Integer> requiredSkills = new HashMap<>();
    private final List<AttributeFlag> attributeFlags = new ArrayList<>();
    private Item menuIcon;
    private int menuX = 0;
    private int menuY = 0;
    private int minimumAge;
    private int learnTime;
    private double bonusFatigueMitigation = 0.0;
    private HashMap<Attribute, Integer> requiredAttributes = new HashMap<>();

    public Perk(final String name, final Block menuIcon, final int menuX, final int menuY,
                final MythicSkills relatedSkill, final int requiredLevel,
                final String[] requiredAttribute) {
        super();
        this.name = name;
        this.menuIcon = Item.getItemFromBlock(menuIcon);
        this.menuX = menuX;
        this.menuY = menuY;

        requiredPerks = requiredAttribute;

        if (relatedSkill != null) requiredSkills.put(relatedSkill, requiredLevel);
    }

    public Perk(final String name, final Item menuIcon, final int menuX, final int menuY,
                final MythicSkills relatedSkill, final int requiredLevel,
                final String[] requiredAttribute) {
        super();
        this.name = name;
        this.menuIcon = menuIcon;
        this.menuX = menuX;
        this.menuY = menuY;
        requiredPerks = requiredAttribute;

        if (relatedSkill != null) requiredSkills.put(relatedSkill, requiredLevel);
    }

    public HashMap<Attribute, Integer> getRequiredAttributes() {
        return requiredAttributes;
    }

    public Perk addRequiredAttribute(Attribute attribute, Integer value) {
        requiredAttributes.put(attribute, value);
        return this;
    }

    public Perk addAttributeFlag(final AttributeFlag dodge) {
        attributeFlags.add(dodge);
        return this;
    }

    public Perk addBreakable(final Block... blocks) {
        for (final Block b : blocks)
            breakable.add(b);
        return this;
    }

    public Perk addCraftable(final Object... items) {
        for (final Object i : items) {
            if (i instanceof Item)
                craftable.add((Item) i);
            if (i instanceof Block)
                craftable.add(Item.getItemFromBlock((Block) i));
        }

        return this;
    }

    public Perk addPlaceable(final Block... blocks) {
        for (final Block b : blocks)
            placable.add(b);
        return this;
    }

    public void addRequiredSkill(final MythicSkills type, final int value) {
        requiredSkills.put(type, value);
    }

    public List<AttributeFlag> getAttributeFlags() {
        return attributeFlags;
    }

    public ItemStack getAttributeItem(final EntityPlayer player) {
        final IProfile p = player.getCapability(ProfileProvider.PROFILE_CAP, null);
        TextFormatting color = TextFormatting.BLUE;
        if (p.getPlayerSkills().contains(this))
            color = TextFormatting.GREEN;
        boolean display = true;
        if (requiredPerks.length > 0)
            for (final String s : requiredPerks) {
                final Perk req = PerkManager.getAttribute(s);
                if (req != null && !p.getPlayerSkills().contains(req))
                    display = false;
            }
        if (!display)
            return new ItemStack(Blocks.GLASS).setStackDisplayName(TextFormatting.RED + "Locked");

        if (getRequiredSkills().size() > 0) if (!hasRequiredSkills(p))
            color = TextFormatting.RED;

        boolean aligned = false;

        final ItemStack is = new ItemStack(menuIcon, 1);
        is.setStackDisplayName(color + name);
        final ArrayList<String> lines = new ArrayList<>();
        if (requiredPerks.length > 0)
            for (final String s : requiredPerks) {
                lines.add(MythriaConst.MAIN_COLOR + "Requires:");
                if (PerkManager.getAttribute(s) != null)
                    lines.add(MythriaConst.CONT_COLOR + s);
            }
        if (learnTime > 0)
            lines.add(MythriaConst.MAIN_COLOR + "Learn Time: " + MythriaConst.CONT_COLOR + learnTime);
        if (minimumAge > 0)
            lines.add(MythriaConst.MAIN_COLOR + "Minimum Age: " + MythriaConst.CONT_COLOR + minimumAge + " ("
                    + p.getBirthDay().getYearsFromCurrent() + ")");
        for (final Entry<MythicSkills, Integer> e : requiredSkills.entrySet())
            lines.add(MythriaConst.MAIN_COLOR + e.getKey().name() + ": " + MythriaConst.CONT_COLOR + e.getValue() + " ("
                    + p.getSkillLevel(e.getKey()) + ")");

        for (Entry<StatType, Double> e : getStatModifiers().entrySet()) {
            lines.add(MythriaConst.CONT_COLOR + e.getKey().name() + (e.getValue() > 0 ? ": +" : ": ") + e.getValue());
        }

        if (getCraftable().size() > 0) lines.add(MythriaConst.MAIN_COLOR + "Craftable:");
        for (Item i : getCraftable()) {
            lines.add(MythriaConst.CONT_COLOR + new ItemStack(i, 1).getDisplayName());
        }
        if (getPlacable().size() > 0) lines.add(MythriaConst.MAIN_COLOR + "Placeable:");
        for (Block b : getPlacable()) {
            lines.add(MythriaConst.CONT_COLOR + new ItemStack(ItemBlock.getItemFromBlock(b), 1).getDisplayName());
        }
        if (getBreakable().size() > 0) lines.add(MythriaConst.MAIN_COLOR + "Breakable:");
        for (Block b : getBreakable()) {
            lines.add(MythriaConst.CONT_COLOR + new ItemStack(ItemBlock.getItemFromBlock(b), 1).getDisplayName());
        }

        if (requiredAttributes.size() > 0) {
            lines.add(MythriaConst.MAIN_COLOR + "Required Attribute Levels:");
            for (Entry<Attribute, Integer> e : requiredAttributes.entrySet()) {
                lines.add(MythriaConst.CONT_COLOR + "" + e.getKey().name() + ": " + e.getValue() + " (" + p.getAttribute(e.getKey()) + ")");
            }
        }

        if (this instanceof PersonallityTrait) {
            PersonallityTrait pers = (PersonallityTrait) this;
            lines.add(MythriaConst.MAIN_COLOR + "Starting Attribute Levels:");
            for (Entry<Attribute, Integer> e : pers.getStartingAttributes().entrySet()) {
                lines.add(MythriaConst.CONT_COLOR + "" + e.getKey().name() + ": " + e.getValue() + " (" + p.getAttribute(e.getKey()) + ")");
            }
        }

        MythriaUtil.addLoreToItemStack(is, lines);
        return is;
    }

    public HashMap<MythicSkills, Integer> getRequiredSkills() {
        return requiredSkills;
    }

    public boolean hasRequiredSkills(final IProfile p) {
        for (final Entry<MythicSkills, Integer> e : requiredSkills.entrySet())
            if (p.getSkillLevel(e.getKey()) < e.getValue())
                return false;
        return true;
    }

    public HashMap<StatType, Double> getStatModifiers() {
        return statModifiers;
    }

    public List<Item> getCraftable() {
        return craftable;
    }

    public List<Block> getPlacable() {
        return placable;
    }

    public List<Block> getBreakable() {
        return breakable;
    }

    public double getFatigueMitigation() {
        return bonusFatigueMitigation;
    }

    public ItemStack getItemStack() {
        final ItemStack is = new ItemStack(menuIcon, 1);
        is.setStackDisplayName(getName());
        return is;
    }

    public String getName() {
        return name;
    }

    public int getLearnTime() {
        return learnTime;
    }

    public Perk setLearnTime(final int learnTime) {
        this.learnTime = learnTime;
        return this;
    }

    public Item getMenuIcon() {
        return menuIcon;
    }

    public int getMenuX() {
        return menuX;
    }

    public int getMenuY() {
        return menuY;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public Perk setMinimumAge(final int minimumAge) {
        this.minimumAge = minimumAge;
        return this;
    }

    public String[] getRequiredPerks() {
        return requiredPerks;
    }

    public boolean hasRequiredAttributes(final IProfile p) {
        for (final String s : requiredPerks) {
            final Perk pa = PerkManager.getAttribute(s);
            if (pa == null)
                continue;
            if (!p.getPlayerSkills().contains(pa))
                return false;
        }
        return true;
    }

    public Perk setBonusFatigueMitigation(final double d) {
        bonusFatigueMitigation = d;
        return this;
    }

    public Perk setFlags(final SpecialFlag[] flags) {
        return this;
    }
}
