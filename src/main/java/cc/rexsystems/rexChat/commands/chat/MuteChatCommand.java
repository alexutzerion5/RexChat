package cc.rexsystems.rexChat.commands.chat;

import cc.rexsystems.rexChat.RexChat;
import cc.rexsystems.rexChat.commands.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MuteChatCommand extends BaseCommand {
    public MuteChatCommand(RexChat plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                           @NotNull String label, String[] args) {
        if (!hasPermission(sender, "mute")) {
            return true;
        }

        if (!(sender instanceof Player player)) {
            sendMessage(sender, "%rc_prefix%&cThis command can only be used by players!");
            return true;
        }

        plugin.getChatManager().toggleMute(player);
        return true;
    }
} 