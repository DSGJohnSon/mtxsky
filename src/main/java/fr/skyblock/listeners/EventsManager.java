package fr.skyblock.listeners;

import fr.skyblock.Main;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class EventsManager implements Listener {

    private Main main;
    private PluginManager pm;

    public EventsManager(Main m){
        main = m;
        pm = m.getServer().getPluginManager();
    }

    public void listenEvents(){

    }

}
