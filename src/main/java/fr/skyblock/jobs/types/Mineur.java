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
 * Modélise le métier mineur
 */
public class Mineur extends Job {
    private final Random random = new Random();

    public Mineur(){
        super(JobInfo.MINEUR.getName(), JobInfo.MINEUR.getPrice(), JobInfo.MINEUR.getIcon());
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, Player p) {
        if(e.getBlock().getType() == Material.COAL_ORE
                || e.getBlock().getType() == Material.IRON_ORE
                || e.getBlock().getType() == Material.DIAMOND_ORE
                || e.getBlock().getType() == Material.GOLD_ORE){
            if(random.nextDouble() < 0.1){
                p.getInventory().addItem(new ItemStack(e.getBlock().getType(), 1));
            }
        }
    }

    @Override
    public void applyPassiveEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 9999, 1, false ,false));
    }
}
