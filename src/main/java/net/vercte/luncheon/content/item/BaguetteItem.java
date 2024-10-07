package net.vercte.luncheon.content.item;

import com.jozufozu.flywheel.util.AnimationTickHolder;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.equipment.extendoGrip.ExtendoGripInteractionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.vercte.luncheon.content.registry.LuncheonItems;
import net.vercte.luncheon.content.registry.LuncheonMisc;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.SwordItem;
import net.vercte.luncheon.mixin.LivingEntityInvoker;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Supplier;

@EventBusSubscriber
public class BaguetteItem extends SwordItem {
    public static final AttributeModifier singleRangeAttributeModifier =
            new AttributeModifier(UUID.fromString("2024cdb5-f529-4d04-9eae-6a0611f2ece7"), "Range modifier", 2,
                    AttributeModifier.Operation.ADDITION);
    private static final Supplier<Multimap<Attribute, AttributeModifier>> rangeModifier = Suppliers.memoize(() ->
            ImmutableMultimap.of(ForgeMod.BLOCK_REACH.get(), singleRangeAttributeModifier));

    private static DamageSource lastActiveDamageSource;

    public static final String BAGUETTE_MARKER = "luncheonBaguette";

    public BaguetteItem(Properties properties) {
        super(LuncheonMisc.BAGUETTE_TIER, 2, -2.4F, properties);
    }

    @SubscribeEvent
    public static void holdingBaguetteIncreasesRange(LivingTickEvent event) {
        if(!(event.getEntity() instanceof Player player))
            return;

        CompoundTag persistentData = player.getPersistentData();
        boolean holdingBaguette = LuncheonItems.BAGUETTE.isIn(player.getMainHandItem());
        boolean wasHoldingBaguette = persistentData.contains(BAGUETTE_MARKER);

        if (holdingBaguette != wasHoldingBaguette) {
            if (!holdingBaguette) {
                player.getAttributes()
                        .removeAttributeModifiers(rangeModifier.get());
                persistentData.remove(BAGUETTE_MARKER);
            } else {
                player.getAttributes()
                        .addTransientAttributeModifiers(rangeModifier.get());
                persistentData.putBoolean(BAGUETTE_MARKER, true);
            }
        }
    }

    @SubscribeEvent
    public static void addReachToJoiningPlayersHoldingExtendo(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CompoundTag persistentData = player.getPersistentData();

        if (persistentData.contains(BAGUETTE_MARKER))
            player.getAttributes()
                    .addTransientAttributeModifiers(rangeModifier.get());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void dontMissEntitiesWhenYouHaveHighReachDistance(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (mc.level == null || player == null)
            return;
        if (!isHoldingBaguette(player))
            return;
        if (mc.hitResult instanceof BlockHitResult && mc.hitResult.getType() != HitResult.Type.MISS)
            return;

        // Modified version of GameRenderer#getMouseOver
        double d0 = player.getAttribute(ForgeMod.BLOCK_REACH.get())
                .getValue();
        if (!player.isCreative())
            d0 -= 0.5f;
        Vec3 Vector3d = player.getEyePosition(AnimationTickHolder.getPartialTicks());
        Vec3 Vector3d1 = player.getViewVector(1.0F);
        Vec3 Vector3d2 = Vector3d.add(Vector3d1.x * d0, Vector3d1.y * d0, Vector3d1.z * d0);
        AABB AABB = player.getBoundingBox()
                .expandTowards(Vector3d1.scale(d0))
                .inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityraytraceresult =
                ProjectileUtil.getEntityHitResult(player, Vector3d, Vector3d2, AABB, (e) -> {
                    return !e.isSpectator() && e.isPickable();
                }, d0 * d0);
        if (entityraytraceresult != null) {
            Entity entity1 = entityraytraceresult.getEntity();
            Vec3 Vector3d3 = entityraytraceresult.getLocation();
            double d2 = Vector3d.distanceToSqr(Vector3d3);
            if (d2 < d0 * d0 || mc.hitResult == null || mc.hitResult.getType() == HitResult.Type.MISS) {
                mc.hitResult = entityraytraceresult;
                if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrame)
                    mc.crosshairPickEntity = entity1;
            }
        }
    }

    private static boolean isUncaughtClientInteraction(Entity entity, Entity target) {
        // Server ignores entity interaction further than 6m
        if (entity.distanceToSqr(target) < 24)
            return false;
        if (!entity.level().isClientSide)
            return false;
        return entity instanceof Player;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void notifyServerOfLongRangeAttacks(AttackEntityEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();
        if (!isUncaughtClientInteraction(entity, target))
            return;
        Player player = (Player) entity;
        if (isHoldingBaguette(player))
            AllPackets.getChannel().sendToServer(new ExtendoGripInteractionPacket(target));
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void notifyServerOfLongRangeInteractions(PlayerInteractEvent.EntityInteract event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();
        if (!isUncaughtClientInteraction(entity, target))
            return;
        Player player = (Player) entity;
        if (isHoldingBaguette(player))
            AllPackets.getChannel().sendToServer(new ExtendoGripInteractionPacket(target, event.getHand()));
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void notifyServerOfLongRangeSpecificInteractions(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();
        if (!isUncaughtClientInteraction(entity, target))
            return;
        Player player = (Player) entity;
        if (isHoldingBaguette(player))
            AllPackets.getChannel()
                    .sendToServer(new ExtendoGripInteractionPacket(target, event.getHand(), event.getLocalPos()));
    }

    public static boolean isHoldingBaguette(Player player) {
        return LuncheonItems.BAGUETTE.isIn(player.getMainHandItem());
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        itemStack.hurtAndBreak(8, livingEntity, (LivingEntity using) -> {
                using.broadcastBreakEvent(livingEntity.getUsedItemHand());
        });
        ((LivingEntityInvoker) livingEntity).callAddEatEffect(itemStack, level, livingEntity);
        if(livingEntity instanceof Player player) {
            player.getFoodData().eat(itemStack.getItem(), itemStack, player);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }
        livingEntity.gameEvent(GameEvent.EAT);
        return itemStack;
    }

}
