package com.github.elic0de.queue;

import com.github.elic0de.queue.player.QueuePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class QueueManager {

    private final Queue plugin = Queue.getInstance();
    private final Map<Player,QueuePlayer> queuePlayers;

    public QueueManager() {
        queuePlayers = new HashMap<>();
        checkQueueTask();
    }

    public void addQueue(Player queuePlayer) {
        queuePlayers.put(queuePlayer, new QueuePlayer(queuePlayer, queuePlayer.isEmpty() ? 1 : getNextPosition()));
        sendAllPlayerPosition();
    }

    public void removeQueue(Player player) {
        queuePlayers.remove(player);
        sendAllPlayerPosition();
    }

    public void sendAllPlayerPosition() {
        queuePlayers.values().forEach(QueuePlayer::sendPlayerPosition);
    }

    public void checkQueue() {
        queuePlayers.values().stream().min(Comparator.comparingInt(QueuePlayer::getPosition)).ifPresent(QueuePlayer::sendToServer);
    }

    public void checkQueueTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                checkQueue();
            }
        }.runTaskTimer(plugin, plugin.getSettings().delay, 20);
    }

    private int getNextPosition() {
        return queuePlayers.values().stream().max(Comparator.comparingInt(QueuePlayer::getPosition)).get().getPosition() + 1;
    }

}
