package me.Jonathon594.Mythria.Enum;

import me.Jonathon594.Mythria.Interface.IMetaLookup;

public enum Wood implements IMetaLookup {
    OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK;

    @Override
    public String getID() {
        return "wood_type";
    }

    @Override
    public Wood getByOrdinal(int metadata) {
        return Wood.values()[metadata];
    }
}
