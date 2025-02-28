package fr.skyblock.listeners;

import fr.skyblock.jobs.menu.JobInv;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Permet de gérer l'écoute des événements
 */
public class EventsManager implements Listener {

    private final Plugin pl;
    private final PluginManager pm;

    /**
     * Constructeur, renvoie une nouvelle instance de EventsManager
     * @param plugin Plugin
     */
    public EventsManager(Plugin plugin){
        pl = plugin;
        pm = Bukkit.getPluginManager();
    }

    /**
     * Permet d'écouter les évenements
     */
    public void listenEvents(){
        pm.registerEvents(new JoinQuit(), pl);
        pm.registerEvents(new JobInv(), pl);
        pm.registerEvents(new Death(), pl);
        pm.registerEvents(new JobEvents(), pl);
        pm.registerEvents(new QuestEvents(), pl);
    }

}
