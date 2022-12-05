package com.github.elic0de.queue.config;

import net.william278.annotaml.YamlFile;
import net.william278.annotaml.YamlKey;

/**
 * Plugin settings, read from config.yml
 */
@YamlFile(header = """
        ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
        ┃       Queue Config           ┃
        ┃    Developed by Elic0de      ┃
        ┗╸━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
        """,

        versionField = "config_version", versionNumber = 11)
public class Settings {

    // Top-level settings
    public String language = "ja-jp";

    @YamlKey("send_to_server")
    public String sendToServer = "main";

    @YamlKey("delay")
    public int delay = 1;

}
