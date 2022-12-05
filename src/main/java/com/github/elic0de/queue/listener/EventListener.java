package com.github.elic0de.queue.listener;

import com.github.elic0de.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    private final Queue plugin = Queue.getInstance();

    public EventListener() {
        Bukkit.getPluginManager().registerEvents(this, Queue.getInstance());
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        plugin.getQueueManager().addQueue(event.getPlayer());
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent event) {
        plugin.getQueueManager().removeQueue(event.getPlayer());
    }
}
