package fr.skyblock.jobs;

import fr.skyblock.Main;
import fr.skyblock.exceptions.NotEnoughMoneyException;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.users.User;
import org.bukkit.entity.Player;

/**
 * Permet de gérer les métiers
 */
public class JobManager {

    private final static Main main = Main.getInstance();

    /**
     * Permet de changer de métier
     * @param user utilisateur
     * @param job métier
     */
    public static void changeJob(User user, IJob job){
        if(user == null || job == null){
            main.getLogger().warning(JobManager.class.getSimpleName() + " Param(s) null");
            throw new ValParamException(JobManager.class.getSimpleName() + " Param(s) null");
        }

        Player p = user.getPlayer();
        double price = getNewPrice(job.getEJob().getPrice(), user.getJobChangeTimes());

        if(user.getMoney() < price){
            p.sendMessage(main.getErrorPrefix() + "&7 Tu n'as pas assez d'argent !");
            main.getLogger().info(p.getName() + " a essayé de changer de métier mais n'a pas assez d'argent.");
            throw new NotEnoughMoneyException();
        }

        user.removeMoney(price);
        user.changeJob(job);
        p.sendMessage("Votre nouveau métier est désormais &f" + job.getEJob().getName().toLowerCase() + ".");
        main.getLogger().info(p.getName() + " vient de changer de métier.");
    }

    /**
     * Retourne le nouveau prix du métier en fonction du nombre de changement et du prix du métier
     * @param jobPrice prix
     * @param changes nbr changement
     * @return double nouveau prix
     */
    public static double getNewPrice(double jobPrice, double changes){
        return jobPrice + 50*changes;
    }

}
