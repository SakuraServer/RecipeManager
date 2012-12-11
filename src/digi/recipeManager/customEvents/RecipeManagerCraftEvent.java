package digi.recipeManager.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.inventory.ItemStack;

import digi.recipeManager.RecipeManager;
import digi.recipeManager.data.*;

/**
 * Event triggered when RecipeManager's custom recipes are crafted/combined in the workbench.<br>
 * Player can return null in certain situations, so be sure to prepare for that situation.<br>
 * Event can be cancelled to prevent the action.<br>
 * Event only triggers when crafted item is actually taken from the result slot, if you want to register all clicks on the result, use CraftItemEvent instead.
 * 
 * @author Digi
 */
public class RecipeManagerCraftEvent extends Event implements Cancellable
{
	private static final HandlerList	handlers	= new HandlerList();
	private boolean						cancelled	= false;
	private boolean						shiftClick	= false;
	private boolean						rightClick	= false;
	private Item						result;
	private ItemStack					cursor;
	private Recipe						recipe;
	private Player						player;
	
	public RecipeManagerCraftEvent(Recipe recipe, Item result, Player player, ItemStack cursor, boolean shiftClick, boolean rightClick)
	{
		this.recipe = recipe;
		this.result = result;
		this.player = player;
		this.cursor = cursor;
		this.shiftClick = shiftClick;
		this.rightClick = rightClick;
	}
	
	/**
	 * @return player or null if crafted by automated plugins
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * @return the recipe
	 */
	public Recipe getRecipe()
	{
		return recipe;
	}
	
	/**
	 * @return result item or AIR if chance of failure occured
	 */
	public Item getResult()
	{
		return result;
	}
	
	/**
	 * Sets the result to the specified item.<br>
	 * Set to AIR to mark as failed.
	 * 
	 * @param result
	 *            the Item result
	 */
	public void setResult(Item result)
	{
		this.result = result;
	}
	
	/**
	 * Sets the result to the specified item.<br>
	 * Set to AIR to mark as failed.<br>
	 * Shortcut for: setResult(new Item(result));
	 * 
	 * @param result
	 *            the ItemStack result
	 */
	public void setResult(ItemStack result)
	{
		this.result = new Item(result);
	}
	
	/**
	 * @return The item in the player's cursor or null
	 */
	public ItemStack getCursorItem()
	{
		return cursor;
	}
	
	/**
	 * Checks if result can be taken by the player.<br>
	 * Cehcks against full inventory on shift-click or full cursor.<br>
	 * Will return true if player is null.<br>
	 * You should use this method after you changed the result and you want to know if you can give rewards for the taken item or something.
	 * 
	 * @return can result be taken ?
	 */
	public boolean isResultTakeable()
	{
		return (player == null || RecipeManager.getRecipes().isResultTakeable(player, result, cursor, shiftClick));
	}
	
	/**
	 * @return Was it a Shift+Click ?
	 */
	public boolean isShiftClick()
	{
		return shiftClick;
	}
	
	/**
	 * Shortcut for: !isRightClick()
	 * 
	 * @return Was the click a LeftClick ?
	 */
	public boolean isLeftClick()
	{
		return !rightClick;
	}
	
	/**
	 * @return Was the click a RightClick ?
	 */
	public boolean isRightClick()
	{
		return rightClick;
	}
	
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	
	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
}
