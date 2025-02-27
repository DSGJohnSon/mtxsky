package fr.skyblock.lang;

import fr.skyblock.Main;
import fr.skyblock.files.SkyFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public enum Lang {
    PREFIX,
    ERROR,
    PLAYER_JOIN,
    PLAYER_QUIT,
    CMD_USER_HELP,
    CMD_USER_STATS,
    CMD_USER_DEATHS,
    CMD_USER_COINS,
    CMD_USER_COINS_NOT_ENOUGH_MONEY,
    CMD_USER_COINS_INVALID_PARAM,
    CMD_USER_JOB_CHANGED_TIMES,
    CMD_USER_JOB,
    JOB_NOT_FOUND,
    ACCOUNT_NOT_FOUND,
    PLAYER_NOT_FOUND,
    NOT_ENOUGH_MONEY,
    JOB_ALREADY_TAKEN,
    NEW_JOB;

    private static final Map<Lang, String> values = new HashMap<>();

    static {
        for(Lang lang : values()){
            values.put(lang, lang.getFromFile());
        }

        Main.getInstance().getLogger().info("Fichier lang lu avec succès !");
    }

    private String getFromFile(){
        FileConfiguration config = SkyFile.LANG.getConfig();
        String key = name().toLowerCase().replace("_", "-");
        String value = config.getString(key);

        if(value == null){
            value = "";
        }

        return value.replace("&", "§");
    }

    public static String getPrefix(){
        return PREFIX.get() + "§f ";
    }

    public static String getErrorPrefix(){
        return ERROR.get() + "§f ";
    }

    public String get(){
        return values.get(this);
    }
}
