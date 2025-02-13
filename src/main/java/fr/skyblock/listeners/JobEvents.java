package fr.skyblock.listeners;

import fr.skyblock.Main;
import fr.skyblock.users.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class JobEvents implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e){
        Player p = e.getPlayer();

        if(main.getUserManager().getUser(p).isEmpty()) return;
        User user = main.getUserManager().getUser(p).get();

        user.getJob().onBlockBreak(e, p);
    }

    @EventHandler
    public void onFishEvent(PlayerFishEvent e){
        Player p = e.getPlayer();

        if(main.getUserManager().getUser(p).isEmpty()) return;
        User user = main.getUserManager().getUser(p).get();

        user.getJob().onFishCatch(e, p);
    }

    @EventHandler
    public void onMobKillEvent(EntityDeathEvent e){

        if(!(e.getDamageSource().getCausingEntity() instanceof Player)) return;
        if(e instanceof Player) return;

        Player p = (Player) e.getDamageSource().getCausingEntity();

        if(main.getUserManager().getUser(p).isEmpty()) return;
        User user = main.getUserManager().getUser(p).get();

        user.getJob().onMobKill(e, p);
    }

}
