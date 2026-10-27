package com.angychikngames.worldsouls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

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
        MinecraftForge.EVENT_BUS.addListener(WorldNameDisplay::onClientTick);
        MinecraftForge.EVENT_BUS.addListener(WorldNameDisplay::onPauseMenu);
        MinecraftForge.EVENT_BUS.addListener(WorldNameDisplay::onTitleRender);
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        net.minecraft.client.server.IntegratedServer server = minecraft.getSingleplayerServer();
        boolean ready = minecraft.level != null && minecraft.player != null
                && minecraft.screen == null && !minecraft.isPaused()
                && minecraft.getCameraEntity() == minecraft.player
                && minecraft.level.hasChunkAt(minecraft.player.blockPosition())
                && minecraft.getOverlay() == null;
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
        event.getGuiGraphics().drawCenteredString(minecraft.font, name, event.getScreen().width / 2, 16, 0xFFBDB7AA);
    }

    private static void onTitleRender(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (currentWorld == null || worldName.isEmpty() || readyTicks < 10
                || titleTicks == 0 || titleTicks >= 70 || minecraft.screen != null
                || minecraft.options.hideGui) {
            return;
        }
        int color = titleColor(event.getPartialTick());
        if (color == 0) {
            return;
        }
        net.minecraft.client.gui.GuiGraphics graphics = event.getGuiGraphics();
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        float scale = Math.min(2.0F, (width - 32.0F) / Math.max(1, minecraft.font.width(worldName)));
        graphics.pose().pushPose();
        graphics.pose().translate(width / 2.0F, height / 3.0F, 0);
        graphics.pose().scale(scale, scale, 1.0F);
        graphics.drawCenteredString(minecraft.font, worldName, 0, 0, color);
        graphics.pose().popPose();
    }
}
