package cc.rexsystems.rexChat.commands.chat;

import cc.rexsystems.rexChat.RexChat;
import cc.rexsystems.rexChat.commands.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearChatCommand extends BaseCommand {
    public ClearChatCommand(RexChat plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                           @NotNull String label, String[] args) {
        if (!hasPermission(sender, "clear")) {
            return true;
        }

        String playerName = sender instanceof Player ? sender.getName() : "Console";
        plugin.getChatManager().clearChat(playerName);
        return true;
    }
}