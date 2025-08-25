package net.vercte.luncheon;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Luncheon.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuncheonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ALLOW_GLASS_SHARD_SPIKING = BUILDER
            .comment("Whether Deployers should be able to spike food using Glass Shards.")
            .define("allow_glass_shard_spiking", true);

    private static final ForgeConfigSpec.IntValue GLASS_SHARD_DAMAGE = BUILDER
            .comment("How much damage Glass Shards deal on consumption.")
            .defineInRange("glass_shard_damage", 5, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue MIN_CENTRIFUGE_SPEED = BUILDER
            .comment("The slowest speed you can process items with a centrifuge.")
            .defineInRange("min_centrifuge_speed", 1d, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue MAX_CENTRIFUGE_SPEED = BUILDER
            .comment("The fastest speed you can process items with a centrifuge.")
            .defineInRange("max_centrifuge_speed", 8d, 0, Double.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean allowGlassShardSpiking;
    public static int glassShardDamage;
    public static double minCentrifugeSpeed;
    public static double maxCentrifugeSpeed;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        allowGlassShardSpiking = ALLOW_GLASS_SHARD_SPIKING.get();
        glassShardDamage = GLASS_SHARD_DAMAGE.get();
        minCentrifugeSpeed = MIN_CENTRIFUGE_SPEED.get();
        maxCentrifugeSpeed = MAX_CENTRIFUGE_SPEED.get();
    }
}
