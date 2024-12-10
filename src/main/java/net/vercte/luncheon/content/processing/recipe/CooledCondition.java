package net.vercte.luncheon.content.processing.recipe;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.Lang;

public enum CooledCondition {
    NONE(0xffffff), COOLED(0x00d5ff), FREEZING(0xa53dff);

    private final int color;
    CooledCondition(int color) { this.color = color; }

    public String serialize() {
        return Lang.asId(name());
    }

    public String getTranslationKey() {
        return "recipe.cooling_requirement." + serialize();
    }

    public static CooledCondition deserialize(String name) {
        for (CooledCondition cooledCondition : values())
            if (cooledCondition.serialize()
                    .equals(name))
                return cooledCondition;
        Create.LOGGER.warn("Tried to deserialize invalid cooled condition: \"" + name + "\"");
        return NONE;
    }
}
