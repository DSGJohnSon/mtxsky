package fr.skyblock.users;

import fr.skyblock.Main;
import fr.skyblock.exceptions.NotEnoughMoneyException;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.jobs.JobInfo;
import fr.skyblock.jobs.types.Chomeur;
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

    public void showStats(Player sender, String targetName){
        User user;
        if(Bukkit.getPlayer(targetName) == null){
            sender.sendMessage(Lang.getErrorPrefix() + Lang.PLAYER_NOT_FOUND.get()
                    .replace(LangValue.PLAYER.getName(), targetName));
            return;
        }
        if(getUser(Bukkit.getPlayer(targetName)).isEmpty()) {
            sender.sendMessage(Lang.getErrorPrefix() + Lang.ACCOUNT_NOT_FOUND.get()
                    .replace(LangValue.PLAYER.getName(), targetName));
            return;
        }
        user = getUser(Bukkit.getPlayer(targetName)).get();
        sender.sendMessage(Lang.CMD_USER_STATS.get()
                .replace(LangValue.PLAYER.getName(), targetName)
                .replace(LangValue.JOB.getName(), user.getJob().getName())
                .replace(LangValue.DEATHS.getName(), String.valueOf(user.getDeaths()))
                .replace(LangValue.JOB_CHANGED_TIMES.getName(), String.valueOf(user.getJobChangeTimes()))
                .replace(LangValue.COINS.getName(), String.valueOf(user.getMoney())));
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
                            .replace(LangValue.DEATHS.getName(), Integer.toString(user.getDeaths()))));
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

                        try{
                            user.removeMoney(Integer.parseInt(value));
                        } catch (NotEnoughMoneyException e){
                            sender.sendMessage(Lang.getErrorPrefix() + Lang.CMD_USER_COINS_NOT_ENOUGH_MONEY.get()
                                    .replace(LangValue.PLAYER.getName(), target));
                        } catch (ValParamException e) {
                            sender.sendMessage(Lang.getErrorPrefix() + Lang.CMD_USER_COINS_INVALID_PARAM.get());
                        }

                    }
                    sender.sendMessage(Component.text(Lang.getPrefix() + Lang.CMD_USER_COINS.get()
                            .replace(LangValue.PLAYER.getName(), target)
                            .replace(LangValue.COINS.getName(), Double.toString(user.getMoney())));
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
                    sender.sendMessage(Component.text(Lang.getPrefix() + Lang.CMD_USER_JOB_CHANGED_TIMES.get()
                            .replace(LangValue.PLAYER.getName(), target)
                            .replace(LangValue.JOB_CHANGED_TIMES.getName(), Integer.toString(user.getJobChangeTimes()))));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "job":
                if(!cmd.equalsIgnoreCase("set")){
                    showHelp(sender);
                    return;
                }

                if(data.equalsIgnoreCase(JobInfo.CHOMEUR.getName())){
                    user.changeJob(new Chomeur());
                } else if(data.equalsIgnoreCase(JobInfo.MINEUR.getName())){
                    user.changeJob(new Mineur());
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
