package in.twizmwaz.cardinal.util;

import in.twizmwaz.cardinal.Cardinal;
import in.twizmwaz.cardinal.module.modules.nick.NickModule;
import in.twizmwaz.cardinal.module.modules.permissions.PermissionModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Skin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {

    public static void resetPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
        for (PotionEffect effect : player.getActivePotionEffects()) {
            try {
                player.removePotionEffect(effect.getType());
            } catch (NullPointerException ignored) {
            }
        }
        player.setTotalExperience(0);
        player.setExp(0);
        player.setPotionParticles(true);
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.2F);
    }

    public static double getSnowflakeMultiplier(OfflinePlayer player) {
        if (player.isOp()) return 2.5;
        if (PermissionModule.isDeveloper(player.getUniqueId())) return 2.0;
        if (PermissionModule.isMod(player.getUniqueId())) return 1.5;
        return 1.0;
    }

    public static void updateNick(Player player) {
        NickModule nickModule = Cardinal.getInstance().getGameHandler().getMatch().getModules().getModule(NickModule.class);
        if (!nickModule.nicks.containsKey(player)) {
            return;
        }
        String nick = nickModule.nicks.get(player);
        player.setDisplayName(nick);
        player.setPlayerListName(nick);
        for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
            player.setFakeNameAndSkin(onlinePlayer, nick, Skin.EMPTY);
        }
    }
}
