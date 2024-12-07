package cc.rexsystems.rexChat.commands;

import cc.rexsystems.rexChat.RexChat;
import cc.rexsystems.rexChat.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabCompleter {
    protected final RexChat plugin;
    
    public BaseCommand(RexChat plugin) {
        this.plugin = plugin;
    }
    
    protected void sendMessage(CommandSender sender, String message) {
        String prefix = plugin.getConfigManager().getConfig().getString("messages.prefix", "");
        message = message.replace("%rc_prefix%", prefix);
        sender.sendMessage(ColorUtils.parseComponent(message));
    }
    
    protected boolean hasPermission(CommandSender sender, String permission) {
        String fullPermission = "rexchat." + permission;
        if (!sender.hasPermission(fullPermission)) {
            String noPermMsg = plugin.getConfigManager().getConfig()
                .getString("messages.no-permission", "%rc_prefix%&#ff0000You don't have permission to use this command.");
            sendMessage(sender, noPermMsg);
            return false;
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, 
                                    @NotNull String alias, String[] args) {
        return null;
    }
} 