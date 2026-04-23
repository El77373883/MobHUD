package com.tuservidor.mobhud.listeners;

import com.tuservidor.mobhud.HologramManager;
import com.tuservidor.mobhud.HudManager;
import com.tuservidor.mobhud.MobLevelManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener {
    
    private final HudManager hudManager;
    private final MobLevelManager mobLevelManager;
    private final HologramManager hologramManager;
    
    public EntityListener(HudManager hudManager, MobLevelManager mobLevelManager, HologramManager hologramManager) {
        this.hudManager = hudManager;
        this.mobLevelManager = mobLevelManager;
        this.hologramManager = hologramManager;
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player jugador) {
            if (event.getEntity() instanceof LivingEntity entidad) {
                // Asignar nivel si no tiene
                int nivel = mobLevelManager.asignarNivel(entidad, jugador);
                
                // Obtener vida
                double vidaActual = entidad.getHealth() - event.getFinalDamage();
                double vidaMaxima = entidad.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                
                // Mostrar en barra de acción
                hudManager.mostrarVidaEnActionBar(jugador, entidad);
                
                // Mostrar holograma
                hologramManager.mostrarHolograma(entidad, nivel, Math.max(0, vidaActual), vidaMaxima);
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entidad = event.getEntity();
        Player jugador = entidad.getKiller();
        
        if (jugador != null) {
            // Obtener nivel y EXP
            int nivel = mobLevelManager.getNivel(entidad);
            int expExtra = mobLevelManager.getExpPorNivel(entidad);
            
            // Modificar la EXP soltada
            event.setDroppedExp(event.getDroppedExp() + expExtra);
            
            // Mostrar mensaje
            hudManager.mostrarExpObtenida(jugador, entidad, expExtra);
            
            // Mensaje adicional en chat
            jugador.sendMessage("§6[Nv." + nivel + "] §e+" + expExtra + " EXP extra");
        }
        
        // Limpiar registros
        mobLevelManager.eliminarMob(entidad);
        hologramManager.eliminarHolograma(entidad);
    }
}