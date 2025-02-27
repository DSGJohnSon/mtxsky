package fr.skyblock.jobs.types;

import fr.skyblock.Main;
import fr.skyblock.jobs.Job;
import fr.skyblock.jobs.JobInfo;

/**
 * Modélise les chomeurs
 */
public class Chomeur extends Job {

    private final static Main main = Main.getInstance();

    public Chomeur(){
        super(JobInfo.CHOMEUR.getName(), JobInfo.CHOMEUR.getPrice(), JobInfo.CHOMEUR.getIcon());
    }


}
