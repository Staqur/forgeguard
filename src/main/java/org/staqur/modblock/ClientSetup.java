package org.staqur.modblock;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.PacketDistributor;
import org.staqur.forgeguard.ActionQueue;
import org.staqur.forgeguard.Forgeguard;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ClientSetup {
    @SubscribeEvent
    public static void onLoggedIn(ClientPlayerNetworkEvent.LoggedInEvent event) {
        System.out.println("DEBUG: onLogIn Triggered");
        //Sends users hashes to server
        Map<String, String> modHashes = new HashMap<>();
        ModList.get().getMods().forEach(mod -> {
            String modId = mod.getModId();
            String modVersion = mod.getVersion().toString();
            String hash = Integer.toHexString((modId + modVersion).hashCode());
            modHashes.put(modId, hash);
        });
        ActionQueue.enqueue(() -> {
            System.out.println("DEBUG Sending mod list to server: " + modHashes);
            ModNetwork.getChannel().send(PacketDistributor.SERVER.noArg(), new ClientModListPacket(modHashes));
        });
    }


}
