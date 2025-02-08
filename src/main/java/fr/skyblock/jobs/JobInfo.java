package fr.skyblock.jobs;

public enum JobName {

    CHOMEUR("Chomeur"),
    MINEUR("Mineur"),
    BUCHERON("Bucheron");

    private final String jobName;

    JobName(String name){
        jobName = name;
    }

    public String getJobName() {
        return jobName;
    }
}
