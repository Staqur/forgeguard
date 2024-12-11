package org.staqur.modblock;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
@SuppressWarnings("unused")
public class ClientModListPacket {
    private final Map<String, String> modHashes;

    public ClientModListPacket(Map<String, String> modHashes) {
        this.modHashes = modHashes;
    }

    public static void encode(ClientModListPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.modHashes.size());
        packet.modHashes.forEach((key, value) -> {
            buffer.writeUtf(key);
            buffer.writeUtf(value);
        });
    }

    public static ClientModListPacket decode(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        Map<String, String> modHashes = new HashMap<>();
        for (int i = 0; i < size; i++) {
            modHashes.put(buffer.readUtf(), buffer.readUtf());
        }
        return new ClientModListPacket(modHashes);
    }

    public static class Handler {
        public static void handle(ClientModListPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getSender() == null) {
                    System.out.println("Packet received but no sender found");
                    return;
                }
                // Handle the received mod list on the server
                String playerName = context.getSender().getGameProfile().getName();
                ServerData.CLIENT_MOD_HASHES.put(playerName, packet.modHashes);

                System.out.println("Current CLIENT HASHES: " + ServerData.CLIENT_MOD_HASHES);


            });
            context.setPacketHandled(true);
        }
    }
}
