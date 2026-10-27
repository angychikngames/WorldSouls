package com.angychikngames.worldsouls;

import java.util.Random;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;

public class ClientEvents {
    private static final Random RANDOM = new Random();
    private static final ResourceLocation DICE_ICON = new ResourceLocation("worldsouls", "textures/gui/dice.png");

    public static void register() {
        LogManager.getLogger("World Souls").info("[World Souls] Shaking the cosmic dice. Please stand back.");
        MinecraftForge.EVENT_BUS.addListener(ClientEvents::onScreenInit);
        MinecraftForge.EVENT_BUS.addListener(ClientEvents::onScreenRender);
        WorldNameDisplay.register();
    }

    private static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof CreateWorldScreen)) {
            return;
        }
        CreateWorldScreen screen = (CreateWorldScreen) event.getScreen();
        for (GuiEventListener listener : event.getListenersList()) {
            if (listener instanceof EditBox) {
                EditBox name = (EditBox) listener;
                if (name.getMessage().equals(Component.translatable("selectWorld.enterName"))) {
                    event.addListener(new DiceButton(screen, name));
                    break;
                }
            }
        }
    }

    private static void onScreenRender(ScreenEvent.Render.Pre event) {
        if (event.getScreen() instanceof CreateWorldScreen) {
            for (GuiEventListener listener : event.getScreen().children()) {
                if (listener instanceof DiceButton) {
                    ((DiceButton) listener).updatePosition();
                }
            }
        }
    }

    private static class DiceButton extends Button {
        private final CreateWorldScreen screen;
        private final EditBox name;

        private DiceButton(CreateWorldScreen screen, EditBox name) {
            super(0, 0, 20, 20, Component.empty(), button -> {
                String next;
                do {
                    next = WorldNameGenerator.generate(RANDOM);
                } while (next.equals(name.getValue()));
                name.setValue(next);
            }, supplier -> Component.translatable("worldsouls.generate"));
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
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            super.renderWidget(graphics, mouseX, mouseY, partialTick);
            graphics.blit(DICE_ICON, getX() + 2, getY() + 2, 0.0F, 0.0F, 16, 16, 16, 16);
        }
    }
}
