package fr.skyblock.lang;

public enum LangValue {

    PLAYER("player"),
    COINS("coins"),
    JOB_CHANGED_TIMES("jobChangedTimes"),
    DEATHS("deaths"),
    JOB("job");

    private final String name;

    LangValue(String valueName){
        name = valueName;
    }

    public String getName() {
        return "{" + name + "}";
    }
}
