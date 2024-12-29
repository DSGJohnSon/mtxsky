package fr.skyblock.jobs;

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
