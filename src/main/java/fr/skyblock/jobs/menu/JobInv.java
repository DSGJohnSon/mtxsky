package fr.skyblock.jobs.menu;

import fr.skyblock.Main;
import fr.skyblock.exceptions.JobAlreadyTakenException;
import fr.skyblock.exceptions.NotEnoughMoneyException;
import fr.skyblock.jobs.EJob;
import fr.skyblock.jobs.JobManager;
import fr.skyblock.jobs.types.Mineur;
import fr.skyblock.lang.Lang;
import fr.skyblock.users.User;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JobInv implements Listener, CommandExecutor {

    private final Inventory inv;
    private final String invName = "§7Menu des métiers";

    private final static Main main = Main.getInstance();

    public JobInv(){
        inv = Bukkit.createInventory(null, 9, Component.text(invName));
    }

    public void setupInv(Player p){
        User user;
        double price;

        if(p == null) return;
        if(main.getUserManager().getUser(p).isEmpty()) return;
        user = main.getUserManager().getUser(p).get();

        price = JobManager.getNewPrice(EJob.MINEUR.getPrice(), user.getJobChangeTimes());
        inv.setItem(4, EJob.MINEUR.getItem(price));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Seul un joueur peut executer cette commande !");
            return true;
        }

        if(label.equalsIgnoreCase("job")){
            Player p = (Player) sender;
            setupInv(p);
            p.openInventory(inv);
        }

        return false;
    }

    @EventHandler
    public void onJobClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        User user;

        Inventory inv = e.getClickedInventory();
        ItemStack current = e.getCurrentItem();

        if(!e.getView().title().equals(Component.text(invName))) return;
        if(current == null) return;
        if(main.getUserManager().getUser(p).isEmpty()) return;

        user = main.getUserManager().getUser(p).get();

        e.setCancelled(true);
        p.closeInventory();

        if (current.getType() == EJob.MINEUR.getIcon().getType()){
            try {
                JobManager.changeJob(user, new Mineur(user));
            } catch (NotEnoughMoneyException exception){
                p.sendMessage(Lang.getErrorPrefix() + "&7 Tu n'as pas assez d'argent !");
                main.getLogger().info(p.getName() + " a essayé de changer de métier mais n'a pas assez d'argent.");
                exception.getSuppressed();
            } catch (JobAlreadyTakenException exception){
                p.sendMessage(Lang.getErrorPrefix() + "&7 Tu as déjà ce métier !");
                exception.getSuppressed();
            }
        }
    }
}
