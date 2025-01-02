package fr.skyblock.users;

import fr.skyblock.Main;
import fr.skyblock.exceptions.NotEnoughMoneyException;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.jobs.types.Chomeur;
import fr.skyblock.jobs.IJob;
import fr.skyblock.jobs.types.Mineur;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Class qui modélise les utilisateurs
 */
public class User {

    private final UUID uuid;
    private int deaths;
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
        deaths = 0;
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
     * Retourne le joueur à partir de l'uuid
     * @return Player joueur
     */
    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Retourne le métier du joueur
     * @return IJob métier
     */
    public IJob getJob() {
        return job;
    }

    /**
     * Retourne le nombre de mort du joueur
     * @return int nbr de mort
     */
    public int getDeaths() {
        return deaths;
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
     * Met le nombre de morts à i
     * @param i int morts
     */
    public void setDeaths(int i) {
        deaths = i;
    }

    /**
     * Met l'argent à d
     * @param d double argent
     */
    public void setMoney(double d){
        money = d;
    }

    /**
     * Met le nmbr de chgmt de métier à i
     * @param i int nbr chgmt métier
     */
    public void setJobChangeTimes(int i) {
        jobChangeTimes = i;
    }

    /**
     * Met le métier du joueur associé au nom du métier
     * @param jobName nom du métier
     */
    public void setJob(String jobName) {
        switch (jobName){
            case "chomeur" -> job = new Chomeur(this);
            case "mineur" -> job = new Mineur(this);
            default -> throw new RuntimeException(getClass().getSimpleName() + " Métier non trouvé");
        }
    }

    /**
     * Ajouter des morts au joueur
     * @param i morts
     */
    public void addDeath(final int i){
        deaths += i;
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
     * Change le métier du joueur
     * @param ijob métier
     */
    public void changeJob(IJob ijob) {
        job = ijob;
        jobChangeTimes += 1;
    }
}
