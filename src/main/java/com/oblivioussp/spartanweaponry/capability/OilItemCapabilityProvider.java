package com.oblivioussp.spartanweaponry.capability;

import com.oblivioussp.spartanweaponry.init.ModCapabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.capabilities.ICapabilityProvider;
import net.neoforged.common.util.LazyOptional;

public class OilItemCapabilityProvider implements ICapabilityProvider
{	
	protected final LazyOptional<IOilHandler> oilHandler;
	
	public OilItemCapabilityProvider(ItemStack stackIn)
	{
		oilHandler = LazyOptional.of(() -> new OilHandler(stackIn));
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
	{
		return ModCapabilities.OIL_CAPABILITY.orEmpty(cap, oilHandler);
	}

}
