package in.twizmwaz.cardinal.rank;

import in.twizmwaz.cardinal.GameHandler;
import in.twizmwaz.cardinal.module.modules.permissions.PermissionModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Rank {

    private static List<Rank> ranks = new ArrayList<>();
    private static HashMap<UUID, List<Rank>> playerRanks = new HashMap<>();
    private String name;
    private String flair;
    private boolean staffRank;
    private boolean defaultRank;

    public Rank(String name, String flair, boolean staffRank, boolean defaultRank) {
        this.name = name;
        this.flair = flair;
        this.staffRank = staffRank;
        this.defaultRank = defaultRank;

        ranks.add(this);
    }

    public static List<Rank> getRanks() {
        return ranks;
    }

    public static List<Rank> getDefaultRanks() {
        List<Rank> results = new ArrayList<>();
        for (Rank rank : ranks) {
            if (rank.isDefaultRank()) {
                results.add(rank);
            }
        }
        return results;
    }

    public String getName() {
        return name;
    }

    public String getFlair() {
        return flair;
    }

    public boolean isStaffRank() {
        return staffRank;
    }

    public boolean isDefaultRank() {
        return defaultRank;
    }

    public void addPlayer(UUID player) {
        if (!playerRanks.containsKey(player)) {
            playerRanks.put(player, new ArrayList<Rank>());
        }
        playerRanks.get(player).add(this);
    }

    public void removePlayer(UUID player) {
        if (!playerRanks.containsKey(player)) {
            playerRanks.put(player, new ArrayList<Rank>());
        }
        playerRanks.get(player).remove(this);
    }


    public static List<Rank> getRanksByPlayer(UUID player) {
        return playerRanks.containsKey(player) ? playerRanks.get(player) : new ArrayList<Rank>();
    }

    public static String getPlayerPrefix(UUID player) {
        String prefix = "";
        String staffChar = "\u2756";
        String adminChar = "۞";
        String asterisk = "*";
        if (Bukkit.getOfflinePlayer(player).isOp() || isRank(player, "owner")) {
            prefix += ChatColor.DARK_PURPLE + adminChar;
        } else if (PermissionModule.isMod(player)) {
            prefix += ChatColor.RED + adminChar;
        }
        if (PermissionModule.isDeveloper(player)) {
            prefix += ChatColor.DARK_PURPLE + staffChar;
        }
        if (isRank(player, "admin")) {
            prefix += ChatColor.GOLD + adminChar;
        }
        if (isRank(player, "ref")) {
            prefix += ChatColor.GREEN + staffChar;
        }
        if (isRank(player, "jr"))  {
            prefix += ChatColor.LIGHT_PURPLE + adminChar;
        }
        if (isRank(player, "tournament_winner")) {
            prefix += ChatColor.WHITE + asterisk;
        }
        if (isRank(player, "optio")) {
            prefix += ChatColor.GREEN + asterisk;
        }
        if (isRank(player, "centurion")) {
            prefix += ChatColor.YELLOW + asterisk;
        }
        if (isRank(player, "dux")) {
            prefix += ChatColor.DARK_PURPLE + asterisk;
        }
        if (isRank(player, "internet")) {
            prefix += ChatColor.DARK_BLUE + asterisk;
        }
        return prefix;
    }

    public static boolean isRank(UUID player, String rank) {
        if (rank.equals("admin") && Bukkit.getOfflinePlayer(player).isOp()) {
            return true;
        }
        for (String uuid : GameHandler.getGameHandler().getPlugin().getConfig().getStringList("permissions." + rank + ".players")) {
            if (uuid.equals(player.toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean canUseNick(OfflinePlayer player) {
        return PermissionModule.isStaff(player) || isRank(player.getUniqueId(), "internet");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Rank rank : getDefaultRanks()) {
            rank.addPlayer(event.getPlayer().getUniqueId());
        }
//        Bukkit.getPluginManager().callEvent(new RankChangeEvent());
    }

}
