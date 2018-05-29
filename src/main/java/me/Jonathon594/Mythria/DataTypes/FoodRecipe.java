package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.CookType;
import net.minecraft.item.Item;

public class FoodRecipe {
    private final Item result;
    private final Item ingredient;
    private final CookType type;

    public FoodRecipe(final Item result, final Item ingredient, final CookType type) {
        super();
        this.result = result;
        this.ingredient = ingredient;
        this.type = type;
    }

    public Item getIngredient() {
        return ingredient;
    }

    public Item getResult() {
        return result;
    }

    public CookType getType() {
        return type;
    }
}
