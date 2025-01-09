package fr.skyblock.scoreboard;

import fr.skyblock.Main;
import fr.skyblock.scoreboard.fastboard.FastBoard;
import org.bukkit.scheduler.BukkitRunnable;

public class SboreboardRunnable extends BukkitRunnable {

    private final Main main = Main.getInstance();

    @Override
    public void run() {
        for (FastBoard board : main.getScoreboardbManager().getBoards().values()) {
            main.getScoreboardbManager().updateBoard(board);
        }
    }
}
