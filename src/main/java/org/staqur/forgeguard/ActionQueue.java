package org.staqur.forgeguard;

import java.util.LinkedList;
import java.util.Queue;

public class ActionQueue {
    private static final Queue<Runnable> actions = new LinkedList<>();
    private static boolean initialized = false;

    public static void initialize() {
        initialized = true;
        System.out.println("DEBUG: Running deferred actions...");
        while (!actions.isEmpty()) {
            actions.poll().run();
        }
        System.out.println("DEBUG: All deferred actions executed.");
    }

    public static void enqueue(Runnable action) {
        if (initialized) {
            // Execute the action immediately if already initialized
            action.run();
        } else {
            // Otherwise, add it to the queue
            actions.add(action);
            System.out.println("DEBUG: Action added to queue.");
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
