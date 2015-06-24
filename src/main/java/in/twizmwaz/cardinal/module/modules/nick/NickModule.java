package in.twizmwaz.cardinal.module.modules.nick;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import in.twizmwaz.cardinal.module.Module;
import in.twizmwaz.cardinal.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class NickModule implements Module {

    public BiMap<Player, String> nicks = HashBiMap.create();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Collection<Player> onlinePlayers = new HashSet<>(Bukkit.getServer().getOnlinePlayers());
        onlinePlayers.add(event.getPlayer());
        for (Player onlinePlayer: onlinePlayers) {
            PlayerUtils.updateNick(onlinePlayer);
        }
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
