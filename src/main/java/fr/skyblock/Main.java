package fr.skyblock;

import fr.skyblock.files.SkyFile;
import fr.skyblock.jobs.menu.JobInv;
import fr.skyblock.listeners.EventsManager;
import fr.skyblock.quests.QuestCmd;
import fr.skyblock.quests.QuestManager;
import fr.skyblock.scoreboard.SboreboardRunnable;
import fr.skyblock.scoreboard.ScoreboardManager;
import fr.skyblock.users.UserCmd;
import fr.skyblock.users.UserManager;
import fr.skyblock.users.UsersFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private UserManager userManager;
    private ScoreboardManager sbManager;
    private UsersFile usersFile;
    private QuestManager questManager;

    @Override
    public void onEnable() {
        getLogger().info("---------------------------------------");
        getLogger().info("Initialisation du plugin en cours ...");

        instance = this;

        //Création des fichiers
        SkyFile lang = SkyFile.LANG;
        lang.create(getLogger());

        SkyFile user = SkyFile.USER;
        user.create(getLogger());

        SkyFile quest = SkyFile.QUEST;
        quest.create(getLogger());

        userManager = new UserManager();
        sbManager = new ScoreboardManager();
        usersFile = new UsersFile();
        questManager = new QuestManager();

        //Ecoute des événements
        new EventsManager(this).listenEvents();

        // Enregistrement des commandes
        getCommand("job").setExecutor(new JobInv());
        getCommand("user").setExecutor(new UserCmd());
        getCommand("quest").setExecutor(new QuestCmd());

        //Threads
        new SboreboardRunnable().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {

        Bukkit.getOnlinePlayers().forEach(player -> {
            usersFile.writeData(player);
        });

        getLogger().info("Plugin Disable");
    }

    public static Main getInstance(){
        return instance;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public UsersFile getUsersFile() {
        return usersFile;
    }

    public ScoreboardManager getScoreboardbManager() {
        return sbManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}














