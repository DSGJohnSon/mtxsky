package fr.skyblock.jobs;

import fr.skyblock.Main;
import fr.skyblock.exceptions.ValParamException;
import fr.skyblock.users.User;

/**
 * Modélise les chomeurs
 */
public class Chomeur implements IJob{

    private final EJob eJob;
    private final User user;

    private final static Main main = Main.getInstance();

    /**
     * Constructeur, renvoie une nouvelle instance de chomeur
     * @param u utilisateur
     */
    public Chomeur(User u){
        if(u == null){
            main.getLogger().warning(getClass().getSimpleName() + " Param null");
            throw new ValParamException(getClass().getSimpleName() + " Param null");
        }

        eJob = EJob.CHOMEUR;
        user = u;
    }

    @Override
    public void applyBonus() {
        user.getPlayer().sendMessage("&cT'as pas de bonus le chomeur");
    }

    @Override
    public EJob getEJob() {
        return eJob;
    }

}
