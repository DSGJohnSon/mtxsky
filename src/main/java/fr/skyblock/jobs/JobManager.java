package fr.skyblock.jobs;

import fr.skyblock.Main;
import fr.skyblock.exceptions.JobAlreadyTakenException;
import fr.skyblock.exceptions.NotEnoughMoneyException;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.lang.Lang;
import fr.skyblock.lang.LangValue;
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
    public static void changeJob(User user, Job job){
        if(user == null || job == null){
            main.getLogger().warning(JobManager.class.getSimpleName() + " Param(s) null");
            throw new ValParamException(JobManager.class.getSimpleName() + " Param(s) null");
        }

        Player p = user.getPlayer();

        if(user.getJob().equals(job)){
           throw new JobAlreadyTakenException();
        }

        double price = getNewPrice(job.getPrice(), user.getJobChangeTimes());

        if(user.getMoney() < price){
            throw new NotEnoughMoneyException();
        }

        user.removeMoney(price);
        user.changeJob(job);
        p.sendMessage(Lang.getPrefix() + Lang.NEW_JOB.get()
                .replace(LangValue.JOB.getName(), job.getName().toLowerCase()));
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
