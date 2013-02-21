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
		double eggArthropodPercentage = plugin.getConfig().getDouble( "eggArthropodPercentage" );
		double eggLootingPercentage = plugin.getConfig().getDouble( "eggLootingPercentage" );
		double levelOfArthropod = handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS);
		double levelOfLooting = handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS);
		
		double targetPercentage = (eggArthropodPercentage * levelOfArthropod) + (eggLootingPercentage * levelOfLooting);
		if( plugin.getConfig().getBoolean("eggDebug")) {
			targetPlayer.sendMessage( "Arth[" + levelOfArthropod + "], Loot[" + levelOfLooting + "]");
			targetPlayer.sendMessage( "Total =" + targetPercentage * 100 + "%, random% is " + randomNum * 100 );
		}
		
		// Get the level of enchantment, multiply by percentPerLevel, compare against random #
		if( randomNum < (eggArthropodPercentage * (double)handstack.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS) ) )
		{
			// Figure out the right item type to drop.
			ItemStack item = new ItemStack(383, 1, event.getEntity().getType().getTypeId());
			if( plugin.getConfig().getBoolean("eggRemoveDrops")) {
				event.getDrops().clear();
				event.setDroppedExp(0);
			}
			event.getDrops().add( item );
			if( plugin.getConfig().getBoolean("eggDebug")) {
				targetPlayer.sendMessage( "Egg generated." );
			}
		}		
	}
}
