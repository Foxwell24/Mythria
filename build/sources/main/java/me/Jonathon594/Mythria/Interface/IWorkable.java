package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.DataTypes.SmeltingRecipe;
import me.Jonathon594.Mythria.Managers.SmithingManager;

public interface IWorkable {
    SmeltingRecipe.EnumMetal getMetalType();

    SmithingManager.EnumMetalShape getMetalShape();
}
