package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Managers.PerkManager;
import net.minecraft.init.Items;

public class BlessingPerk extends Perk {
    public BlessingPerk(String name, AttributeFlag flag) {
        super(name, Items.AIR, 0, 0, null, 0, new String[] {});
        addAttributeFlag(flag);

        PerkManager.addBlessingPerk(this);
    }
}
