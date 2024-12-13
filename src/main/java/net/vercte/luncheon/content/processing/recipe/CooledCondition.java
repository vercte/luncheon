package net.vercte.luncheon.content.processing.recipe;

import com.simibubi.create.Create;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.utility.Lang;
import net.vercte.luncheon.content.processing.cooler.CoolerBlock;

public enum CooledCondition {
    NONE(0xffffff), COOLED(0x00d5ff);

    private final int color;
    CooledCondition(int color) { this.color = color; }

    public String serialize() {
        return Lang.asId(name());
    }

    public String getTranslationKey() {
        return "recipe.cooling_requirement." + serialize();
    }

    public boolean testCooler(CoolerBlock.CoolingLevel level) {
        if(this == COOLED) return level == CoolerBlock.CoolingLevel.COOLED;
        return true;
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
