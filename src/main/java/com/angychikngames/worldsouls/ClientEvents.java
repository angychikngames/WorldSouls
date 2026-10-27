package com.angychikngames.worldsouls;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEvents {
    private static final Random RANDOM = new Random();
    private static final ResourceLocation DICE_ICON = new ResourceLocation("worldsouls", "textures/gui/dice.png");
    private static final Field NAME_FIELD = ObfuscationReflectionHelper.findField(GuiCreateWorld.class, "field_146333_g");
    private static final Field SAVED_NAME = ObfuscationReflectionHelper.findField(GuiCreateWorld.class, "field_146330_J");
    private static final Field MORE_OPTIONS = ObfuscationReflectionHelper.findField(GuiCreateWorld.class, "field_146344_y");
    private static final Method UPDATE_FOLDER = ObfuscationReflectionHelper.findMethod(GuiCreateWorld.class, "func_146314_g", void.class);
    private DiceButton dice;

    @SubscribeEvent
    public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.getGui() instanceof GuiCreateWorld)) {
            return;
        }
        GuiCreateWorld screen = (GuiCreateWorld) event.getGui();
        try {
            GuiTextField name = (GuiTextField) NAME_FIELD.get(screen);
            dice = new DiceButton(screen, name, event.getButtonList().get(0));
            event.getButtonList().add(dice);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("World Souls could not access the world name field", e);
        }
    }

    @SubscribeEvent
    public void onButtonPress(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.getButton() instanceof DiceButton) {
            ((DiceButton) event.getButton()).roll();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onScreenDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (dice != null && event.getGui() == dice.screen && dice.visible && dice.isMouseOver()) {
            dice.screen.drawHoveringText(I18n.format("worldsouls.generate"), event.getMouseX(), event.getMouseY());
        }
    }

    private static class DiceButton extends GuiButton {
        private final GuiCreateWorld screen;
        private final GuiTextField name;
        private final GuiButton create;

        private DiceButton(GuiCreateWorld screen, GuiTextField name, GuiButton create) {
            super(-873, name.x + name.getWidth() + 12, name.y, 20, 20, "");
            this.screen = screen;
            this.name = name;
            this.create = create;
        }

        private void roll() {
            String next;
            do {
                next = WorldNameGenerator.generate(RANDOM);
            } while (next.equals(name.getText()));
            name.setText(next);
            try {
                SAVED_NAME.set(screen, next);
                UPDATE_FOLDER.invoke(screen);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("World Souls could not update the world name", e);
            }
            create.enabled = true;
        }

        @Override
        public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTick) {
            try {
                visible = !MORE_OPTIONS.getBoolean(screen);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("World Souls could not check the world options screen", e);
            }
            x = name.x + name.getWidth() + 12;
            y = name.y;
            super.drawButton(minecraft, mouseX, mouseY, partialTick);
            if (visible) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                minecraft.getTextureManager().bindTexture(DICE_ICON);
                Gui.drawModalRectWithCustomSizedTexture(x + 2, y + 2, 0, 0, 16, 16, 16, 16);
            }
        }
    }
}

