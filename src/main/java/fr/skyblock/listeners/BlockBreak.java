package fr.skyblock.listeners;

import fr.skyblock.Main;
import fr.skyblock.users.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e){
        Player p = e.getPlayer();

        if(main.getUserManager().getUser(p).isEmpty()) return;
        User user = main.getUserManager().getUser(p).get();
    }

}
