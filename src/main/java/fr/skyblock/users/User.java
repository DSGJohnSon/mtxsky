package fr.skyblock.users;

import fr.skyblock.Main;
import fr.skyblock.exceptions.NotEnoughMoneyException;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.jobs.Job;
import fr.skyblock.jobs.types.Chomeur;
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
    private Job job;

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
        job = new Chomeur();
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
     * @return Job métier
     */
    public Job getJob() {
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
            case "chomeur" -> job = new Chomeur();
            case "mineur" -> job = new Mineur();
            default -> throw new RuntimeException(getClass().getSimpleName() + " Métier non trouvé");
        }
    }

    /**
     * Ajouter des morts au joueur
     * @param i morts
     */
    public void addDeaths(final int i){
        deaths += i;
    }

    /**
     * Retire des morts au joueur
     * @param i morts
     */
    public void removeDeaths(final int i){
        deaths -= i;
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
     * Ajoute i au nbr de chgmt de métier
     * @param i int
     */
    public void addJobChangeTimes(int i) {
        jobChangeTimes += i;
    }

    /**
     * Retire i au nbr de chgmt de métier
     * @param i int
     */
    public void removeJobChangeTimes(int i) {
        jobChangeTimes -= i;
    }

    /**
     * Change le métier du joueur
     * @param newJob métier
     */
    public void changeJob(Job newJob) {
        job = newJob;
        jobChangeTimes += 1;
    }
}
