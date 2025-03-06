package net.vercte.luncheon.content.processing.recipe;

import com.simibubi.create.Create;
import net.createmod.catnip.lang.Lang;
import net.vercte.luncheon.content.processing.cooler.CoolerBlock;

public enum CooledCondition {
    NONE(0xffffff), COOLED(0x4455b8);

    private final int color;
    CooledCondition(int color) { this.color = color; }

    public String serialize() {
        return Lang.asId(name());
    }

    public String getTranslationKey() {
        return "recipe.cooling_requirement." + serialize();
    }

    public int getColor() { return color; }

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
