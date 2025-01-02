package fr.skyblock.users;

import fr.skyblock.Main;
import fr.skyblock.jobs.types.Chomeur;
import fr.skyblock.jobs.EJob;
import fr.skyblock.jobs.types.Mineur;
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
                !cmd.equals("remove")) {
            showHelp(sender);
            return;
        }

        if(Bukkit.getPlayer(target) == null){
            sender.sendMessage(Main.getInstance().getErrorPrefix() + "Le joueur §c" + target + " §7n'existe pas.");
            return;
        }
        if(getUser(Bukkit.getPlayer(target)).isEmpty()){
            sender.sendMessage(Main.getInstance().getErrorPrefix() + "Le joueur §c" + target + " §7n'a pas de compte utilisateur.");
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
                    user.setDeaths(Integer.parseInt(value));
                    sender.sendMessage(Main.getInstance().getPrefix() + "Le nombre de morts de §b"
                            + target
                            + " §fest maintenant de §b"
                            + value);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "coins":
                try {
                    user.setMoney(Double.parseDouble(value));
                    sender.sendMessage(Main.getInstance().getPrefix() + "L'argent de §b"
                            + target
                            + " §fest maintenant de §b"
                            + value);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "jobChangedTimes":
                try {
                    user.setJobChangeTimes(Integer.parseInt(value));
                    sender.sendMessage(Main.getInstance().getPrefix() + "Le nombre de fois que §b"
                            + target + " §f a changé de métier est maintenant de §b" + value);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "job":
                if(data.equals(EJob.CHOMEUR.getName().toLowerCase())){
                    user.changeJob(new Chomeur(user));
                } else if(data.equals(EJob.MINEUR.getName().toLowerCase())){
                    user.changeJob(new Mineur(user));
                } else {
                    sender.sendMessage(Main.getInstance().getErrorPrefix() + " Le métier §c"
                            + data + "§f n'existe pas");
                    return;
                }
                sender.sendMessage(Main.getInstance().getPrefix() + "Le métier de §b"
                        + target
                        + " §fest maintenant §b"
                        + value);
                break;
            default:
                sender.sendMessage(Main.getInstance().getErrorPrefix() + "L'attribut "
                        + data + " n'existe pas !");
                break;
        }
    }

    /**
     * Affiche le menu d'aide de la commande /user
     * @param p Player joueur
     */
    public void showHelp(Player p){
        p.sendMessage("§7--------- §bUser §7----------");
        p.sendMessage("§7/user set {user} {data} {value}");
        p.sendMessage("§7/user add {user} {data} {value}");
        p.sendMessage("§7/user remove {user} {data} {value}");
        p.sendMessage("§7-------------------------");
    }

}
