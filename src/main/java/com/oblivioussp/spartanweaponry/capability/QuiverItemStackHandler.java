package com.oblivioussp.spartanweaponry.capability;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.items.ItemStackHandler;

public class QuiverItemStackHandler extends ItemStackHandler implements IQuiverItemHandler
{
	public QuiverItemStackHandler(int size)
	{
		super(size);
	}
	
	/**
	 * Resizes the stack list to the specified size. NOTE: If reducing the size of the stack list, any items over the specified size will be LOST
	 * @param size
	 */
	public void resize(int size)
	{
		NonNullList<ItemStack> newStacks = NonNullList.withSize(size, ItemStack.EMPTY);
		
		for(int i = 0; i < newStacks.size(); i++)
		{
			if(i < stacks.size())
				newStacks.set(i, stacks.get(i));
		}
		stacks = newStacks;
	}

	@Override
	public boolean isEmpty() 
	{
		for(ItemStack stack : stacks)
		{
			if(stack.isEmpty())
				return false;
		}
		return true;
	}
}
