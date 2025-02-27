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
 * Modélise le métier mineur
 */
public class Mineur extends Job {
    private final Random random = new Random();

    private final Set<Material> pickaxes = EnumSet.of(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.NETHERITE_PICKAXE
    );

    private final Set<Material> ores = EnumSet.of(
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.LAPIS_ORE,
            Material.COPPER_ORE,
            Material.REDSTONE_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_REDSTONE_ORE
    );

    public Mineur(){
        super(JobInfo.MINEUR.getName(), JobInfo.MINEUR.getPrice(), JobInfo.MINEUR.getIcon());
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, Player p) {
        if(ores.contains(e.getBlock().getType())){
            if(random.nextDouble() < 0.1){
                e.getBlock().getDrops().forEach(drop -> {
                    p.getInventory().addItem(drop);
                });
            }
        }
    }

    @Override
    public void applyPassiveEffects(Player p, ItemStack previous, ItemStack current) {

        if(current != null && pickaxes.contains(current.getType())){
            p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 9999, 0, false ,false));
        }

        if(previous != null && pickaxes.contains(previous.getType())){
            if(current == null || !pickaxes.contains(current.getType())){
                if(p.hasPotionEffect(PotionEffectType.HASTE)) p.removePotionEffect(PotionEffectType.HASTE);
            }
        }
    }
}
