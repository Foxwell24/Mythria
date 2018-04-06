package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.Alignment;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Managers.PerkManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CombatSkill extends Perk {

    public CombatSkill(final String name, final Block menuIcon, final int menuX, final int menuY, final int cost,
                       final int intellectCost, final MythicSkills relatedSkill, final int requiredLevel,
                       final Alignment alignment, final String... requiredAttribute) {
        super(name, menuIcon, menuX, menuY, relatedSkill, requiredLevel,
                requiredAttribute);

        PerkManager.addCombatSkill(this);
    }

    public CombatSkill(final String name, final Item menuIcon, final int menuX, final int menuY, final int cost,
                       final int intellectCost, final MythicSkills relatedSkill, final int requiredLevel,
                       final Alignment alignment, final String... requiredAttribute) {
        super(name, menuIcon, menuX, menuY, relatedSkill, requiredLevel,
                requiredAttribute);

        PerkManager.addCombatSkill(this);
    }
}
