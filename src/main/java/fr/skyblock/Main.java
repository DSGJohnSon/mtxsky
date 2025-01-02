package fr.skyblock;

import fr.skyblock.jobs.menu.JobInv;
import fr.skyblock.listeners.EventsManager;
import fr.skyblock.scoreboard.SboreboardRunnable;
import fr.skyblock.scoreboard.ScoreboardManager;
import fr.skyblock.users.UserCmd;
import fr.skyblock.users.UserManager;
import fr.skyblock.users.UsersFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private UserManager userManager;
    private ScoreboardManager sbManager;
    private UsersFile usersFile;
    private String errorPrefix;
    private String prefix;

    @Override
    public void onEnable() {
        getLogger().info("------------------------------------------");
        getLogger().info("Initialisation du plugin en cours ...");

        instance = this;
        userManager = new UserManager();
        sbManager = new ScoreboardManager();
        usersFile = new UsersFile();
        usersFile.createFile();

        errorPrefix = "&7[&cErreur&7] ";
        prefix = "&7[&bSkyBlock&7] ";

        //Ecoute des événements
        new EventsManager(this).listenEvents();

        // Enregistrement des commandes
        getCommand("job").setExecutor(new JobInv());
        getCommand("user").setExecutor(new UserCmd());

        //Threads

        new SboreboardRunnable().runTaskTimer(this, 0, 20);
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

    public ScoreboardManager getScoreboardbManager() {
        return sbManager;
    }
}