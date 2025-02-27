package fr.skyblock.jobs.types;

import fr.skyblock.jobs.Job;
import fr.skyblock.jobs.JobInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

/**
 * Modélise le métier bucheron
 */
public class Bucheron extends Job {
    private final Random random = new Random();

    private final Set<Material> axes = EnumSet.of(
            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.GOLDEN_AXE,
            Material.DIAMOND_AXE,
            Material.NETHERITE_AXE
    );

    private final Set<Material> logs = EnumSet.of(
            Material.OAK_LOG,
            Material.BIRCH_LOG,
            Material.SPRUCE_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG,
            Material.CHERRY_LOG,
            Material.PALE_OAK_LOG
    );

    public Bucheron() {
        super(JobInfo.BUCHERON.getName(), JobInfo.BUCHERON.getPrice(), JobInfo.BUCHERON.getIcon());
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, Player p) {
        if(logs.contains(e.getBlock().getType())){
            if(random.nextDouble() < 0.2){
                p.getInventory().addItem(new ItemStack(e.getBlock().getType(), 1));
            }
        }
    }

    @Override
    public void applyPassiveEffects(Player p, ItemStack previous, ItemStack current) {

        if(current != null && axes.contains(current.getType())){
            p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 9999, 0, false ,false));
        }

        if(previous != null && axes.contains(previous.getType())){
            if(current == null || !axes.contains(current.getType())){
                if(p.hasPotionEffect(PotionEffectType.HASTE)) p.removePotionEffect(PotionEffectType.HASTE);
            }
        }
    }

}
