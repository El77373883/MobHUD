package com.tuservidor.mobhud;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HudManager {
    
    private final MobHUD plugin;
    
    public HudManager(MobHUD plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Muestra la vida del mob en la barra de acción del jugador
     */
    public void mostrarVidaEnActionBar(Player jugador, LivingEntity entidad) {
        double vidaActual = entidad.getHealth();
        double vidaMaxima = entidad.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        String nombre = obtenerNombreLimpio(entidad);
        
        // Calcular porcentaje y barra visual
        int porcentaje = (int) ((vidaActual / vidaMaxima) * 100);
        String barra = generarBarraProgreso(vidaActual, vidaMaxima, 20);
        
        // Color según porcentaje
        ChatColor color = obtenerColorPorPorcentaje(porcentaje);
        
        String mensaje = String.format("%s%s %s[%.1f/%.1f] %s %d%%",
                color,
                nombre,
                barra,
                vidaActual,
                vidaMaxima,
                color,
                porcentaje);
        
        jugador.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(mensaje));
    }
    
    /**
     * Muestra el mensaje de EXP al matar un mob
     */
    public void mostrarExpObtenida(Player jugador, LivingEntity entidad, int exp) {
        String nombre = obtenerNombreLimpio(entidad);
        jugador.sendMessage(ChatColor.GREEN + "+" + exp + " EXP " + ChatColor.GRAY + "de " + nombre);
    }
    
    /**
     * Genera una barra visual estilo [██████▒▒▒▒]
     */
    private String generarBarraProgreso(double actual, double maximo, int largo) {
        int progreso = (int) ((actual / maximo) * largo);
        StringBuilder barra = new StringBuilder(ChatColor.WHITE + "[");
        
        for (int i = 0; i < largo; i++) {
            if (i < progreso) {
                barra.append(ChatColor.GREEN + "█");
            } else {
                barra.append(ChatColor.RED + "▒");
            }
        }
        barra.append(ChatColor.WHITE + "]");
        return barra.toString();
    }
    
    private ChatColor obtenerColorPorPorcentaje(int porcentaje) {
        if (porcentaje > 75) return ChatColor.GREEN;
        if (porcentaje > 50) return ChatColor.YELLOW;
        if (porcentaje > 25) return ChatColor.GOLD;
        return ChatColor.RED;
    }
    
    private String obtenerNombreLimpio(LivingEntity entidad) {
        String nombre = entidad.getType().name().toLowerCase().replace("_", " ");
        // Capitalizar primera letra
        return Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);
    }
}
