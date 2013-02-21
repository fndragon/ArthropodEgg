/**
 * 
 */
package com.fndragon.arthropodegg;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import com.fndragon.arthropodegg.ArthropodEggEntityListener;

/**
 * @author Randy
 *
 */
public class ArthropodEgg extends JavaPlugin {

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		getLogger().info("ArthropodEgg has been enabled.");
		getServer().getPluginManager().registerEvents( new ArthropodEggEntityListener(this), this );
	}
	
	@Override
	public void onDisable() {
		getLogger().info("ArthropodEgg has been disabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("arthropodegg")) {
			// permission to run this command is already checked by plugin.yml
			
			// Check for no arguments, return false
			if( args.length == 0 ) {
				return false;
			}
			if( args[0].equalsIgnoreCase("version")) {
				sender.sendMessage( this.getDescription().getFullName() ); // automatically appends version
			}
			if( args[0].equalsIgnoreCase("debug")) {
				if(args.length < 2) {
					return false;
				}
				Boolean eggDebug = args[1].equalsIgnoreCase("on");
				this.getConfig().set( "eggDebug", eggDebug );
				if( eggDebug ) {
					sender.sendMessage("ArthropodEgg debug enabled");
				} else {
					sender.sendMessage("ArthropodEgg debug disabled");
				}
			}
			if( args[0].equalsIgnoreCase("list")) {
				for( Short id : this.getConfig().getShortList("eggEntityIDList")) {
					sender.sendMessage( "ID #" + id + ": " + org.bukkit.entity.EntityType.fromId( (int) id).getName());
				}
			}
			if( args[0].equalsIgnoreCase("add")) {
				if( args.length < 2) {
					return false;
				}
				// Future expansion:  handle multiple inputs at once
				List<Short> idList = this.getConfig().getShortList("eggEntityIDList");
				Short idToAdd = (short) Short.parseShort( args[1] );
				if( idList.contains( idToAdd )) {
					sender.sendMessage( "Already in list");
				} else {
					idList.add( idToAdd );
					this.getConfig().set("eggEntityIDList", idList);
					sender.sendMessage( "ID #" +idToAdd+ ": " + org.bukkit.entity.EntityType.fromId((int)idToAdd).getName() + " added");
				}
			}
			if( args[0].equalsIgnoreCase("remove")) {
				if( args.length < 2) {
					return false;
				}
				// Future expansion:  handle multiple inputs at once
				List<Short> idList = this.getConfig().getShortList("eggEntityIDList");
				Short idToDel = (short) Short.parseShort( args[1] );
				if( false == idList.contains( idToDel )) {
					sender.sendMessage( "Not in list");
				} else {
					idList.remove( idToDel );
					this.getConfig().set("eggEntityIDList", idList);
					sender.sendMessage( "ID #" +idToDel+ ": " + org.bukkit.entity.EntityType.fromId((int)idToDel).getName() + " removed");
				}
			}
			if( args[0].equalsIgnoreCase("reset")) {
				saveResource("config.yml", true);
				this.reloadConfig();
				sender.sendMessage("Reset configuration");
			}
			if( args[0].equalsIgnoreCase("reload")) {
				this.reloadConfig();
				sender.sendMessage("Reloaded configuration");
			}
			if( args[0].equalsIgnoreCase("save")) {
				this.saveConfig();
				sender.sendMessage("Saved configuration");
			}
			if( args[0].equalsIgnoreCase("arthropodrate")) {
				if( args.length < 2) {
					return false;
				}
				double droprate = Double.parseDouble( args[1] );
				// Human error check. If droprate > 1.0, they probably specified
				// the value in actual %, not (0..1]
				if( droprate > 1.0 ) {
					droprate = (droprate / 100);
				}
				this.getConfig().set("eggArthropodPercentage", droprate);
				sender.sendMessage("Set each level of Bane of Arthropods to " + droprate * 100 + "%");
				sender.sendMessage("Without looting, this gives a maximum of " + droprate * 5 * 100 + "%");
				sender.sendMessage("With looting, this gives a maximum of " + (droprate * 5 * 100 + this.getConfig().getDouble( "eggLootingPercentage" ) * 3 * 100) + "%");
			}
			if( args[0].equalsIgnoreCase("lootingrate")) {
				if( args.length < 2) {
					return false;
				}
				double lootingrate = Double.parseDouble( args[1] );
				this.getConfig().set("eggLootingPercentage", lootingrate);
				sender.sendMessage("Set each level of Looting to " + lootingrate * 100 + "%");
				sender.sendMessage("With looting, this gives a maximum of " + (lootingrate * 3 * 100 + this.getConfig().getDouble( "eggArthropodPercentage" ) * 5 * 100) + "%");
			
			}
			if( args[0].equalsIgnoreCase("removedrops")) {
				if( args.length < 2) {
					return false;
				}
				Boolean removedrops = args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("true");
				this.getConfig().set("eggRemoveDrops", removedrops);
				if( removedrops ) {
					sender.sendMessage("All xp and mob drops removed if egg is generated");
				} else {
					sender.sendMessage("All xp and mob drops preserved if egg is generated");
				}
					
			}
			return true;
		}
		return false;
	}
}
