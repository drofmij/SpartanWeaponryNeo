package com.oblivioussp.spartanweaponry.event;

import java.util.List;

import com.oblivioussp.spartanweaponry.api.IReloadable;
import com.oblivioussp.spartanweaponry.api.ReloadableHandler;
import com.oblivioussp.spartanweaponry.util.Log;

import net.neoforged.event.TagsUpdatedEvent;
import net.neoforged.eventbus.api.EventPriority;
import net.neoforged.eventbus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE)
public class ReloadResourceEventHandler
{
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onUpdateTags(TagsUpdatedEvent ev)
	{
		List<IReloadable> reloadList = ReloadableHandler.getReloadList();
		
		Log.debug("Initaliasing reloadables for " + reloadList.size() + " values");
		long start = System.nanoTime();
		reloadList.forEach((item) -> item.reload());
		long end = System.nanoTime();
		double milliseconds = (end-start) / 1000000.0d;
		Log.info("Finished initialising Weapon Traits & Attributes! Took " + milliseconds + "ms");
	}
}
