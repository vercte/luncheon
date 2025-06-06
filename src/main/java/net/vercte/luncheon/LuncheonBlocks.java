package net.vercte.luncheon;

import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;
import net.vercte.luncheon.content.mechanical_cooler.MechanicalCoolerBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class LuncheonBlocks {
    private static final CreateRegistrate REGISTRATE = Luncheon.registrate();

    public static final BlockEntry<MechanicalCoolerBlock> MECHANICAL_COOLER = REGISTRATE
            .block("mechanical_cooler", MechanicalCoolerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
            .transform(pickaxeOnly())
            .lang("Mechanical Cooler")
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .item()
            .transform(customItemModel())
            .onRegister((block) -> BlockStressValues.IMPACTS.register(block, () -> 2.0))
            .register();

    public static void initalize() {}
}
