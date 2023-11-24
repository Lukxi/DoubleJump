package me.lukxi.doublejump.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.UUID;

public class JumpListener implements Listener {
    private static HashMap<UUID, Boolean> cooldown = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if (e.getPlayer().hasPermission("doublejump.use")){
            e.getPlayer().setAllowFlight(false);
            cooldown.put(e.getPlayer().getUniqueId(), false);
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if (!e.getPlayer().hasPermission("doublejump.use")) return;
        cooldown.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onFlightActivation(PlayerToggleFlightEvent e){
        if (e.getPlayer().hasPermission("doublejump.use") && e.getPlayer().getGameMode() == GameMode.SURVIVAL){
            e.setCancelled(true);
            if (!cooldown.containsKey(e.getPlayer().getUniqueId())){
                cooldown.put(e.getPlayer().getUniqueId(), false);
            }
            if (!cooldown.get(e.getPlayer().getUniqueId())){
                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).setY(1.5));
                e.getPlayer().setAllowFlight(false);
                cooldown.put(e.getPlayer().getUniqueId(), true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (!e.getPlayer().hasPermission("doublejump.use")) return;
        if (!e.getPlayer().isOnGround()) return;
        cooldown.replace(e.getPlayer().getUniqueId(), false);
        e.getPlayer().setAllowFlight(true);
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e){
        if (!e.getPlayer().hasPermission("doublejump.use")) return;
        if (e.getNewGameMode() == GameMode.SURVIVAL){
            e.getPlayer().setAllowFlight(true);
        }
    }

}
