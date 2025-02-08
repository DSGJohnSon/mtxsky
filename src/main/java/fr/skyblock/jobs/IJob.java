package fr.skyblock.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Interface pour les méthodes que doivent avoir les métiers
 */
public interface IJob {

    /**
     * Bonus lorsque l'on casse un bloque
     */
    void onBlockBreak(BlockBreakEvent e, Player p);

    /**
     * Bonus lorsque l'on tue un mob
     */
    void onMobKill(EntityDeathEvent e, Player p);

    /**
     * Bonus lors de la pêche
     */
    void onFishCatch(PlayerFishEvent e, Player p);

    /**
     * Bonus passifs (sans event)
     */
    void applyPassiveEffects(Player p);

}
