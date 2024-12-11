package org.staqur.modblock;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.Map;
public class ModHashUtils {

    public static Map<String, String> getModHashes() {
        Map<String, String> modHashes = new HashMap<>();
        ModList.get().getMods().forEach(mod -> {
            String modId = mod.getModId();
            String modVersion = mod.getVersion().toString();
            String hash = Integer.toHexString((modId + modVersion).hashCode());
            modHashes.put(modId, hash);
        });
        return modHashes;
    }
}
