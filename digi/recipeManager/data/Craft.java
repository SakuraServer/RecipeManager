package digi.recipeManager.data;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import digi.recipeManager.Messages;

public class Craft extends Recipe
{
	private ItemData[]	ingredients;
	private List<Item>	results;
	
	public Craft(ItemData[] ingredients, List<Item> results, Recipe recipeData)
	{
		super(recipeData);
		this.ingredients = ingredients;
		this.results = results;
	}
	
	/**
	 * Get all ingredients for this recipe
	 * 
	 * @return List of ingredient items
	 */
	public ItemData[] getIngredients()
	{
		return ingredients;
	}
	
	/**
	 * Get all of the results in a list
	 * You shouldn't use this but it's here if needed
	 * 
	 * @return List of result items
	 */
	public List<Item> getResults()
	{
		return results;
	}
	
	/**
	 * Gets the result of the recipe or gets a random one from the list if there are more
	 * The same method is used by the plugin when crafting, use this if you want to emulate crafting
	 * Returns AIR if chance of failure occured
	 * 
	 * @return The result item
	 */
	public Item getResult()
	{
		if(results.size() == 1)
			return results.get(0);
		
		int rand = new Random().nextInt(100);
		int chance = 0;
		Item result = null;
		
		for(Item item : results)
		{
			if((chance += item.getChance()) > rand)
			{
				result = item;
				break;
			}
		}
		
		return result;
	}
	
	@Override
	public String[] print()
	{
		String[] slots = new String[9];
		List<String> str = new ArrayList<String>();
		HashMap<String, Character> charItems = new HashMap<String, Character>();
		char charIndex = 'a';
		Character charStr;
		
		for(int i = 0; i < 9; i++)
		{
			if(ingredients[i] == null)
				slots[i] = ChatColor.GRAY + "[" + ChatColor.BLACK + "_" + ChatColor.GRAY + "]";
			else
			{
				charStr = charItems.get(ingredients[i].printItemData());
				
				if(charStr == null)
				{
					charItems.put(ingredients[i].printItemData(), charIndex);
					charStr = charIndex;
					charIndex++;
				}
				
				slots[i] = ChatColor.GRAY + "[" + ChatColor.BLUE + charStr + ChatColor.GRAY + "]";
			}
		}
		
		str.add(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "Workbench shaped recipe:");
		str.add(slots[0] + slots[1] + slots[2]);
		str.add(slots[3] + slots[4] + slots[5]);
		str.add(slots[6] + slots[7] + slots[8]);
		
		for(Entry<String, Character> entry : charItems.entrySet())
		{
			str.add("  " + ChatColor.BLUE + entry.getValue() + ChatColor.GRAY + " = " + ChatColor.WHITE + entry.getKey());
		}
		
		int offset = str.size();
		String[] display = new String[offset + results.size()];
		
		for(int i = 0; i < offset; i++)
		{
			display[i] = str.get(i);
		}
		
		for(int i = 0; i < results.size(); i++)
		{
			display[i + offset] = ChatColor.GREEN + " => " + ChatColor.WHITE + results.get(i).printAuto();
		}
		
		return display;
	}
	
	public void sendLog(String playerName, Item result)
	{
		if(log)
		{
			StringBuffer string = new StringBuffer();
			
			for(ItemData item : ingredients)
			{
				if(item != null)
					string.append("+" + item.printItemData());
			}
			
			Messages.log("[@log] " + (playerName == null ? "(unknown player)" : playerName) + " crafted " + string.deleteCharAt(0).toString() + " to make " + (result == null ? "nothing" : result.printAuto()));
		}
	}
}