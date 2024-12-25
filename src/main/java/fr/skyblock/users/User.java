package fr.skyblock.users;

import fr.skyblock.Main;
import fr.skyblock.exceptions.NotEnoughMoneyException;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.jobs.Chomeur;
import fr.skyblock.jobs.IJob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Class qui modélise les utilisateurs
 */
public class User {

    private final UUID uuid;
    private int death;
    private double money;
    private int jobChangeTimes;
    private IJob job;

    private final static Main main = Main.getInstance();

        /**
         * Constructeur d'utilisateur
         * @param player joueur
         */
    public User(Player player){

        if(player == null){
            main.getLogger().warning(getClass().getSimpleName() + " - Param null !");
            throw new ValParamException(getClass().getSimpleName() + " - Param null !");
        }

        uuid = player.getUniqueId();
        death = 0;
        money = 0;
        jobChangeTimes = 0;
        job = new Chomeur(this);
    }

    /**
     * Retourne l'uuid du joueur
     * @return String uuid
     */
    public String getUUID() {
        return uuid.toString();
    }

    /**
     * Retourne le nombre de mort du joueur
     * @return int nbr de mort
     */
    public int getDeath() {
        return death;
    }

    /**
     * Retourne le l'argent du joueur
     * @return double argent
     */
    public double getMoney() {
        return money;
    }

    /**
     * Retourne le nombre de fois que le joueur
     * a changé de métier
     * @return int nbr
     */
    public int getJobChangeTimes() {
        return jobChangeTimes;
    }

    /**
     * Ajouter des morts au joueur
     * @param i morts
     */
    public void addDeath(final int i){
        death += i;
    }

    /**
     * Ajouter de l'argent au joueur
     * @param d argent
     */
    public void addMoney(final double d){
        money += d;
    }

    /**
     * Retire de l'argent au joueur
     * @param d argent
     * @throws NotEnoughMoneyException exception renvoyée si le solde n'est pas suffisant
     * @throws ValParamException exception renvoyée si le montant à retirer n'est pas strictement positif
     */
    public void removeMoney(final double d) throws NotEnoughMoneyException, ValParamException{
        if(d <= 0) throw new ValParamException(getClass().getSimpleName() + " Param invalide");
        if((money - d) < 0) throw new NotEnoughMoneyException();
        money -= d;
    }

    /**
     * Retourne le métier du joueur
     * @return IJob métier
     */
    public IJob getJob() {
        return job;
    }

    /**
     * Change le métier du joueur
     * @param ijob métier
     */
    public void changeJob(IJob ijob) {
        job = ijob;
        jobChangeTimes += 1;
    }

    /**
     * Retourne le joueur à partir de l'uuid
     * @return Player joueur
     */
    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }
}
