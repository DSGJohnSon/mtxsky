package fr.skyblock.users;

import fr.skyblock.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class UserCmd implements CommandExecutor {

    private final Main main = Main.getInstance();
    private String[] dataKeys = {"deaths", "coins", "job", ""};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Seul un joueur peut éxecuter cette commande.");
            return true;
        }

        Player p = (Player) sender;

        if(label.equalsIgnoreCase("user")){
            if(args.length != 4){
                main.getUserManager().showHelp(p);
                return true;
            }

            main.getUserManager().changeData(p, args[0], args[1], args[2], args[3]);
        }

        return false;
    }
}
