package com.oblivioussp.spartanweaponry.capability;

import com.oblivioussp.spartanweaponry.init.ModCapabilities;
import com.oblivioussp.spartanweaponry.item.QuiverBaseItem;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.capabilities.ICapabilitySerializable;
import net.neoforged.common.util.LazyOptional;

public class QuiverCapabilityProvider implements ICapabilitySerializable<CompoundTag>
{
	protected ItemStack quiver;
	protected final LazyOptional<QuiverItemStackHandler> handler;
	protected final int inventorySize;

	public QuiverCapabilityProvider(ItemStack stack, int invSize, CompoundTag nbt)
	{
		quiver = stack;
		inventorySize = invSize;
		
		handler = LazyOptional.of(() -> new QuiverItemStackHandler(invSize));
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
	{
		return ModCapabilities.QUIVER_ITEM_CAPABILITY.orEmpty(cap, handler.cast());
	}

	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag tag = handler.resolve().get().serializeNBT();
		quiver.getOrCreateTag().put(QuiverBaseItem.NBT_AMMO, tag);
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) 
	{
/*		if(nbt.contains("Size") && nbt.contains("Items"))
		{
			// Convert current tag to store on item and clear passed tag
			quiver.getOrCreateTag().put(QuiverBaseItem.NBT_AMMO, nbt);
			nbt.remove("Size");
			nbt.remove("Items");
		}*/
		CompoundTag tagCopy = quiver.getOrCreateTag().getCompound(QuiverBaseItem.NBT_AMMO);
		handler.resolve().get().deserializeNBT(tagCopy);
	}

}