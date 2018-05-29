package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Managers.PerkManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;

public class PersonallityTrait extends Perk {

    public PersonallityTrait(final String name, final Block menuIcon, final int menuX, final int menuY) {
        super(name, menuIcon, menuX, menuY, null, 0, new String[] {});

        PerkManager.addPersonality(this);
    }

    public PersonallityTrait(final String name, final Item menuIcon, final int menuX, final int menuY) {
        super(name, menuIcon, menuX, menuY, null, 0, new String[] {});

        PerkManager.addPersonality(this);
    }

    public HashMap<Attribute, Integer> getStartingAttributes() {
        return startingAttributes;
    }

    private HashMap<Attribute, Integer> startingAttributes = new HashMap<>();

    public PersonallityTrait addStartingAttributes(Attribute attribute, Integer value) {
        startingAttributes.put(attribute, value);
        return this;
    }
}
