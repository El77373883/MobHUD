package com.tuservidor.mobhud;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MobLevelManager {
    
    private final MobHUD plugin;
    private final Map<LivingEntity, Integer> nivelesMob = new HashMap<>();
    private final Random random = new Random();
    
    public MobLevelManager(MobHUD plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Asigna un nivel aleatorio a un mob según la distancia del jugador
     */
    public int asignarNivel(LivingEntity entidad, Player jugador) {
        // Si ya tiene nivel asignado, devolverlo
        if (nivelesMob.containsKey(entidad)) {
            return nivelesMob.get(entidad);
        }
        
        // Calcular nivel basado en distancia al spawn (0,0) o posición del jugador
        double distancia = entidad.getLocation().distance(entidad.getWorld().getSpawnLocation());
        int nivelBase = (int) (distancia / 100) + 1;
        
        // Añadir variación aleatoria (-2 a +3)
        int variacion = random.nextInt(6) - 2;
        int nivel = Math.max(1, nivelBase + variacion);
        
        nivelesMob.put(entidad, nivel);
        return nivel;
    }
    
    /**
     * Obtiene el nivel de un mob
     */
    public int getNivel(LivingEntity entidad) {
        return nivelesMob.getOrDefault(entidad, 1);
    }
    
    /**
     * Elimina el mob del registro cuando muere
     */
    public void eliminarMob(LivingEntity entidad) {
        nivelesMob.remove(entidad);
    }
    
    /**
     * Calcula la EXP que suelta según el nivel
     */
    public int getExpPorNivel(LivingEntity entidad) {
        int nivel = getNivel(entidad);
        return 5 + (nivel * 2); // Nv.1 = 7exp, Nv.10 = 25exp
    }
}