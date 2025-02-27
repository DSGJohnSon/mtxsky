package fr.skyblock.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public abstract class Job implements IJob {

    protected final String name;
    protected final double price;
    protected final ItemStack icon;

    public Job(String jobName, double jobPrice, ItemStack jobIcon) {
        name = jobName;
        price = jobPrice;
        icon = jobIcon;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ItemStack getIcon() {
        return icon;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, Player p) {}

    @Override
    public void onMobKill(EntityDeathEvent e, Player p) {}

    @Override
    public void onFishCatch(PlayerFishEvent e, Player p) {}

    @Override
    public void applyPassiveEffects(Player p, ItemStack previous, ItemStack current) {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Double.compare(price, job.price) == 0 && Objects.equals(name, job.name) && Objects.equals(icon, job.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, icon);
    }
}
