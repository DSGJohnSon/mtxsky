package fr.skyblock.users;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Permet de gérer les utilisateurs
 */
public class UserManager {

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


}
