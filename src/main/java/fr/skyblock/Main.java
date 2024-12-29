package fr.skyblock;

import fr.skyblock.jobs.menu.JobInv;
import fr.skyblock.listeners.EventsManager;
import fr.skyblock.users.UserManager;
import fr.skyblock.users.UsersFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main instance;
    private UserManager userManager;
    private UsersFile usersFile;
    private String errorPrefix;
    private String prefix;

    @Override
    public void onEnable() {
        getLogger().info("------------------------------------------");
        getLogger().info("Initialisation du plugin en cours ...");

        instance = this;
        userManager = new UserManager();
        usersFile = new UsersFile();
        usersFile.createFile();

        errorPrefix = "&7[&cErreur&7] ";
        prefix = "&7[&bSkyBlock&7] ";

        //Ecoute des événements
        new EventsManager(this).listenEvents();

        // Enregistrement des commandes
        Objects.requireNonNull(getCommand("job")).setExecutor(new JobInv());
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Disable");
    }

    public static Main getInstance(){
        return instance;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public String getErrorPrefix() {
        return errorPrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public UsersFile getUsersFile() {
        return usersFile;
    }
}