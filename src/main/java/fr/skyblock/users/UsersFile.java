package fr.skyblock.users;

import fr.skyblock.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UsersFile {

    private static final Logger log = LoggerFactory.getLogger(UsersFile.class);
    private File file;
    private FileConfiguration config;

    private final String fileName, coinAccess, deathAccess, jobAccesss, jobChangedTimesAccesss;

    private final Main main = Main.getInstance();

    public UsersFile(){
        fileName = "users.yml";
        coinAccess = ".coins";
        deathAccess = ".deaths";
        jobAccesss = ".job";
        jobChangedTimesAccesss = ".jobChangedTimes";
    }

    public void createFile(){

        if(!main.getDataFolder().exists()){
            main.getDataFolder().mkdir();
        }

        file = new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            main.saveResource(fileName, false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAccount(Player player){
        if(player == null){
            main.getLogger().warning(getClass().getSimpleName() + "Joueur inexistant");
            throw new RuntimeException(getClass().getSimpleName() + "Joueur inexistant");
        }
        String uuid = player.getUniqueId().toString();

        if(config.get(uuid) == null){
            main.getLogger().info("Je crée un compte pour " + player.getName());
            config.set(uuid + coinAccess, 0);
            config.set(uuid + deathAccess, 0);
            config.set(uuid + jobAccesss, "chomeur");
            config.set(uuid + jobChangedTimesAccesss, 0);
            save();
        }
    }

    public void readData(Player player){
        User user;

        // Joueur introuvable
        if(player == null){
            main.getLogger().warning(getClass().getSimpleName() + "Joueur inexistant");
            throw new RuntimeException(getClass().getSimpleName() + "Joueur inexistant");
        }

        String uuid = player.getUniqueId().toString();
        createAccount(player);

        // Pas de compte dans le plugin
        if(main.getUserManager().getUser(player).isEmpty()){
            main.getLogger().severe(getClass().getSimpleName() + "Impossible de trouver le compte " + uuid);
            throw new RuntimeException(getClass().getSimpleName() + "Impossible de trouver le compte " + uuid);
        }

        user = main.getUserManager().getUser(player).get();

        user.setDeaths(config.getInt(uuid + deathAccess));
        user.setMoney(config.getDouble(uuid + coinAccess));
        user.setJobChangeTimes(config.getInt(uuid + jobChangedTimesAccesss));
        user.setJob(Objects.requireNonNull(config.getString(uuid + jobAccesss)));
    }

    public void writeData(Player player) throws RuntimeException{
        String uuid;
        User user;

        // Joueur introuvable
        if(player == null){
            main.getLogger().severe(getClass().getSimpleName() + "Joueur inexistant");
            throw new RuntimeException(getClass().getSimpleName() + "Joueur inexistant");
        }
        uuid = player.getUniqueId().toString();

        // Pas de compte plugin ou fichier
        if(main.getUserManager().getUser(player).isEmpty() || config.get(uuid) == null){
            main.getLogger().severe(getClass().getSimpleName() + "Impossible de trouver le compte " + uuid);
            throw new RuntimeException(getClass().getSimpleName() + "Impossible de trouver le compte " + uuid);
        }
        user = main.getUserManager().getUser(player).get();

        config.set(uuid + coinAccess, user.getMoney());
        config.set(uuid + deathAccess, user.getDeaths());
        config.set(uuid + jobChangedTimesAccesss, user.getJobChangeTimes());
        config.set(uuid + jobAccesss, user.getJob().getEJob().getName().toLowerCase());
        save();
    }
}
