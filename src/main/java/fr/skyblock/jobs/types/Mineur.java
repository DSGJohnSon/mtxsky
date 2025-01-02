package fr.skyblock.jobs.types;

import fr.skyblock.Main;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.jobs.EJob;
import fr.skyblock.jobs.IJob;
import fr.skyblock.users.User;

/**
 * Modélise le métier mineur
 */
public class Mineur implements IJob {

    private final User user;
    private final EJob eJob;

    private final static Main main = Main.getInstance();

    /**
     * Constructeur, renvoie une nouvelle instance de mineur
     * @param u utilisateur
     */
    public Mineur(User u){
        if(u == null){
            main.getLogger().warning(getClass().getSimpleName() + " Param null");
            throw new ValParamException(getClass().getSimpleName() + " Param null");
        }
        user = u;
        eJob = EJob.CHOMEUR;
    }

    @Override
    public void applyBonus() {
        user.getPlayer().sendMessage("Bonus mineur appliqué !");
    }

    @Override
    public EJob getEJob() {
        return eJob;
    }
}
