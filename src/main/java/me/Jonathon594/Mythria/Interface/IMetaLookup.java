package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Enum.Wood;

public interface IMetaLookup {
    String getID();

    Wood getByOrdinal(int metadata);
}
