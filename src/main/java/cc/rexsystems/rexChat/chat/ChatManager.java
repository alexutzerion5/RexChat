package cc.rexsystems.rexChat.chat;

import cc.rexsystems.rexChat.RexChat;
import cc.rexsystems.rexChat.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {
    private final RexChat plugin;
    private boolean chatMuted;

    public ChatManager(RexChat plugin) {
        this.plugin = plugin;
        this.chatMuted = plugin.getDataManager().getData().getBoolean("chat.muted", false);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        
        if (chatMuted && !player.hasPermission("rexchat.bypass")) {
            String mutedMessage = plugin.getConfigManager().getConfig()
                .getString("chat-management.mute.muted-message", "%rc_prefix%&#ff0000The chat is currently muted.");
            sendMessage(player, mutedMessage);
            event.setCancelled(true);
        }
    }

    public void clearChat(String executor) {
        int lines = plugin.getConfigManager().getConfig().getInt("chat-management.clear.lines", 100);
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < lines; i++) {
                player.sendMessage(" ");
            }
        }

        String clearMessage = plugin.getConfigManager().getConfig()
            .getString("chat-management.clear.clear-message", "%rc_prefix%&#00ff00The chat has been cleared by {player}");
        broadcastMessage(clearMessage.replace("{player}", executor));
    }

    public void toggleMute(String executor) {
        chatMuted = !chatMuted;
        
        plugin.getDataManager().getData().set("chat.muted", chatMuted);
        plugin.getDataManager().saveData();
        
        String message = chatMuted ? 
            plugin.getConfigManager().getConfig().getString("chat-management.mute.mute-announcement", "%rc_prefix%&#ff0000The chat has been muted by {player}") :
            plugin.getConfigManager().getConfig().getString("chat-management.mute.unmute-announcement", "%rc_prefix%&#00ff00The chat has been unmuted by {player}");
        
        broadcastMessage(message.replace("{player}", executor));
    }

    private void sendMessage(Player player, String message) {
        String prefix = plugin.getConfigManager().getConfig().getString("messages.prefix", "");
        message = message.replace("%rc_prefix%", prefix);
        player.sendMessage(ColorUtils.parseComponent(message));
    }

    private void broadcastMessage(String message) {
        String prefix = plugin.getConfigManager().getConfig().getString("messages.prefix", "");
        message = message.replace("%rc_prefix%", prefix);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ColorUtils.parseComponent(message));
        }
        Bukkit.getConsoleSender().sendMessage(ColorUtils.parseComponent(message));
    }

    public boolean isChatMuted() {
        return chatMuted;
    }
} 