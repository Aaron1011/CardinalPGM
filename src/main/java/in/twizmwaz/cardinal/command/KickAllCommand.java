package in.twizmwaz.cardinal.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import in.twizmwaz.cardinal.chat.ChatConstant;
import in.twizmwaz.cardinal.chat.LocalizedChatMessage;
import in.twizmwaz.cardinal.module.modules.team.TeamModule;
import in.twizmwaz.cardinal.util.ChatUtils;
import in.twizmwaz.cardinal.util.TeamUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;

public class KickAllCommand {

    @Command(aliases = {"kickall", "ka"}, desc = "Kick all the players in a group", min = 1)
    public static void kickAll(final CommandContext cmd, CommandSender sender) throws CommandException {
        if (cmd.argsLength() == 1) {
            TeamModule team = TeamUtils.getTeamByName(cmd.getString(0));
            if (team != null) {
                Iterator iterator = team.iterator();
                while (iterator.hasNext()) {
                    Player player = (Player) iterator.next();
                    iterator.remove();
                    player.kickPlayer("You got kicked!");
                }
            } else {
                throw new CommandException(new LocalizedChatMessage(ChatConstant.ERROR_NO_TEAM_MATCH).getMessage(
                        ChatUtils.getLocale(sender)));
            }
        }
    }

}
