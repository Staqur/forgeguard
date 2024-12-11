package org.staqur.modblock;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class LoginHandler {
    private static final Map<String, String> BLOCKED_HASHES = Map.of(

    );

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            Map<String, String> modHashes = ModHashUtils.getModHashes();

            for (Map.Entry<String, String> entry : BLOCKED_HASHES.entrySet()) {
                if (BLOCKED_HASHES.containsKey(entry.getKey())
                        && BLOCKED_HASHES.get(entry.getKey()).equals(entry.getValue())) {
                    player.connection.disconnect(new TextComponent("Blocked mod detected: " + entry.getKey()));
                    return;
                }
            }
        }
    }
}
