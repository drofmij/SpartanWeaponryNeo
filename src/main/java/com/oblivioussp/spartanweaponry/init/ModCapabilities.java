package com.oblivioussp.spartanweaponry.init;

import com.oblivioussp.spartanweaponry.capability.IOilHandler;
import com.oblivioussp.spartanweaponry.capability.IQuiverItemHandler;
import com.oblivioussp.spartanweaponry.util.Log;

import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.capabilities.CapabilityManager;
import net.neoforged.common.capabilities.CapabilityToken;
import net.neoforged.common.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities
{
	public static final Capability<IOilHandler> OIL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IQuiverItemHandler> QUIVER_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
//	public static final Capability<IWeaponTraitContainer<?>> WEAPON_TRAITS = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void registerCapabilities(RegisterCapabilitiesEvent ev)
	{
		ev.register(IOilHandler.class);
		ev.register(IQuiverItemHandler.class);
//		ev.register(IWeaponTraitContainer.class);
		Log.debug("Registered oil capability!");
	}
}
