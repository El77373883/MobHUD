package com.tuservidor.mobhud;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HologramManager {
    
    private final MobHUD plugin;
    private final Map<LivingEntity, ArmorStand[]> hologramas = new HashMap<>();
    
    public HologramManager(MobHUD plugin) {
        this.plugin = plugin;
    }
    
    public void mostrarHolograma(LivingEntity entidad, int nivel, double vidaActual, double vidaMaxima) {
        if (hologramas.containsKey(entidad)) {
            eliminarHolograma(entidad);
        }
        
        Location loc = entidad.getLocation().add(0, entidad.getHeight() + 0.5, 0);
        
        ArmorStand linea1 = (ArmorStand) entidad.getWorld().spawnEntity(loc.clone().add(0, 0.3, 0), EntityType.ARMOR_STAND);
        configurarArmorStand(linea1);
        linea1.setCustomName(ChatColor.GOLD + "[Nv." + nivel + "] " + ChatColor.YELLOW + obtenerNombreLimpio(entidad));
        linea1.setCustomNameVisible(true);
        
        ArmorStand linea2 = (ArmorStand) entidad.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        configurarArmorStand(linea2);
        String barra = generarBarraVisual(vidaActual, vidaMaxima);
        linea2.setCustomName(barra + " " + ChatColor.GRAY + (int)vidaActual + "/" + (int)vidaMaxima + "❤");
        linea2.setCustomNameVisible(true);
        
        hologramas.put(entidad, new ArmorStand[]{linea1, linea2});
        
        new BukkitRunnable() {
            @Override
            public void run() {
                eliminarHolograma(entidad);
            }
        }.runTaskLater(plugin, 160L);
    }
    
    public void actualizarHolograma(LivingEntity entidad, int nivel, double vidaActual, double vidaMaxima) {
        if (hologramas.containsKey(entidad)) {
            ArmorStand[] stands = hologramas.get(entidad);
            if (stands.length >= 2 && stands[1] != null && stands[1].isValid()) {
                String barra = generarBarraVisual(vidaActual, vidaMaxima);
                stands[1].setCustomName(barra + " " + ChatColor.GRAY + (int)vidaActual + "/" + (int)vidaMaxima + "❤");
            }
        }
    }
    
    public void eliminarHolograma(LivingEntity entidad) {
        if (hologramas.containsKey(entidad)) {
            ArmorStand[] stands = hologramas.get(entidad);
            for (ArmorStand stand : stands) {
                if (stand != null && stand.isValid()) {
                    stand.remove();
                }
            }
            hologramas.remove(entidad);
        }
    }
    
    public void eliminarTodosHologramas() {
        for (ArmorStand[] stands : hologramas.values()) {
            for (ArmorStand stand : stands) {
                if (stand != null && stand.isValid()) {
                    stand.remove();
                }
            }
        }
        hologramas.clear();
    }
    
    private void configurarArmorStand(ArmorStand stand) {
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setMarker(true);           // ← ELIMINA LA HITBOX
        stand.setCustomNameVisible(true);
        stand.setInvulnerable(true);
        stand.setCollidable(false);
        stand.setCanPickupItems(false);
        stand.setArms(false);
        stand.setBasePlate(false);
        stand.setSmall(true);
    }
    
    private String generarBarraVisual(double actual, double maximo) {
        int porcentaje = (int) ((actual / maximo) * 10);
        StringBuilder barra = new StringBuilder();
        
        for (int i = 0; i < 10; i++) {
            if (i < porcentaje) {
                barra.append(ChatColor.GREEN + "█");
            } else {
                barra.append(ChatColor.RED + "█");
            }
        }
        return barra.toString();
    }
    
    private String obtenerNombreLimpio(LivingEntity entidad) {
        String nombre = entidad.getType().name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);
    }
}