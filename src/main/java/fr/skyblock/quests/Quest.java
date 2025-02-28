package fr.skyblock.quests;

public class Quest {

    private final String name, desc;
    private float progress;

    public Quest(String questName, String questDesc, float questProgression){
        name = questName;
        desc = questDesc;
        progress = questProgression;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public float getProgression() {
        return progress;
    }

    public void setProgression(float level) {
        progress = level;
    }
}
