package com.tuservidor.mobhud;

import com.tuservidor.mobhud.listeners.EntityListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MobHUD extends JavaPlugin {
    
    private static MobHUD instance;
    private HudManager hudManager;
    
    @Override
    public void onEnable() {
        instance = this;
        hudManager = new HudManager(this);
        
        getServer().getPluginManager().registerEvents(new EntityListener(hudManager), this);
        
        getLogger().info("§aMobHUD v1.0 activado - ¡Mostrando vida y EXP de mobs!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("§cMobHUD desactivado.");
    }
    
    public static MobHUD getInstance() {
        return instance;
    }
    
    public HudManager getHudManager() {
        return hudManager;
    }
}
