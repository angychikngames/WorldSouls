package com.angychikngames.worldsouls;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;

@Mod(modid = "worldsouls", name = "World Souls", version = "1.0.0",
        acceptedMinecraftVersions = "[1.12.2]", clientSideOnly = true, acceptableRemoteVersions = "*")
public class WorldSouls {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LogManager.getLogger("World Souls").info("[World Souls] Shaking the cosmic dice. Please stand back.");
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(new WorldNameDisplay());
    }
}
