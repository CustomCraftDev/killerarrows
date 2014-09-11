package killerarrows;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author DieFriiks / CustomCraftDev / undeaD_D
 * @category KillerArrows plugin
 * @version 1.0
 */
public class Killerarrows extends JavaPlugin {
	
    FileConfiguration config;
    Boolean debug;
    List<String> bl_mobs;
    List<String> bl_players;
    
	
	/**
     * on Plugin enable
     */
	public void onEnable() {
		loadConfig();
    	say("Config loaded");
    	
    	new EventListener(this);
    	say("Eventlistener loaded");
	}

	
	/**
     * on Plugin disable
     */
	public void onDisable() {

	}

	
	/**
     * on Command
     * @param sender - command sender
     * @param cmd - command
     * @param label
     * @return true or false
     */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player)) {
			Player p = (Player)sender;

			if(cmd.getName().equalsIgnoreCase("ohko") && args.length != 0){
				
				// give
				if(args[0].equalsIgnoreCase("give")){
					if(args.length > 1){
						if(p.hasPermission("ohko.give.others")){
							Player target = this.getServer().getPlayer(args[1]);
							if(target != null && target.isOnline()){
							  give(target);
							}
						}
					}
					else{
						if(p.hasPermission("ohko.give.self")){
							give(p);
						}
					}
				}
				
				// disable
				if(args[0].equalsIgnoreCase("disable") && p.hasPermission("ohko.disable")){
						this.setEnabled(false);
						p.sendMessage(ChatColor.RED + "[KillerArrows] was disabled");
						say("disabled by " + p.getName());
					return true;
				}
				
				// reset
				if(args[0].equalsIgnoreCase("reset") && p.hasPermission("ohko.reset")){
					    File configFile = new File(getDataFolder(), "config.yml");
					    configFile.delete();
					    saveDefaultConfig();
						p.sendMessage(ChatColor.RED + "[KillerArrows] config reset");
					    reload();
						p.sendMessage(ChatColor.RED + "[KillerArrows] was reloaded");
						say("reset by " + p.getName());
					return true;
				}
				
				// reload
				if(args[0].equalsIgnoreCase("reload") && p.hasPermission("ohko.reload")){
						reload();
						p.sendMessage(ChatColor.RED + "[KillerArrows] was reloaded");
						say("reloaded by " + p.getName());
					return true;
				}
			}
		}
		
		// commands from console
		else{
			System.out.println("[KillerArrows] Command ingame only ...");
			return true;
		}
		
		// nothing to do here \o/
		return false;
	}
	
	
	/**
     * give player ohko arrow
     */
	private void give(Player target) {
		// TODO Auto-generated method stub
		
	}


	/**
     * load config settings
     */
	private void loadConfig() {
		
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();

		debug = config.getBoolean("debug");
		bl_mobs = (List<String>) config.getStringList("blacklisted-entities");
		bl_players = (List<String>) config.getStringList("blacklisted-players");

	}
	   
    
    /**
     * reload
     */
    private void reload(){
 	   	try {
 	   		// Remove unused variables and objects
			    config = null;
			    debug = null;
			    
			    bl_mobs = null;
			    bl_players = null;

			// Run java garbage collector to delete unused things
			    System.gc();
			
			// load everything again
				this.reloadConfig();
				loadConfig();
			
 	   	} catch (Exception e) {
        	if(debug){
        		e.printStackTrace();
        	}
        }
    }
    
    
    /**
     * print to console
     * @param message to print
     */
	public void say(String out) {
		if(debug){
			System.out.println("[KillerArrows] " + out);
		}
	}	
}
