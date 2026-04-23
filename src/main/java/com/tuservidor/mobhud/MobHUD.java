package com.tuservidor.mobhud;

import com.tuservidor.mobhud.listeners.EntityListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MobHUD extends JavaPlugin {
    
    private static MobHUD instance;
    private HudManager hudManager;
    private ConfigManager configManager;
    private MobLevelManager mobLevelManager;
    private HologramManager hologramManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        configManager = new ConfigManager(this);
        hudManager = new HudManager(this);
        mobLevelManager = new MobLevelManager(this);
        hologramManager = new HologramManager(this);
        
        getServer().getPluginManager().registerEvents(new EntityListener(hudManager, mobLevelManager, hologramManager), this);
        
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
        
        getLogger().info("§aMobHUD v2.0 activado - ¡Hologramas y niveles activados!");
    }
    
    @Override
    public void onDisable() {
        hologramManager.eliminarTodosHologramas();
        getLogger().info("§cMobHUD desactivado.");
    }
    
    public static MobHUD getInstance() { return instance; }
    public HudManager getHudManager() { return hudManager; }
    public ConfigManager getConfigManager() { return configManager; }
    public MobLevelManager getMobLevelManager() { return mobLevelManager; }
    public HologramManager getHologramManager() { return hologramManager; }
}