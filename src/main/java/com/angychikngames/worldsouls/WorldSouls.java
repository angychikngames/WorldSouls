package com.angychikngames.worldsouls;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod("worldsouls")
public class WorldSouls {
    public WorldSouls() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientEvents::register);
    }
}
