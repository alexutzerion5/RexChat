package cc.rexsystems.rexChat.commands;

import cc.rexsystems.rexChat.RexChat;
import cc.rexsystems.rexChat.commands.admin.RexChatCommand;
import cc.rexsystems.rexChat.commands.chat.ClearChatCommand;
import cc.rexsystems.rexChat.commands.chat.MuteChatCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatCommandManager {
    private final RexChat plugin;
    private final Map<String, CommandConfig> commands;
    private CommandMap commandMap;

    public ChatCommandManager(RexChat plugin) {
        this.plugin = plugin;
        this.commands = new HashMap<>();
        setupCommandMap();
    }

    private void setupCommandMap() {
        try {
            Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getPluginManager());
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to access command map: " + e.getMessage());
        }
    }

    private PluginCommand createPluginCommand(String name, List<String> aliases) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, org.bukkit.plugin.Plugin.class);
            constructor.setAccessible(true);
            PluginCommand command = constructor.newInstance(name, plugin);
            command.setAliases(aliases);
            return command;
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to create command " + name + ": " + e.getMessage());
            return null;
        }
    }

    public void loadCommands() {
        commands.clear();
        
       
        ConfigurationSection commandsSection = plugin.getConfigManager().getConfig().getConfigurationSection("commands");
        if (commandsSection != null) {
            for (String key : commandsSection.getKeys(false)) {
                ConfigurationSection cmdSection = commandsSection.getConfigurationSection(key);
                if (cmdSection != null && cmdSection.getBoolean("enabled", true)) {
                    CommandConfig cmdConfig = new CommandConfig(key, cmdSection);
                    commands.put(key, cmdConfig);
                }
            }
        }

        
        registerCommands();
    }

    public void registerCommands() {
        // Register dynamic commands
        for (CommandConfig cmdConfig : commands.values()) {
            String commandName = cmdConfig.getCommand();
            PluginCommand existingCommand = plugin.getCommand(commandName);
            
            if (existingCommand != null) {
                // Command exists in plugin.yml, just set the executor
                existingCommand.setExecutor(new DynamicCommand(plugin, cmdConfig));
            } else {
                // Create and register dynamic command
                PluginCommand newCommand = createPluginCommand(commandName, cmdConfig.getAliases());
                if (newCommand != null) {
                    newCommand.setExecutor(new DynamicCommand(plugin, cmdConfig));
                    commandMap.register(plugin.getName().toLowerCase(), newCommand);
                }
            }
        }
        
        
        registerCommand("clearchat", new ClearChatCommand(plugin));
        registerCommand("mutechat", new MuteChatCommand(plugin));
        registerCommand("rexchat", new RexChatCommand(plugin));
    }
    
    private void registerCommand(String name, BaseCommand command) {
        PluginCommand pluginCommand = plugin.getCommand(name);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        }
    }

    public Map<String, CommandConfig> getCommands() {
        return commands;
    }
} 