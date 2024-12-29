package fr.skyblock.users;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UserCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Seul un joueur peut éxecuter cette commande.");
            return true;
        }


        Player p = (Player) sender;

        if(label.equalsIgnoreCase("user")){
            if(args.length == 0){

            }
        }

        return false;
    }
}
