package com.angychikngames.worldsouls;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
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

    private static void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.getGui() instanceof CreateWorldScreen)) {
            return;
        }
        CreateWorldScreen screen = (CreateWorldScreen) event.getGui();
        for (IGuiEventListener listener : screen.children()) {
            if (listener instanceof TextFieldWidget) {
                TextFieldWidget name = (TextFieldWidget) listener;
                if (name.getMessage().equals(new TranslationTextComponent("selectWorld.enterName"))) {
                    event.addWidget(new DiceButton(screen, name));
                    break;
                }
            }
        }
    }

    private static void onScreenRender(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.getGui() instanceof CreateWorldScreen) {
            for (IGuiEventListener listener : event.getGui().children()) {
                if (listener instanceof DiceButton) {
                    ((DiceButton) listener).updatePosition();
                }
            }
        }
    }

    private static class DiceButton extends Button {
        private final TextFieldWidget name;

        private DiceButton(CreateWorldScreen screen, TextFieldWidget name) {
            super(0, 0, 20, 20, new StringTextComponent(""), button -> {
                String next;
                do {
                    next = WorldNameGenerator.generate(RANDOM);
                } while (next.equals(name.getValue()));
                name.setValue(next);
            }, (button, matrix, mouseX, mouseY) -> screen.renderTooltip(matrix,
                    new TranslationTextComponent("worldsouls.generate"), mouseX, mouseY));
            this.name = name;
            updatePosition();
        }

        private void updatePosition() {
            visible = name.isVisible();
            active = visible && name.active;
            x = name.x + name.getWidth() + 4;
            y = name.y + (name.getHeight() - getHeight()) / 2;
        }

        @Override
        protected IFormattableTextComponent createNarrationMessage() {
            return new TranslationTextComponent("worldsouls.generate");
        }

        @Override
        public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTick) {
            super.renderButton(matrix, mouseX, mouseY, partialTick);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getTextureManager().bind(DICE_ICON);
            AbstractGui.blit(matrix, x + 2, y + 2, 0.0F, 0.0F, 16, 16, 16, 16);
        }
    }
}
