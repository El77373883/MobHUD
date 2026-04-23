package com.tuservidor.mobhud.listeners;

import com.tuservidor.mobhud.HudManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EntityListener implements Listener {
    
    private final HudManager hudManager;
    
    public EntityListener(HudManager hudManager) {
        this.hudManager = hudManager;
    }
    
    /**
     * Muestra la vida cuando un jugador daña a un mob
     */
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player jugador) {
            if (event.getEntity() instanceof LivingEntity entidad) {
                hudManager.mostrarVidaEnActionBar(jugador, entidad);
            }
        }
    }
    
    /**
     * Muestra la EXP obtenida al matar un mob
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entidad = event.getEntity();
        Player jugador = entidad.getKiller();
        
        if (jugador != null) {
            int exp = event.getDroppedExp();
            hudManager.mostrarExpObtenida(jugador, entidad, exp);
        }
    }
}
