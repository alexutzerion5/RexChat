package cc.rexsystems.rexChat.commands;

import org.bukkit.configuration.ConfigurationSection;
import java.util.List;

public class CommandConfig {
    private final String name;
    private final boolean enabled;
    private final String command;
    private final String message;
    private final List<String> messageList;
    private final List<String> aliases;
    private final String permission;

    public CommandConfig(String name, ConfigurationSection section) {
        this.name = name;
        this.enabled = section.getBoolean("enabled", true);
        this.command = section.getString("command", name);
        this.message = section.getString("message", "");
        this.messageList = section.getStringList("message-list");
        this.aliases = section.getStringList("aliases");
        this.permission = section.getString("permission", "");
    }

    public String getName() { return name; }
    public boolean isEnabled() { return enabled; }
    public String getCommand() { return command; }
    public String getMessage() { return message; }
    public List<String> getMessageList() { return messageList; }
    public boolean hasMessageList() { return !messageList.isEmpty(); }
    public List<String> getAliases() { return aliases; }
    public String getPermission() { return permission; }
} 