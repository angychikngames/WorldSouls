package com.angychikngames.worldsouls;

import com.mojang.logging.LogUtils;
import java.util.Random;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(value = "worldsouls", dist = Dist.CLIENT)
public class WorldSouls {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Random RANDOM = new Random();
    private static final Identifier DICE_ICON =
            Identifier.fromNamespaceAndPath("worldsouls", "textures/gui/dice.png");

    public WorldSouls() {
        LOGGER.info("[World Souls] Shaking the cosmic dice. Please stand back.");
        NeoForge.EVENT_BUS.addListener(WorldSouls::onScreenInit);
        NeoForge.EVENT_BUS.addListener(WorldSouls::onScreenRender);
        WorldNameDisplay.register();
    }

    private static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof CreateWorldScreen screen)) {
            return;
        }
        for (var listener : event.getListenersList()) {
            if (listener instanceof EditBox name
                    && name.getMessage().equals(Component.translatable("selectWorld.enterName"))) {
                event.addListener(new DiceButton(screen, name));
                break;
            }
        }
    }

    private static void onScreenRender(ScreenEvent.Render.Pre event) {
        if (event.getScreen() instanceof CreateWorldScreen screen) {
            for (var listener : screen.children()) {
                if (listener instanceof DiceButton dice) {
                    dice.updatePosition();
                }
            }
        }
    }

    private static class DiceButton extends Button {
        private final CreateWorldScreen screen;
        private final EditBox name;

        private DiceButton(CreateWorldScreen screen, EditBox name) {
            super(0, 0, 20, 20, Component.translatable("worldsouls.generate"),
                    button -> {
                        String next;
                        do {
                            next = WorldNameGenerator.generate(RANDOM);
                        } while (next.equals(name.getValue()));
                        name.setValue(next);
                    }, DEFAULT_NARRATION);
            this.screen = screen;
            this.name = name;
            setTooltip(Tooltip.create(Component.translatable("worldsouls.generate")));
            updatePosition();
        }

        private void updatePosition() {
            visible = screen.children().contains(name) && name.visible;
            active = visible && name.active;
            setX(name.getX() + name.getWidth() + 4);
            setY(name.getY() + (name.getHeight() - getHeight()) / 2);
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
            extractDefaultSprite(graphics);
            graphics.blit(DICE_ICON, getX() + 2, getY() + 2, getX() + 18, getY() + 18,
                    0.0F, 1.0F, 0.0F, 1.0F);
        }
    }
}
