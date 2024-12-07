package cc.rexsystems.rexChat.commands;

import cc.rexsystems.rexChat.RexChat;
import cc.rexsystems.rexChat.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DynamicCommand extends BaseCommand {
    private final CommandConfig config;

    public DynamicCommand(RexChat plugin, CommandConfig config) {
        super(plugin);
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                           @NotNull String label, String[] args) {
        if (!config.getPermission().isEmpty() && !hasPermission(sender, config.getPermission())) {
            return true;
        }

        String prefix = plugin.getConfigManager().getConfig().getString("messages.prefix", "");
        
        if (config.hasMessageList()) {
            for (String line : config.getMessageList()) {
                String processedLine = line.replace("%rc_prefix%", prefix);
                sender.sendMessage(ColorUtils.parseComponent(processedLine));
            }
            return true;
        }

        String message = config.getMessage();
        if (message != null && !message.isEmpty()) {
            message = message.replace("%rc_prefix%", prefix);
            sender.sendMessage(ColorUtils.parseComponent(message));
            return true;
        }

        sender.sendMessage(ColorUtils.parseComponent(prefix + "&#ff0000No message configured for this command."));
        return true;
    }
} 