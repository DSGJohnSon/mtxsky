package fr.skyblock.jobs;

import fr.skyblock.users.User;
import org.bukkit.inventory.ItemStack;

/**
 * Interface pour les méthodes que doivent avoir les métiers
 */
public interface IJob {

    /**
     * Applique le bonus du métier
     */
    void applyBonus();

    /**
     * Retourne l'énum associée au métier
     * @return EJob ejob
     */
    EJob getEJob();
}
