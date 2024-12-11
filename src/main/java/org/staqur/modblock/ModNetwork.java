package org.staqur.modblock;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.staqur.forgeguard.ActionQueue;
import org.staqur.forgeguard.Forgeguard;


public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel channel; // Lazy initialization

    public static void register() {
        System.out.println("DEBUG: ModNetwork.register() called");
        // Initialize the channel only when this method is called
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Forgeguard.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals

        );

        // Register your custom packets
        channel.registerMessage(
                0,
                ClientModListPacket.class,
                ClientModListPacket::encode,
                ClientModListPacket::decode,
                ClientModListPacket.Handler::handle
        );

        System.out.println("DEBUG: ModNetwork Channel registered");
        ActionQueue.initialize();
    }


    public static SimpleChannel getChannel() {
        System.out.println("DEBUG: ModNetwork.getChannel() called");
        if (channel == null) {
            throw new IllegalStateException("ModNetwork channel has not been registered yet!");
        }
        return channel;
    }
}
