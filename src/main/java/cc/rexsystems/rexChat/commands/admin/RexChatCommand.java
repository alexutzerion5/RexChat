package cc.rexsystems.rexChat.commands.admin;

import cc.rexsystems.rexChat.RexChat;
import cc.rexsystems.rexChat.commands.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RexChatCommand extends BaseCommand {
    private final List<String> subCommands = List.of("reload");

    public RexChatCommand(RexChat plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                           @NotNull String label, String[] args) {
        if (!hasPermission(sender, "admin")) {
            return true;
        }

        if (args.length < 1) {
            String usage = plugin.getConfigManager().getConfig()
                .getString("messages.reload-usage", "%rc_prefix%&cUsage: /%cmd% reload")
                .replace("%cmd%", label);
            sendMessage(sender, usage);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            
            plugin.getConfigManager().loadConfigs();
            
            
            plugin.getCommandManager().loadCommands();
            
            String reloadMsg = plugin.getConfigManager().getConfig()
                .getString("messages.reload-success", "&aConfiguration reloaded successfully!");
            sendMessage(sender, reloadMsg);
            return true;
        }

        String unknownCmd = plugin.getConfigManager().getConfig()
            .getString("messages.command-not-found", "&cCommand not found.");
        sendMessage(sender, unknownCmd);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                    @NotNull String alias, String[] args) {
        if (args.length == 1 && hasPermission(sender, "admin")) {
            return subCommands.stream()
                .filter(cmd -> cmd.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
} 