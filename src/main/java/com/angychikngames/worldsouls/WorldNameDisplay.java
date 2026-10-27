package com.angychikngames.worldsouls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.getMinecraft();
        net.minecraft.server.integrated.IntegratedServer server = minecraft.getIntegratedServer();
        boolean ready = minecraft.world != null && minecraft.player != null
                && minecraft.currentScreen == null && !minecraft.isGamePaused()
                && minecraft.getRenderViewEntity() == minecraft.player
                && minecraft.world.isBlockLoaded(minecraft.player.getPosition());
        updateArrival(server, server == null ? "" : server.getWorld(0).getWorldInfo().getWorldName(), ready);
    }

    @SubscribeEvent
    public void onPauseMenu(GuiScreenEvent.DrawScreenEvent.Post event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (!(event.getGui() instanceof GuiIngameMenu) || minecraft.getIntegratedServer() == null) {
            return;
        }
        String name = minecraft.getIntegratedServer().getWorld(0).getWorldInfo().getWorldName();
        int availableWidth = Math.max(0, event.getGui().width - 24);
        if (minecraft.fontRenderer.getStringWidth(name) > availableWidth) {
            name = minecraft.fontRenderer.trimStringToWidth(name, Math.max(0, availableWidth - minecraft.fontRenderer.getStringWidth("..."))) + "...";
        }
        minecraft.fontRenderer.drawStringWithShadow(name, (event.getGui().width - minecraft.fontRenderer.getStringWidth(name)) / 2.0F, 16, 0xFFBDB7AA);
    }

    @SubscribeEvent
    public void onTitleRender(RenderGameOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (currentWorld == null || worldName.isEmpty() || readyTicks < 10
                || titleTicks == 0 || titleTicks >= 70 || minecraft.currentScreen != null
                || minecraft.gameSettings.hideGUI) {
            return;
        }
        int color = titleColor(event.getPartialTicks());
        if (color == 0) {
            return;
        }
        ScaledResolution resolution = event.getResolution();
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        float scale = Math.min(2.0F, (width - 32.0F) / Math.max(1, minecraft.fontRenderer.getStringWidth(worldName)));
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2.0F, height / 3.0F, 0);
        GlStateManager.scale(scale, scale, 1.0F);
        GlStateManager.enableBlend();
        minecraft.fontRenderer.drawStringWithShadow(worldName, -minecraft.fontRenderer.getStringWidth(worldName) / 2.0F, 0, color);
        GlStateManager.popMatrix();
    }
}
