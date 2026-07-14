package com.minikraft;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import java.util.*;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║       MiniKraft Plugin v1.0.0                    ║
 * ║       Minecraft 1.20 Java Edition                ║
 * ║       Bitta Java faylda hamma kod!               ║
 * ║       Created by: Azizbek_777_01                 ║
 * ╚══════════════════════════════════════════════════╝
 * 
 * FUNKSIYALAR:
 * ✅ /teleport <player> - Boshqa o'yunchiga o'tish
 * ✅ /home - O'zing belgilagan uyga qaytish
 * ✅ /sethome - Hozirgi joylashni uy qilish
 * ✅ /spawn - Spawn nuqtasiga o'tish
 * ✅ /setspawn - Spawn nuqtasini belgilash (Admin)
 * ✅ Welcome xabar - O'yunchi kirganida
 */
public class MiniKraftMain extends JavaPlugin implements Listener {
    
    // ============================================
    // GLOBAL VARIABLES (Barcha o'yunchining data)
    // ============================================
    private static final Map<UUID, Location> HOMES = new HashMap<>();
    private static Location SPAWN = null;

    // ============================================
    // PLUGIN ENABLE (Server ishga tushganda)
    // ============================================
    @Override
    public void onEnable() {
        // Console-ga xabar
        getLogger().info("╔════════════════════════════════════╗");
        getLogger().info("║ ✅ MiniKraft Plugin v1.0.0 Enabled ║");
        getLogger().info("║ Created by Azizbek_777_01          ║");
        getLogger().info("╚════════════════════════════════════╝");

        // Barcha buyruqlarni register qilish
        getCommand("teleport").setExecutor(new TeleportCmd());
        getCommand("home").setExecutor(new HomeCmd());
        getCommand("sethome").setExecutor(new SetHomeCmd());
        getCommand("spawn").setExecutor(new SpawnCmd());
        getCommand("setspawn").setExecutor(new SetSpawnCmd());

        // Event listener-ni register qilish
        getServer().getPluginManager().registerEvents(this, this);
    }

    // ============================================
    // PLUGIN DISABLE (Server o'chganda)
    // ============================================
    @Override
    public void onDisable() {
        getLogger().info("❌ MiniKraft Plugin disabled!");
    }

    // ============================================
    // EVENT: PLAYER JOIN (O'yunchi kiritirib)
    // ============================================
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.GOLD + "═══════════════════════════════════════");
        player.sendMessage(ChatColor.GOLD + "  🎮 Welcome to MiniKraft Server!");
        player.sendMessage(ChatColor.GREEN + "  📍 /teleport <player> - O'yunchiga o'tish");
        player.sendMessage(ChatColor.GREEN + "  🏠 /home - Uyga qaytish");
        player.sendMessage(ChatColor.GREEN + "  📍 /sethome - Uy belgilash");
        player.sendMessage(ChatColor.GREEN + "  🌍 /spawn - Spawn-ga o'tish");
        player.sendMessage(ChatColor.GOLD + "═══════════════════════════════════════");
    }

    // ============================================
    // COMMAND 1: TELEPORT <PLAYER>
    // ============================================
    class TeleportCmd implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            // Faqat o'yunchiga
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "❌ Faqat o'yunchiga buyruq!");
                return true;
            }

            Player player = (Player) sender;

            // Argument tekshirish
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "❌ Ishlatish: /teleport <o'yunchi>");
                return true;
            }

            // O'yunchini topish
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "❌ O'yunchi topilmadi: " + args[0]);
                return true;
            }

            // O'ziga o'tishni tekshirish
            if (target == player) {
                player.sendMessage(ChatColor.YELLOW + "⚠️ O'zingizga o'tish mumkin emas!");
                return true;
            }

            // Teleport qilish
            player.teleport(target.getLocation());
            player.sendMessage(ChatColor.GREEN + "✅ " + ChatColor.BOLD + target.getName() + 
                             ChatColor.GREEN + " ga teleportlandi!");
            target.sendMessage(ChatColor.BLUE + "📍 " + ChatColor.BOLD + player.getName() + 
                             ChatColor.BLUE + " sizga teleportlandi!");

            return true;
        }
    }

    // ============================================
    // COMMAND 2: HOME
    // ============================================
    class HomeCmd implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "❌ Faqat o'yunchiga buyruq!");
                return true;
            }

            Player player = (Player) sender;

            // Uy belgilangan bo'lsa tekshirish
            if (!HOMES.containsKey(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "❌ Uy belgilangan emas!");
                player.sendMessage(ChatColor.YELLOW + "⚠️ /sethome buyrug'ini ishlatni!");
                return true;
            }

            // Uyga teleport qilish
            Location home = HOMES.get(player.getUniqueId());
            player.teleport(home);
            player.sendMessage(ChatColor.GREEN + "✅ Uyga qaytdingiz!");
            player.sendMessage(ChatColor.GRAY + "   📍 X: " + home.getBlockX() + 
                             ", Y: " + home.getBlockY() + 
                             ", Z: " + home.getBlockZ());

            return true;
        }
    }

    // ============================================
    // COMMAND 3: SETHOME
    // ============================================
    class SetHomeCmd implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "❌ Faqat o'yunchiga buyruq!");
                return true;
            }

            Player player = (Player) sender;
            Location playerLoc = player.getLocation();

            // Uyni belgilash
            HOMES.put(player.getUniqueId(), playerLoc);
            player.sendMessage(ChatColor.GREEN + "✅ Uy belgilandi!");
            player.sendMessage(ChatColor.GRAY + "   📍 X: " + playerLoc.getBlockX() + 
                             ", Y: " + playerLoc.getBlockY() + 
                             ", Z: " + playerLoc.getBlockZ());

            return true;
        }
    }

    // ============================================
    // COMMAND 4: SPAWN
    // ============================================
    class SpawnCmd implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "❌ Faqat o'yunchiga buyruq!");
                return true;
            }

            Player player = (Player) sender;

            // Spawn belgilangan bo'lsa tekshirish
            if (SPAWN == null) {
                player.sendMessage(ChatColor.RED + "❌ Spawn belgilangan emas!");
                player.sendMessage(ChatColor.YELLOW + "⚠️ Admin /setspawn buyrug'ini ishlatishi kerak!");
                return true;
            }

            // Spawn-ga teleport qilish
            player.teleport(SPAWN);
            player.sendMessage(ChatColor.GREEN + "✅ Spawn-ga o'tdingiz!");

            return true;
        }
    }

    // ============================================
    // COMMAND 5: SETSPAWN (ADMIN ONLY)
    // ============================================
    class SetSpawnCmd implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "❌ Faqat o'yunchiga buyruq!");
                return true;
            }

            Player player = (Player) sender;

            // Permission tekshirish
            if (!player.hasPermission("minikraft.admin")) {
                player.sendMessage(ChatColor.RED + "❌ Sizda ruxsat yo'q!");
                player.sendMessage(ChatColor.YELLOW + "⚠️ Faqat Admin qila oladi!");
                return true;
            }

            // Spawn-ni belgilash
            Location spawnLoc = player.getLocation();
            SPAWN = spawnLoc;
            player.sendMessage(ChatColor.GREEN + "✅ Spawn belgilandi!");
            player.sendMessage(ChatColor.GRAY + "   📍 X: " + spawnLoc.getBlockX() + 
                             ", Y: " + spawnLoc.getBlockY() + 
                             ", Z: " + spawnLoc.getBlockZ());
            
            // Barcha o'yunchiga xabar
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(ChatColor.BLUE + "🔵 Spawn o'zgartirildi!");
            }

            return true;
        }
    }
}
