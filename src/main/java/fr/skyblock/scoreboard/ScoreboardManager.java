package fr.skyblock.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import fr.skyblock.Main;
import fr.skyblock.users.User;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private final Map<UUID, FastBoard> boards;
    private final Main main = Main.getInstance();

    public ScoreboardManager(){
        boards = new HashMap<>();
    }

    public void addPlayerToBoard(Player p){
        if(p == null){
            throw new RuntimeException(getClass().getSimpleName() + "Joueur null");
        }
        FastBoard board = new FastBoard(p);
        board.updateTitle("Sky§bBlock");
        boards.put(p.getUniqueId(), board);
    }

    public void updateBoard(FastBoard board){
        User user;

        if(main.getUserManager().getUser(board.getPlayer()).isEmpty()) return;
        user = main.getUserManager().getUser(board.getPlayer()).get();

        board.updateLines(
                "",
                "Métier : " + user.getJob().getEJob().getName(),
                "Morts : " + user.getDeaths(),
                "Argent : " + user.getMoney(),
                ""
        );
    }

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }
}
