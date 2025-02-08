package fr.skyblock.jobs.types;

import fr.skyblock.jobs.Job;
import fr.skyblock.jobs.JobInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * Modélise le métier bucheron
 */
public class Bucheron extends Job {
    private final Random random = new Random();

    public Bucheron() {
        super(JobInfo.BUCHERON.getName(), JobInfo.BUCHERON.getPrice(), JobInfo.BUCHERON.getIcon());
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, Player p) {
        if(e.getBlock().getType() == Material.OAK_LOG
                || e.getBlock().getType() == Material.BIRCH_LOG
                || e.getBlock().getType() == Material.SPRUCE_LOG
                || e.getBlock().getType() == Material.JUNGLE_LOG
                || e.getBlock().getType() == Material.ACACIA_LOG
                || e.getBlock().getType() == Material.DARK_OAK_LOG
                || e.getBlock().getType() == Material.MANGROVE_LOG
                || e.getBlock().getType() == Material.CHERRY_LOG
                || e.getBlock().getType() == Material.PALE_OAK_LOG){
            if(random.nextDouble() < 0.2){
                p.getInventory().addItem(new ItemStack(e.getBlock().getType(), 1));
            }
        }
    }

    @Override
    public void applyPassiveEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 9999, 1, false ,false));
    }

}
