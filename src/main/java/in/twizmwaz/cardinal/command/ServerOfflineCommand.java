package in.twizmwaz.cardinal.command;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import in.twizmwaz.cardinal.GameHandler;
import in.twizmwaz.cardinal.module.modules.serveroffline.ServerOfflineModule;
import org.bukkit.command.CommandSender;

public class ServerOfflineCommand {

    @Command(aliases = {"serveroffline", "so"}, desc = "Set the server's offline mode", max = 1)
    public static void serverOffline(final CommandContext cmd, CommandSender sender) throws CommandException {
        ServerOfflineModule module = GameHandler.getGameHandler().getMatch().getModules().getModule(ServerOfflineModule.class);
        if (cmd.argsLength() == 1) {
            if (cmd.getString(0).equalsIgnoreCase("on")) {
                module.setOffline(true);
                sender.sendMessage(ChatColor.BLUE + "Offline mode " + ChatColor.GREEN + "enabled");
            } else if (cmd.getString(0).equalsIgnoreCase("off")) {
                sender.sendMessage(ChatColor.BLUE + "Offline mode " + ChatColor.RED + "disabled");
                module.setOffline(false);
            } else {
                throw new CommandException("Unrecognized option " + cmd.getString(0));
            }
        } else {
            module.setOffline(!module.isOffline());
        }
    }

}
