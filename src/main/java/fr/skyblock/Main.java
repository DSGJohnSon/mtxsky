package fr.skyblock;

import fr.skyblock.listeners.EventsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin Enable");

        //Ecoute des événements
        new EventsManager(this).listenEvents();
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Disable");
    }
}