package com.tuservidor.mobhud;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {
    
    private final MobHUD plugin;
    private FileConfiguration config;
    
    // Valores de configuración
    private boolean mostrarAlDanar;
    private boolean mostrarAlMirar;
    private boolean mostrarExp;
    private String barraLleno;
    private String barraVacio;
    private int barraLargo;
    private String colorAlto;
    private String colorMedio;
    private String colorBajo;
    private String colorCritico;
    private List<EntityType> mobsExcluidos;
    private List<String> mundosDeshabilitados;
    private String mensajeExp;
    private String mensajeRecargado;
    
    public ConfigManager(MobHUD plugin) {
        this.plugin = plugin;
        cargarConfiguracion();
    }
    
    public void cargarConfiguracion() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        
        // Cargar ajustes generales
        mostrarAlDanar = config.getBoolean("general.mostrar-al-danar", true);
        mostrarAlMirar = config.getBoolean("general.mostrar-al-mirar", false);
        mostrarExp = config.getBoolean("general.mostrar-exp", true);
        
        // Cargar apariencia
        barraLleno = config.getString("apariencia.barra.lleno", "█");
        barraVacio = config.getString("apariencia.barra.vacio", "▒");
        barraLargo = config.getInt("apariencia.barra.largo", 20);
        colorAlto = colorizar(config.getString("apariencia.colores.alto", "&a"));
        colorMedio = colorizar(config.getString("apariencia.colores.medio", "&e"));
        colorBajo = colorizar(config.getString("apariencia.colores.bajo", "&6"));
        colorCritico = colorizar(config.getString("apariencia.colores.critico", "&c"));
        
        // Cargar mobs excluidos
        mobsExcluidos = config.getStringList("mobs-excluidos").stream()
                .map(nombre -> {
                    try {
                        return EntityType.valueOf(nombre.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().warning("Tipo de mob inválido en config: " + nombre);
                        return null;
                    }
                })
                .filter(tipo -> tipo != null)
                .collect(Collectors.toList());
        
        // Cargar mundos deshabilitados
        mundosDeshabilitados = config.getStringList("mundos-deshabilitados");
        
        // Cargar mensajes
        mensajeExp = colorizar(config.getString("mensajes.exp-obtenida", "&a+%exp% EXP &7de &f%mob%"));
        mensajeRecargado = colorizar(config.getString("mensajes.recargado", "&aMobHUD recargado correctamente."));
    }
    
    private String colorizar(String texto) {
        return ChatColor.translateAlternateColorCodes('&', texto);
    }
    
    public void recargar() {
        plugin.reloadConfig();
        cargarConfiguracion();
    }
    
    // Getters
    public boolean isMostrarAlDanar() { return mostrarAlDanar; }
    public boolean isMostrarAlMirar() { return mostrarAlMirar; }
    public boolean isMostrarExp() { return mostrarExp; }
    public String getBarraLleno() { return barraLleno; }
    public String getBarraVacio() { return barraVacio; }
    public int getBarraLargo() { return barraLargo; }
    public String getColorAlto() { return colorAlto; }
    public String getColorMedio() { return colorMedio; }
    public String getColorBajo() { return colorBajo; }
    public String getColorCritico() { return colorCritico; }
    public List<EntityType> getMobsExcluidos() { return mobsExcluidos; }
    public List<String> getMundosDeshabilitados() { return mundosDeshabilitados; }
    public String getMensajeExp() { return mensajeExp; }
    public String getMensajeRecargado() { return mensajeRecargado; }
}
