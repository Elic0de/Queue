package com.github.elic0de.queue;

import com.github.elic0de.queue.config.Locales;
import com.github.elic0de.queue.config.Settings;
import com.github.elic0de.queue.listener.EventListener;
import net.william278.annotaml.Annotaml;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public final class Queue extends JavaPlugin {

    private static Queue instance;
    private Settings settings;
    private Locales locales;

    private QueueManager queueManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        final AtomicBoolean initialized = new AtomicBoolean(true);
        initialized.set(reload());

        new EventListener();

        queueManager = new QueueManager();
    }

    public boolean reload() {
        try {
            // Load settings
            this.settings = Annotaml.create(new File(getDataFolder(), "config.yml"), new Settings()).get();

            // Load locales from language preset default
            final Locales languagePresets = Annotaml.create(Locales.class, Objects.requireNonNull(getResource("locales/" + settings.language + ".yml"))).get();
            this.locales = Annotaml.create(new File(getDataFolder(), "messages_" + settings.language + ".yml"), languagePresets).get();

            return true;
        } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            getLogger().log(Level.SEVERE, "Failed to reload Queue config or messages file", e);
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Cancel remaining tasks
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }

    public Settings getSettings() {
        return settings;
    }

    public Locales getLocales() {
        return locales;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public static Queue getInstance() {
        return instance;
    }
}
