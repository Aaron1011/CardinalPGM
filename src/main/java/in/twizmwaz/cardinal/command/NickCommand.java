package in.twizmwaz.cardinal.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import in.twizmwaz.cardinal.Cardinal;
import in.twizmwaz.cardinal.event.RankChangeEvent;
import in.twizmwaz.cardinal.module.modules.nick.NickModule;
import in.twizmwaz.cardinal.rank.Rank;
import in.twizmwaz.cardinal.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand {

    @Command(aliases = {"nick"}, desc = "Sets your nickname", min = 1, max = 1, usage = "<nickname>")
    public static void nick(final CommandContext cmd, CommandSender sender) throws CommandException {
        if (!Rank.canUseNick((Player) sender)) {
            throw new CommandException("Only staff or internet famous can use /denick!");
        }

        Cardinal.getInstance().getGameHandler().getMatch().getModules().getModule(NickModule.class).nicks.put((Player) sender, cmd.getString(0));
        PlayerUtils.updateNick((Player) sender);
        sender.sendMessage("Your nick is now " + cmd.getString(0));
    }

    @Command(aliases = "denick", desc = "Remove your nickname", min = 0, max = 0, usage = "")
    public static void denick(final CommandContext cmd, CommandSender sender) throws CommandException {
        if (!Rank.canUseNick((Player) sender)) {
            throw new CommandException("Only staff or internet famous can use /denick!");
        }

        Cardinal.getInstance().getGameHandler().getMatch().getModules().getModule(NickModule.class).nicks.remove(sender);
        Player player = (Player) sender;
        player.clearFakeDisplayNames();
        player.clearFakeNamesAndSkins();
        Bukkit.getServer().getPluginManager().callEvent(new RankChangeEvent((Player) sender));
    }

}
