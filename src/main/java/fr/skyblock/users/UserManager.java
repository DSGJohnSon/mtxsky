package fr.skyblock.users;

import fr.skyblock.Main;
import fr.skyblock.jobs.types.Chomeur;
import fr.skyblock.jobs.EJob;
import fr.skyblock.jobs.types.Mineur;
import fr.skyblock.lang.Lang;
import fr.skyblock.lang.LangValue;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Permet de gérer les utilisateurs
 */
public class UserManager {

    private final Main main = Main.getInstance();

    public final Set<User> users = new HashSet<>();

    /**
     * Creer un nouvel utilisateur à la connection
     * @param player joueur
     */
    public void onLogin(Player player){
        users.add(new User(player));
    }

    /**
     * Retourne un user à partir d'un uuid
     * @param uuid uuid
     * @return User user
     */
    public Optional<User> getUser(UUID uuid){
        return users.stream().filter(user -> user.getUUID().equals(uuid.toString())).findFirst();
    }

    /**
     * Retourne un utilisateur à partir du joueur
     * @param player player
     * @return User user
     */
    public Optional<User> getUser(Player player){
        return getUser(player.getUniqueId());
    }

    /**
     * Retourne la liste des utilisateurs
     * @return Set<User> set des utilisateurs
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Supprime un utilisateur à partie de l'uuid
     * @param uuid uuid
     */
    public void delete(UUID uuid){
        getUser(uuid).ifPresent(users::remove);
    }

    /**
     * supprime un utilisateur à partir du joueur
     * @param player player
     */
    public void delete(Player player){
        delete(player.getUniqueId());
    }

    /**
     * Change la valeur d'un attribut associé à un uuid dans le fichier
     * @param sender Player executeur de la commande
     * @param cmd String commande
     * @param target String nom de la cible
     * @param data String attribut
     * @param value String valeur
     */
    public void changeData(Player sender, String cmd, String target, String data, String value){
        if(!cmd.equals("set") &&
                !cmd.equals("add") &&
                !cmd.equals("remove") &&
                !cmd.equals("stats")){
            showHelp(sender);
            return;
        }

        if(Bukkit.getPlayer(target) == null){
            sender.sendMessage(Lang.getErrorPrefix() + Lang.PLAYER_NOT_FOUND.get()
                    .replace(LangValue.PLAYER.getName(), target));
            return;
        }
        if(getUser(Bukkit.getPlayer(target)).isEmpty()){
            sender.sendMessage(Lang.getErrorPrefix() + Lang.ACCOUNT_NOT_FOUND.get()
                    .replace(LangValue.PLAYER.getName(), target));
            return;
        }

        User user = getUser(Bukkit.getPlayer(target)).get();

        if(!data.equals("deaths") &&
                !data.equals("job") &&
                !data.equals("jobChangedTimes") &&
                !data.equals("coins")){
            showHelp(sender);
            return;
        }

        switch(data) {
            case "deaths":
                try {
                    if(cmd.equalsIgnoreCase("set")){
                        user.setDeaths(Integer.parseInt(value));
                    } else if (cmd.equalsIgnoreCase("add")){
                        user.addDeaths(Integer.parseInt(value));
                    } else if (cmd.equalsIgnoreCase("remove")){
                        user.removeDeaths(Integer.parseInt(value));
                    }
                    sender.sendMessage(Component.text(Lang.getPrefix() + Lang.CMD_USER_DEATHS.get()
                            .replace(LangValue.PLAYER.getName(), target)
                            .replace(LangValue.DEATHS.getName(), value)));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "coins":
                try {
                    if(cmd.equalsIgnoreCase("set")){
                        user.setMoney(Integer.parseInt(value));
                    } else if (cmd.equalsIgnoreCase("add")){
                        user.addMoney(Integer.parseInt(value));
                    } else if (cmd.equalsIgnoreCase("remove")){
                        user.removeMoney(Integer.parseInt(value));
                    }
                    sender.sendMessage(Component.text(Lang.getPrefix() + Lang.CMD_USER_DEATHS.get()
                            .replace(LangValue.PLAYER.getName(), target)
                            .replace(LangValue.COINS.getName(), value)));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "jobChangedTimes":
                try {
                    if(cmd.equalsIgnoreCase("set")){
                        user.setJobChangeTimes(Integer.parseInt(value));
                    } else if (cmd.equalsIgnoreCase("add")){
                        user.addJobChangeTimes(Integer.parseInt(value));
                    } else if (cmd.equalsIgnoreCase("remove")){
                        user.removeJobChangeTimes(Integer.parseInt(value));
                    }
                    sender.sendMessage(Component.text(Lang.getPrefix() + Lang.CMD_USER_DEATHS.get()
                            .replace(LangValue.PLAYER.getName(), target)
                            .replace(LangValue.JOB_CHANGED_TIMES.getName(), value)));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "job":
                if(!cmd.equalsIgnoreCase("set")){
                    showHelp(sender);
                    return;
                }

                if(data.equals(EJob.CHOMEUR.getName().toLowerCase())){
                    user.changeJob(new Chomeur(user));
                } else if(data.equals(EJob.MINEUR.getName().toLowerCase())){
                    user.changeJob(new Mineur(user));
                } else {
                    sender.sendMessage(Lang.getErrorPrefix() + Lang.JOB_NOT_FOUND.get()
                            .replace(LangValue.JOB.getName(), value));
                    return;
                }
                sender.sendMessage(Lang.getPrefix() + Lang.CMD_USER_JOB.get()
                        .replace(LangValue.PLAYER.getName(), target)
                        .replace(LangValue.JOB.getName(), value));
                break;
            default:
                showHelp(sender);
                break;
        }
    }

    /**
     * Affiche le menu d'aide de la commande /user
     * @param p Player joueur
     */
    public void showHelp(Player p){
        p.sendMessage(Lang.CMD_USER_HELP.get());
    }

}
