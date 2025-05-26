package com.oblivioussp.spartanweaponry.init;

import com.oblivioussp.spartanweaponry.client.model.OilCoatedItemModel;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.client.event.ModelEvent;
import net.neoforged.eventbus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class ModModelLoaders
{
	@SubscribeEvent
	public static void register(ModelEvent.RegisterGeometryLoaders ev)
	{
		ev.register("oil_coated_item", OilCoatedItemModel.Loader.INSTANCE);
	}
}

/*import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.client.model.OilCoatedItemModel;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.client.event.ModelRegistryEvent;
import net.neoforged.client.model.ModelLoaderRegistry;
import net.neoforged.eventbus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModModelLoaders 
{
	public static void register()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.<ModelRegistryEvent>addListener(ev -> ModelLoaderRegistry.registerLoader(new ResourceLocation(ModSpartanWeaponry.ID, "oil_coated_item"), OilCoatedItemModel.Loader.INSTANCE));
	}
}*/
