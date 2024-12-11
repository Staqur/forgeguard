package org.staqur.modblock;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
@Mod.EventBusSubscriber
public class ModHashCommand {
    @SubscribeEvent

    public static void onRegisterCommands(RegisterCommandsEvent event) {
        System.out.println("DEBUG: Registering /modhashes command");
        event.getDispatcher().register(
                Commands.literal("modhashes")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            String playerName = player.getGameProfile().getName();
                            System.out.println("DEBUG: /modhashes executed by player: " + playerName);

                            Map<String, String> clientMods = ServerData.CLIENT_MOD_HASHES.get(playerName);
                            if (clientMods != null) {
                                clientMods.forEach((modId, hash) -> {

                                    System.out.println("DEBUG: Mod: " + modId + " -> Hash: " + hash);
                                    context.getSource().sendSuccess(new TextComponent(modId + " -> " + hash), false);
                                });
                            } else {
                                System.out.println("DEBUG: No client mod data found for player: " + playerName);
                                context.getSource().sendSuccess(new TextComponent("No client mod data available."), false);
                            }

                            return 1;
                        })
        );
    }
}
