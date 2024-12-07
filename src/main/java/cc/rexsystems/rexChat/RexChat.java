package cc.rexsystems.rexChat;

import cc.rexsystems.rexChat.chat.ChatManager;
import cc.rexsystems.rexChat.commands.ChatCommandManager;
import cc.rexsystems.rexChat.config.ConfigManager;
import cc.rexsystems.rexChat.data.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RexChat extends JavaPlugin {
    private static RexChat instance;
    private ConfigManager configManager;
    private ChatCommandManager commandManager;
    private ChatManager chatManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;
        
        // Initialize configurations
        this.configManager = new ConfigManager(this);
        this.dataManager = new DataManager(this);
        this.chatManager = new ChatManager(this);
        this.commandManager = new ChatCommandManager(this);
        
        // Load configurations and commands
        configManager.loadConfigs();
        commandManager.loadCommands();
        
        getLogger().info("RexChat has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RexChat has been disabled!");
    }

    public static RexChat getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ChatCommandManager getCommandManager() {
        return commandManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
