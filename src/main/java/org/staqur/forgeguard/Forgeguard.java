package org.staqur.forgeguard;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.staqur.modblock.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("forgeguard")
public class Forgeguard {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "forgeguard";
    @SubscribeEvent
    public void ForgeGuardMain() {
        LOGGER.info(("Initializing ForgeGuard"));
        //Lifecycle
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //Forge Handlers
        modEventBus.addListener(event -> ModNetwork.register());

        //Client Specific
        if (net.minecraftforge.fml.loading.FMLLoader.getDist().isClient()) {
            MinecraftForge.EVENT_BUS.register(ClientSetup.class);
        }


    }


}
