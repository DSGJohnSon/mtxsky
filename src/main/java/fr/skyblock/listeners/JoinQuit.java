package fr.skyblock.listeners;

import fr.skyblock.Main;
import fr.skyblock.lang.Lang;
import fr.skyblock.lang.LangValue;
import fr.skyblock.scoreboard.fastboard.FastBoard;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Gère les évènements la connection et déconnection des joueurs
 */
public class JoinQuit implements Listener {

    private final Main main = Main.getInstance();

    /**
     * S'active quand un joueur rejoint le serveur
     * @param e PlayerJoinEvent
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = (Player) e.getPlayer();

        main.getUserManager().onLogin(p);
        main.getUsersFile().readData(p);

        main.getScoreboardbManager().addPlayerToBoard(p);

        e.joinMessage(Component.text(Lang.PLAYER_JOIN.get()
                .replace(LangValue.PLAYER.getName(), p.getName())));
    }

    /**
     * S'active quand un joueur quitte le serveur
     * @param e PlayerQuitEvent
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = (Player) e.getPlayer();

        main.getUsersFile().writeData(p);
        main.getUserManager().delete(p);

        FastBoard board = main.getScoreboardbManager().getBoards().remove(p.getUniqueId());

        if (board != null) {
            board.delete();
        }


        e.quitMessage(Component.text(Lang.PLAYER_QUIT.get()
                .replace(LangValue.PLAYER.getName(), p.getName())));
    }
}
