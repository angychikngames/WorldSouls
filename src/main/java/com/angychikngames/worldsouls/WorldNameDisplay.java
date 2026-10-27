package com.angychikngames.worldsouls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

public class WorldNameDisplay {
    private static Object currentWorld;
    private static String worldName = "";
    private static int readyTicks;
    private static int titleTicks;

    private static void updateArrival(Object world, String name, boolean ready) {
        if (world != currentWorld) {
            currentWorld = world;
            readyTicks = 0;
            titleTicks = 0;
        }
        worldName = name;
        if (world == null || !ready) {
            readyTicks = 0;
            return;
        }
        if (readyTicks < 10) {
            readyTicks++;
        } else if (titleTicks < 70) {
            titleTicks++;
        }
    }

    private static int titleColor(float partialTick) {
        float time = titleTicks - 1 + partialTick;
        float opacity = Math.min(time / 15.0F, (70.0F - time) / 20.0F);
        int alpha = (int) (255.0F * Math.max(0.0F, Math.min(1.0F, opacity)));
        return alpha < 4 ? 0 : (alpha << 24) | 0xEFE6D2;
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(WorldNameDisplay::onClientTick);
        NeoForge.EVENT_BUS.addListener(WorldNameDisplay::onPauseMenu);
        NeoForge.EVENT_BUS.addListener(WorldNameDisplay::onTitleRender);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        var server = minecraft.getSingleplayerServer();
        boolean ready = minecraft.level != null && minecraft.player != null
                && minecraft.gui.screen() == null && !minecraft.isPaused()
                && minecraft.getCameraEntity() == minecraft.player
                && minecraft.level.hasChunkAt(minecraft.player.blockPosition())
                && minecraft.gui.overlay() == null;
        updateArrival(server, server == null ? "" : server.getWorldData().getLevelName(), ready);
    }

    private static void onPauseMenu(ScreenEvent.Render.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!(event.getScreen() instanceof PauseScreen) || minecraft.getSingleplayerServer() == null) {
            return;
        }
        String name = minecraft.getSingleplayerServer().getWorldData().getLevelName();
        int availableWidth = Math.max(0, event.getScreen().width - 24);
        if (minecraft.font.width(name) > availableWidth) {
            name = minecraft.font.plainSubstrByWidth(name, Math.max(0, availableWidth - minecraft.font.width("..."))) + "...";
        }
        event.getGuiGraphics().centeredText(minecraft.font, name, event.getScreen().width / 2, 16, 0xFFBDB7AA);
    }

    private static void onTitleRender(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (currentWorld == null || worldName.isEmpty() || readyTicks < 10
                || titleTicks == 0 || titleTicks >= 70 || minecraft.gui.screen() != null
                || minecraft.gui.hud.isHidden()) {
            return;
        }
        int color = titleColor(event.getPartialTick().getGameTimeDeltaPartialTick(false));
        if (color == 0) {
            return;
        }
        var graphics = event.getGuiGraphics();
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        float scale = Math.min(2.0F, (width - 32.0F) / Math.max(1, minecraft.font.width(worldName)));
        graphics.pose().pushMatrix();
        graphics.pose().translate(width / 2.0F, height / 3.0F);
        graphics.pose().scale(scale, scale);
        graphics.centeredText(minecraft.font, worldName, 0, 0, color);
        graphics.pose().popMatrix();
    }
}
