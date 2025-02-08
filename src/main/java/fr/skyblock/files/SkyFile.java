package fr.skyblock.files;

import fr.skyblock.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;

public enum SkyFile {

    LANG("lang.yml"),
    USER("user.yml");

    private final String fileName;
    private final File dataFolder;
    private final Main main = Main.getInstance();

    SkyFile(String file){
        fileName = file;
        dataFolder = main.getDataFolder();
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile(){
        return new File(dataFolder, fileName);
    }

    public FileConfiguration getConfig(){
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration config){
        try {
            config.save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(Logger logger){
        if(fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        InputStream in = main.getResource(fileName);
        if(in == null){
            throw new IllegalArgumentException("The resource " + fileName + " cannot be found in plugin jar");
        }

        if(!dataFolder.exists() && !dataFolder.mkdir()){
            logger.severe("Failed to make directory");
        }

        File outFile = getFile();
        try {
            if(!outFile.exists()){
                logger.info("The " + fileName + " was not found, creation in progress ..." );

                OutputStream out = new FileOutputStream(outFile);

                byte[] buf = new byte[1024];
                int n;
                while((n = in.read(buf)) >= 0){
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                if(!outFile.exists()){
                    logger.severe("Unable to copy file");
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
