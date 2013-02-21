package com.fndragon.arthropodegg;

import java.util.Map;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;

public class ArthropodEggEntityListener implements Listener {

	public ArthropodEgg plugin;
	
	public ArthropodEggEntityListener( ArthropodEgg instance ) {
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDeath( EntityDeathEvent event ) {
		Player targetPlayer = event.getEntity().getKiller();
		if( null == targetPlayer ) {
			return;
		}
		
		Short currentEntityID = event.getEntity().getType().getTypeId();
		if( false == plugin.getConfig().getShortList("eggEntityIDList").contains( currentEntityID ) ) {
			return;
		}

		// Check for a baby animal
		if( event.getEntity() instanceof Ageable )
		{
			Ageable ageableEntity = (Ageable) event.getEntity();
			if( ageableEntity.isAdult() == false ) {
				return;  // NOPE.
			}
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
		double percentPerLevel = plugin.getConfig().getDouble( "eggdropPercentage" );
		
		if( plugin.getConfig().getBoolean("eggDebug")) {
			targetPlayer.sendMessage( "Ench level is " + handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS));
			targetPlayer.sendMessage( "Target Float is " + percentPerLevel * handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS));
			targetPlayer.sendMessage( "randnum is " + randomNum );
		}
		
		// Get the level of enchantment, multiply by percentPerLevel, compare against random #
		if( randomNum < (percentPerLevel * (double)handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS) ) )
		{
			// Figure out the right item type to drop.
			ItemStack item = new ItemStack(383, 1, event.getEntity().getType().getTypeId());
			event.getDrops().add( item );
		}		
	}
}
