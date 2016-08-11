package fr.zomdev.lefourretout.cmds;

import fr.zomdev.lefourretout.LeFourreTout;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by Thomas on 09/08/2016.
 */
public class LFTCommand implements CommandExecutor {

    private final LeFourreTout pl;
    private final FileConfiguration config;

    public LFTCommand(LeFourreTout pl) {
        this.pl = pl;
        this.config = pl.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        //Si la commande est exécutée alors on téléporte le joueur aux coordonnées dans la config
        if (args.length == 0) {
            p.teleport(new Location(Bukkit.getWorld(config.getString("Spawn.World")), config.getDouble("Spawn.X"), config.getDouble("Spawn.Y"), config.getDouble("Spawn.Z"), (float)config.get("Spawn.Yaw"),(float)config.get("Spawn.Pitch")));
        }else if(args.length == 1){
            //Si l'argument est "set" alors on stock dans la config les coordonnées du joueur ainsi que son orientation
            if(args[0].equalsIgnoreCase("set")){
                config.set("Spawn.X", p.getLocation().getX());
                config.set("Spawn.Y", p.getLocation().getY());
                config.set("Spawn.Z", p.getLocation().getZ());
                config.set("Spawn.Yaw", p.getLocation().getYaw());
                config.set("Spawn.Pitch", p.getLocation().getPitch());
                config.set("Spawn.World", p.getWorld().getName());
                pl.saveConfig();
                p.sendMessage(ChatColor.GREEN + "Vous avez changé la location du lft !");
            }
        }
        return false;
    }
}
