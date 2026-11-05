package com.enderdevelopment.verdantepoch.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

/**
 * The quest line viewer. Shows the story of the Verdant Epoch as a
 * sequence of chapters that the player unlocks by adventuring and by
 * defeating the Verdant Titan.
 */
public class QuestBookScreen extends Screen {

    private static final List<Chapter> CHAPTERS = List.of(
            new Chapter("I. The Sleeping Epoch", new String[]{
                    "The world stirs after a thousand years of silence.",
                    "Listen to the wind and watch the leaves turn. Collect",
                    "a Fire Staff and a Frost Wand to begin your path as",
                    "an apprentice mage."}),
            new Chapter("II. The Living World", new String[]{
                    "The land breathes around you: embers drift at night",
                    "and nature slowly regrows what man has carved away.",
                    "Gather your wits, for something ancient now watches."}),
            new Chapter("III. Awakening the Titan", new String[]{
                    "Deep beneath the old roots slumbers the Verdant",
                    "Titan, a god of the earth. There is no summoning,",
                    "only the hunt. Tracking it is the true test of a",
                    "mage."}),
            new Chapter("IV. The God's Fall", new String[]{
                    "Slay the Verdant Titan to calm the Epoch. Its fall",
                    "scatters verdant essence across the land, completing",
                    "your journey and binding the world to your will."})
    );

    private int page = 0;

    public QuestBookScreen() {
        super(Text.literal("Verdant Epoch — Quest Line"));
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("< Prev"), b -> {
                    if (page > 0) page--;
                })
                .dimensions(cx - 120, this.height - 40, 80, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Next >"), b -> {
                    if (page < CHAPTERS.size() - 1) page++;
                })
                .dimensions(cx + 40, this.height - 40, 80, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        Chapter chapter = CHAPTERS.get(page);

        context.drawCenteredTextWithShadow(this.textRenderer, chapter.title,
                this.width / 2, 40, 0xFFFFD54F);

        int y = 80;
        for (String line : chapter.body) {
            context.drawCenteredTextWithShadow(this.textRenderer, line,
                    this.width / 2, y, 0xFFFFFFFF);
            y += 12;
        }

        context.drawCenteredTextWithShadow(this.textRenderer,
                "Chapter " + (page + 1) + " of " + CHAPTERS.size(),
                this.width / 2, this.height - 70, 0xFF9E9E9E);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private record Chapter(String title, String[] body) {
    }
}
