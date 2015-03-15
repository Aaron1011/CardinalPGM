package in.twizmwaz.cardinal.module.modules.serveroffline;

import in.twizmwaz.cardinal.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Iterator;

public class ServerOfflineModule implements Module {

    private boolean offline;

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        System.out.println("Logged in");
        if (this.offline && !event.getPlayer().isOp()) {
            System.out.println("Kicking...");
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "The server is in offline mode");
        }
    }

    // Screw this, I'm special
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPing(ServerListPingEvent event) {
        // lol, so hacky
        if (this.offline) {
            event.setMotd(ChatColor.DARK_RED + "Can't connect to server");
            event.setMaxPlayers(0);
            Iterator iterator = event.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
    }

    public boolean isOffline() {
        return this.offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
