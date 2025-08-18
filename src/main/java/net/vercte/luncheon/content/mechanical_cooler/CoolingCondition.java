package net.vercte.luncheon.content.mechanical_cooler;

import net.createmod.catnip.lang.Lang;
import net.minecraft.util.StringRepresentable;
import net.vercte.luncheon.Luncheon;
import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.common.crafting.CraftingHelper.serialize;

public enum CoolingCondition implements StringRepresentable {
    NONE(0xffffff), COOL(0x4455b8);

    private final int color;
    CoolingCondition(int color) { this.color = color; }

    public int getColor() { return color; }

    public static CoolingCondition byIndex(int index) { return values()[index]; }

    public static CoolingCondition deserialize(String name) {
        for (CoolingCondition cooledCondition : values())
            if (cooledCondition.getSerializedName().equals(name))
                return cooledCondition;
        Luncheon.LOGGER.warn("Tried to deserialize invalid cooling condition: {}", name);
        return NONE;
    }

    public String getTranslationKey() {
        return "recipe.cooling_requirement." + serialize();
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return Lang.asId(name());
    }
}
