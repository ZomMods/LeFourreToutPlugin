package fr.zomdev.lefourretout;

import fr.zomdev.lefourretout.cmds.LFTCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ZomDev on 09/08/2016.
 */
public class LeFourreTout extends JavaPlugin implements Listener {

    //On définit l'instance
    public static LeFourreTout instance;

    //Méthode permettant de récupérer l'instance
    public static LeFourreTout getInstance() {
        return instance;
    }

    //On définit le PluginManager
    PluginManager pm = Bukkit.getPluginManager();

    @Override
    public void onEnable() {
        //IMPORTANT sinon l'instance ne marchera pas !
        instance = this;

        //Les Commandes
        getCommand("lft").setExecutor(new LFTCommand(this));
        getCommand("lft2").setExecutor(this);

        //Les Events
        pm.registerEvents(this, this);

        //La Config
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        //Si Le joueur est opérateur alors ça envoie le message au serveur
        if (p.isOp()) {
            Bukkit.broadcastMessage(ChatColor.BLUE + "Bienvenue sur Le Fourre-Tout");
            //Sinon on envoie un message au joueur
        } else {
            p.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas la permission d'exécuter cette commande !");
        }

        return false;
    }
    //Quand le joueur casse un bloc d'herbe en sneak on lui envoie un message sinon on lui envoie un message d'erreur
    @EventHandler
    public void onGrassBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if (b.getType() == Material.GRASS) {
            if (!p.isSneaking()) {
                p.sendMessage(ChatColor.DARK_RED + "Vous devez être en sneak pour activer l'event !");
            } else {
                p.sendMessage(ChatColor.GREEN + "Vous avez cassé un block de " + ChatColor.GOLD + b.getType().name());
            }
        }
    }
    //Quand le joueur casse un bloc de dirt et qu'il n'a pas la permission on lui envoie un message d'erreur
    @EventHandler
    public void onDirtPlace(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();
        Player p = e.getPlayer();
        if (b.getType() == Material.DIRT) {
            if (!p.hasPermission("lft.lava.place")) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + " Vous n'avez pas la permission de poser de la lave !");
            } else {
                e.setCancelled(false);
            }
        }
    }

}
