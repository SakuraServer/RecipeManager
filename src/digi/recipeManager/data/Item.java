package digi.recipeManager.data;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Item extends ItemData
{
	private static final long			serialVersionUID	= -4358780777605623605L;
	private int							amount				= 1;
	private int							chance				= 100;
	private Map<Enchantment, Integer>	enchantments		= null;
	
//	public String						special				= null; // TODO test
	
	public Item(int type)
	{
		super(type);
	}
	
	public Item(int type, int amount)
	{
		super(type);
		this.amount = amount;
	}
	
	public Item(int type, int amount, short data)
	{
		super(type, data);
		this.amount = amount;
	}
	
	public Item(int type, int amount, short data, int chance)
	{
		super(type, data);
		this.amount = amount;
		this.chance = chance;
	}
	
	public Item(ItemStack item)
	{
		super(item.getTypeId(), item.getDurability());
		amount = item.getAmount();
		enchantments = (item.getEnchantments().isEmpty() ? null : item.getEnchantments());
	}
	
	@Override
	public ItemStack getItemStack()
	{
		ItemStack item = new ItemStack(type, amount, data);
		
		if(enchantments != null)
			item.addUnsafeEnchantments(enchantments);
		
		return item;
	}
	
	/**
	 * @return enchantments list or NULL if there aren't any
	 */
	public Map<Enchantment, Integer> getEnchantments()
	{
		return enchantments;
	}
	
	public void setEnchantments(Map<Enchantment, Integer> enchantments)
	{
		this.enchantments = enchantments;
	}
	
	public void addEnchantment(Enchantment enchantment, int level)
	{
		if(enchantments == null)
			enchantments = new HashMap<Enchantment, Integer>();
		
		enchantments.put(enchantment, level);
	}
	
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public void setChance(int chance)
	{
		this.chance = chance;
	}
	
	public int getChance()
	{
		return chance;
	}
	
	public String printAuto()
	{
		int enchants = (enchantments == null ? 0 : enchantments.size());
		
		return (type == 0 && chance < 100 ? ChatColor.RED + "" + chance + "% failure chance" : (chance < 100 ? ChatColor.YELLOW + "" + chance + "% chance " + ChatColor.WHITE : "") + (amount > 1 ? amount + "x " : "") + getMaterial().toString().toLowerCase() + (data > 0 ? ":" + data : "") + (enchants > 0 ? " (" + enchants + " enchant" + (enchants == 1 ? "" : "s") + ")" : ""));
	}
	
	@Override
	public String toString()
	{
		return printAuto();
	}
}