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

	/**
	 * 
	 */
	/*public ArthropodEgg() {
		// TODO Auto-generated constructor stub
	}*/

	@Override
	public void onEnable() {
		getLogger().info("ArthropodEgg has been enabled.");
		getServer().getPluginManager().registerEvents( new ArthropodEggEntityListener(), this );
	}
	
	@Override
	public void onDisable() {
		getLogger().info("ArthropodEgg has been disabled.");
	}
}
