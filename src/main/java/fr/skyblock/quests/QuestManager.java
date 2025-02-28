package fr.skyblock.quests;

import fr.skyblock.Main;
import fr.skyblock.exceptions.NoQuestException;
import fr.skyblock.files.SkyFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {

    private final List<Quest> quests;
    private int currentQuestIndex;
    private final FileConfiguration config;
    private final Main main = Main.getInstance();

    public QuestManager(){
        quests = new ArrayList<>();
        config = SkyFile.QUEST.getConfig();
        currentQuestIndex = config.getInt("currentQuestIndex");
        loadQuests();
    }

    public Quest getCurrentQuest() throws NoQuestException{
        if(currentQuestIndex <= quests.size()) {
            return quests.get(currentQuestIndex);
        }

        throw new NoQuestException();
    }

    public void nextQuest() throws NoQuestException{
        if(currentQuestIndex <= quests.size() - 1){
            currentQuestIndex++;
            config.set("currentQuestIndex", currentQuestIndex);
            saveQuest();
            return;
        }

        throw new NoQuestException();
    }

    public void setQuest(int index) throws NoQuestException{
        if(index <= quests.size() - 1){
            currentQuestIndex = index;
            return;
        }

        throw new NoQuestException();
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void saveQuest(){
        SkyFile.QUEST.save(config);
    }

    public void loadQuests(){
        for(String key : config.getConfigurationSection("quests").getKeys(false)){
            String path = "quests." + key;
            String name = config.getString(path + ".name", "Quête inconnue");
            String desc = config.getString(path + ".desc", "");
            float progress = (float) config.getDouble(path + ".progress", 0);

            quests.add(new Quest(name, desc, progress));
        }

        main.getLogger().info("Chargement des quêtes effectué avec succès.");
    }


}
