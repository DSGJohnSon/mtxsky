package fr.skyblock.users;

import fr.skyblock.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UserCmd implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Seul un joueur peut éxecuter cette commande.");
            return true;
        }

        Player p = (Player) sender;

        if(label.equalsIgnoreCase("user")){
            if(args.length == 4){
                if(args[0].equalsIgnoreCase("set")
                        || args[0].equalsIgnoreCase("add")
                        || args[0].equalsIgnoreCase("remove")){
                    main.getUserManager().changeData(p, args[0], args[1], args[2], args[3]);
                    return false;
                } else {
                    main.getUserManager().showHelp(p);
                    return true;
                }
            } else if(args.length == 2){
                if(args[0].equalsIgnoreCase("stats")){
                    main.getUserManager().showStats(args[1]);
                } else {
                    main.getUserManager().showHelp(p);
                    return true;
                }
            } else {
                main.getUserManager().showHelp(p);
                return true;
            }
        }

        return false;
    }
}
