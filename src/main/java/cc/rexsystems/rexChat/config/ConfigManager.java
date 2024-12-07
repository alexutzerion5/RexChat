package cc.rexsystems.rexChat.config;

import cc.rexsystems.rexChat.RexChat;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final RexChat plugin;
    private FileConfiguration config;

    public ConfigManager(RexChat plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }
} 