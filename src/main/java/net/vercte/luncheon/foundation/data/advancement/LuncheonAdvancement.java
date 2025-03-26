package net.vercte.luncheon.foundation.data.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;
import net.vercte.luncheon.Luncheon;
import net.vercte.luncheon.foundation.data.LuncheonAdvancements;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LuncheonAdvancement {
    static final String LANG = "advancement." + Luncheon.ID + ".";
    static final String SECRET_SUFFIX = "\n§7(Hidden Advancement)";

    private final String id;
    private final ItemLike icon;
    private final FrameType frameType;

    private final Advancement.Builder builder;
    private final LuncheonAdvancement parent;

    private final boolean announces;
    private final boolean toasts;
    private final boolean hidden;

    private String title;
    private String description;

    Advancement datagenResult;

    public LuncheonAdvancement(Builder builder) {
        this.builder = Advancement.Builder.advancement();

        this.id = builder.id;
        this.icon = builder.icon;
        this.parent = builder.parent;
        this.frameType = FrameType.byName(builder.frame);
        this.announces = builder.announces;
        this.toasts = builder.toasts;
        this.hidden = builder.hidden;

        this.title = builder.name;
        this.description = builder.description;

        if (this.hidden)
            description += SECRET_SUFFIX;

        this.builder.display(this.icon, Component.translatable(titleKey()),
                Component.translatable(descriptionKey()).withStyle(s -> s.withColor(0xDBA213)),
                id.equals("root") ? LuncheonAdvancements.getBackground() : null,
                this.frameType, this.toasts, this.announces, this.hidden);

        LuncheonAdvancements.ENTRIES.add(this);
    }

    public void save(Consumer<Advancement> t) {
        if (parent != null)
            builder.parent(parent.datagenResult);
        datagenResult = this.builder.save(t, Luncheon.asResource(this.id).toString());
    }

    public String titleKey() {
        return LANG + id;
    }

    public String descriptionKey() {
        return LANG + id + ".desc";
    }

    public void provideLang(BiConsumer<String, String> consumer) {
        consumer.accept(titleKey(), title);
        consumer.accept(descriptionKey(), description);
    }

    public static class Builder {
        public final String id;
        public final ItemLike icon;

        public LuncheonAdvancement parent;

        public boolean announces = false;
        public boolean toasts = true;
        public boolean hidden = false;

        public String frame = "task";
        public String name;
        public String description;

        public Builder(String id, ItemLike icon) {
            this.id = id;
            this.icon = icon;
        }

        public Builder after(LuncheonAdvancement after) {
            this.parent = after;
            return this;
        }

        public Builder goal() {
            this.announce();
            this.frame = "goal";
            return this;
        }

        public Builder challenge() {
            this.announce();
            this.frame = "challenge";
            return this;
        }

        public LuncheonAdvancement build() {
            return new LuncheonAdvancement(this);
        }

        public Builder silent() {
            this.toasts = false;
            return this;
        }

        public Builder announce() {
            this.announces = true;
            return this;
        }

        public Builder secret() {
            this.hidden = true;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }
    }
}
