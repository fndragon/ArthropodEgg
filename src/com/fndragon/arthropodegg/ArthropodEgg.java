/**
 * 
 */
package com.fndragon.arthropodegg;

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
}
