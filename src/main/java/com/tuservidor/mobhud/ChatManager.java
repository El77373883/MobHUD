package com.tuservidor.mobhud;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatManager implements Listener {
    
    private final MobHUD plugin;
    
    public ChatManager(MobHUD plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Muestra un mensaje épico cuando un jugador entra al servidor
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player jugador = event.getPlayer();
        
        // Solo mostrar una vez por sesión (usando metadata o simplemente cada vez)
        mostrarMensajeEpico(jugador);
    }
    
    /**
     * Envía el mensaje épico al chat del jugador
     */
    public void mostrarMensajeEpico(Player jugador) {
        jugador.sendMessage("");
        jugador.sendMessage(ChatColor.GOLD + "═══════════════════════════════════════");
        jugador.sendMessage("");
        jugador.sendMessage(ChatColor.LIGHT_PURPLE + "    ✦ " + ChatColor.YELLOW + "MobHUD" + ChatColor.LIGHT_PURPLE + " ✦");
        jugador.sendMessage(ChatColor.GRAY + "    Muestra vida, EXP y nivel de mobs");
        jugador.sendMessage("");
        jugador.sendMessage(ChatColor.AQUA + "    Creado por: " + ChatColor.WHITE + "soyadrianyt001");
        jugador.sendMessage(ChatColor.AQUA + "    Versión: " + ChatColor.WHITE + "v1.0");
        jugador.sendMessage("");
        jugador.sendMessage(ChatColor.GOLD + "═══════════════════════════════════════");
        jugador.sendMessage("");
    }
    
    /**
     * Muestra el mensaje épico en consola al iniciar
     */
    public void mostrarMensajeConsola() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "═══════════════════════════════════════");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "    ✦ " + ChatColor.YELLOW + "MobHUD" + ChatColor.LIGHT_PURPLE + " ✦");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "    Muestra vida, EXP y nivel de mobs");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "    Creado por: " + ChatColor.WHITE + "soyadrianyt001");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "    Versión: " + ChatColor.WHITE + "v1.0");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "═══════════════════════════════════════");
        Bukkit.getConsoleSender().sendMessage("");
    }
}