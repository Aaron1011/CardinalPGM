package in.twizmwaz.cardinal.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import in.twizmwaz.cardinal.GameHandler;
import in.twizmwaz.cardinal.event.RankChangeEvent;
import in.twizmwaz.cardinal.module.modules.permissions.PermissionModule;
import in.twizmwaz.cardinal.rank.Rank;
import in.twizmwaz.cardinal.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RankCommand {
    @Command(aliases = {"rank"}, desc = "Give a player a rank", usage = "<rank> <player>", min = 2, max = 2)
    @CommandPermissions("cardinal.admin.rank")
    public static void rank(CommandContext cmd, CommandSender sender) throws CommandException {
        OfflinePlayer moderator = Bukkit.getOfflinePlayer(cmd.getString(0));
        if (moderator != null) {
            String rank = cmd.getString(1);
            if (!Rank.isRank(moderator.getUniqueId(), rank)) {
                List<String> players = GameHandler.getGameHandler().getPlugin().getConfig().getStringList("permissions." + rank + "players");
                players.add(moderator.getUniqueId().toString());
                GameHandler.getGameHandler().getPlugin().getConfig().set("permissions." + rank + ".players", players);
                GameHandler.getGameHandler().getPlugin().saveConfig();
                sender.sendMessage(ChatColor.GREEN + "You gave " + rank + " to " + TeamUtils.getTeamColorByPlayer(moderator) + (moderator.isOnline() ? ((Player) moderator).getDisplayName() : moderator.getName()));
                if (moderator.isOnline()) {
                    ((Player) moderator).sendMessage(ChatColor.GREEN + "You are now a " + rank + "!");
                    for (String permission : GameHandler.getGameHandler().getPlugin().getConfig().getStringList("permissions." + rank + "players")) {
                        GameHandler.getGameHandler().getMatch().getModules().getModules(PermissionModule.class).get(0).enablePermission((Player) moderator, permission);
                    }
                    Bukkit.getServer().getPluginManager().callEvent(new RankChangeEvent((Player) moderator));
                }
            } else {
                throw new CommandException("The player specified is already a " + rank + "!");
            }
        } else {
            throw new CommandException("The player specified is already op!");
        }
    }

    @Command(aliases = {"derank", "unrank"}, desc = "Remove a rank from a player", usage = "<rank> <player>", min = 2, max = 2)
    @CommandPermissions("cardinal.admin.derank")
    public static void derank(CommandContext cmd, CommandSender sender) throws CommandException {
        OfflinePlayer moderator = Bukkit.getOfflinePlayer(cmd.getString(0));
        if (moderator != null) {
            String rank = cmd.getString(1);
            if (Rank.isRank(moderator.getUniqueId(), rank)) {
                List<String> players = GameHandler.getGameHandler().getPlugin().getConfig().getStringList("permissions." + rank + ".players");
                players.remove(moderator.getUniqueId().toString());
                GameHandler.getGameHandler().getPlugin().getConfig().set("permissions." + rank + ".players", players);
                sender.sendMessage(ChatColor.RED + "You removed " + rank + "from " + TeamUtils.getTeamColorByPlayer(moderator) + (moderator.isOnline() ? ((Player) moderator).getDisplayName() : moderator.getName()));
                if (moderator.isOnline()) {
                    ((Player) moderator).sendMessage(ChatColor.RED + "You are no longer a " + rank + "!");
                    for (String permission : GameHandler.getGameHandler().getPlugin().getConfig().getStringList("permissions." + rank + "players")) {
                        GameHandler.getGameHandler().getMatch().getModules().getModules(PermissionModule.class).get(0).disablePermission((Player) moderator, permission);
                    }
                    Bukkit.getServer().getPluginManager().callEvent(new RankChangeEvent((Player) moderator));
                }
            } else {
                throw new CommandException("The player specified is not a " + rank + "!");
            }
        } else {
            throw new CommandException("The player specified is already op!");
        }
    }
}
