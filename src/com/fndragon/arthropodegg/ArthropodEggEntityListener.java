package com.fndragon.arthropodegg;

import java.util.Map;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;

public class ArthropodEggEntityListener implements Listener {

	@EventHandler
	public void onEntityDeath( EntityDeathEvent event ) {
		Player targetPlayer = event.getEntity().getKiller();
		if( null == targetPlayer )
			return;
		
		// Check the type of enemy.  Currently this is hardcoded to those
		// mobs that are PASSIVE.
		switch( event.getEntity().getType().getTypeId() ) {
			default:
				return;
			case 90: // PIG
			case 91: // SHEEP
			case 92: // COW
			case 93: // CHICKEN
			case 94: // SQUID
			case 95: // WOLF
			case 96: // MUSHROOM_COW
			case 97: // OCELOT
			case 98: // VILLAGER
		}
		
		// Check the player's currently equipped weapon
		ItemStack handstack = targetPlayer.getItemInHand();
		// Get the map of enchantments on that item
		Map<Enchantment,Integer> itemEnchants = handstack.getEnchantments();
		if(itemEnchants.isEmpty()) {
			return;
		}
		// Check if one enchantment is BaneOfArthropods
		if( null == itemEnchants.get( org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS ) )
		{
			return;
		}
		double randomNum = Math.random();
		//targetPlayer.sendMessage( "Ench level is " + handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS));
		//targetPlayer.sendMessage( "Target Float is " + 0.05 * handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS));
		//targetPlayer.sendMessage( "randnum is " + randomNum );
		
		// Get the level of enchantment, 5% for each level of arthropods for an egg to pop out
		if( randomNum < (0.05 * (double)handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS) ) )
		{
			// Figure out the right item type to drop.
			ItemStack item = new ItemStack(383, 1, event.getEntity().getType().getTypeId());
			event.getDrops().add( item );
		}		
	}
}
