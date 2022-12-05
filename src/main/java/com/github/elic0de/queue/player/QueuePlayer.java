package com.github.elic0de.queue.player;

import com.github.elic0de.queue.Queue;
import com.github.elic0de.queue.config.Settings;
import de.themoep.minedown.MineDown;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class QueuePlayer {

    private final Player player;
    private final int position;

    public QueuePlayer(final Player player, final int position) {
        this.player = player;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    private void sendMessage(MineDown mineDown) {
        player.spigot().sendMessage(mineDown.toComponent());
    }

    public void sendPlayerPosition() {
        Queue.getInstance().getLocales().getLocale("position").ifPresent(this::sendMessage);
    }

    public void sendToServer() {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(Queue.getInstance().getSettings().sendToServer);
            player.sendPluginMessage(Queue.getInstance(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception e) {
            Queue.getInstance().getLocales().getLocale("sending_player_failed").ifPresent(this::sendMessage);
        }
        Queue.getInstance().getLocales().getLocale("sending_player").ifPresent(this::sendMessage);
    }
}
