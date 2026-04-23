package com.tuservidor.mobhud;

import com.tuservidor.mobhud.listeners.EntityListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MobHUD extends JavaPlugin {
    
    private static MobHUD instance;
    private HudManager hudManager;
    private ConfigManager configManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Cargar configuración primero
        configManager = new ConfigManager(this);
        hudManager = new HudManager(this);
        
        // Registrar eventos
        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
        
        // Registrar comando de recarga
        getCommand("mobhud").setExecutor((sender, command, label, args) -> {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("mobhud.reload") || sender.isOp()) {
                    configManager.recargar();
                    sender.sendMessage(configManager.getMensajeRecargado());
                } else {
                    sender.sendMessage("§cNo tienes permiso.");
                }
                return true;
            }
            sender.sendMessage("§eMobHUD v1.0 - /mobhud reload");
            return true;
        });
        
        getLogger().info("§aMobHUD v1.0 activado correctamente.");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("§cMobHUD desactivado.");
    }
    
    public static MobHUD getInstance() { return instance; }
    public HudManager getHudManager() { return hudManager; }
    public ConfigManager getConfigManager() { return configManager; }
}