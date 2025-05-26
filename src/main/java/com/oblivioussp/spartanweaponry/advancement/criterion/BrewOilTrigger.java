package com.oblivioussp.spartanweaponry.advancement.criterion;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.OilEffects;
import com.oblivioussp.spartanweaponry.api.oil.OilEffect;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.neoforged.registries.RegistryManager;

public class BrewOilTrigger extends SimpleCriterionTrigger<BrewOilTrigger.TriggerInstance> 
{
	private static final String JSON_OIL = "oil_effect";
	private static final ResourceLocation ID = new ResourceLocation(ModSpartanWeaponry.ID, "brew_oil");

	@Override
	public ResourceLocation getId()
	{
		return ID;
	}

	@Override
	protected TriggerInstance createInstance(JsonObject jsonIn, ContextAwarePredicate predicateIn, DeserializationContext contextIn)
	{
		OilEffect oilEffect = null;
		if(jsonIn.has(JSON_OIL))
		{
			ResourceLocation loc = new ResourceLocation(GsonHelper.getAsString(jsonIn, JSON_OIL));
			oilEffect = RegistryManager.ACTIVE.getRegistry(OilEffects.REGISTRY_KEY).getValue(loc);
			if(oilEffect == null)
				throw new JsonSyntaxException("Unknown Oil Effect: '" + loc + "'");
		}
		return new BrewOilTrigger.TriggerInstance(predicateIn, oilEffect);
	}
	
	public void trigger(ServerPlayer playerIn, OilEffect oilEffectIn)
	{
		trigger(playerIn, (triggerInstance) -> triggerInstance.matches(oilEffectIn));
	}
	
	public static class TriggerInstance extends AbstractCriterionTriggerInstance
	{
		private final OilEffect oilEffect;

		public TriggerInstance(ContextAwarePredicate predicateIn, @Nullable OilEffect oilEffectIn) 
		{
			super(BrewOilTrigger.ID, predicateIn);
			oilEffect = oilEffectIn;
		}
		
		public static TriggerInstance brewedOil()
		{
			return new BrewOilTrigger.TriggerInstance(ContextAwarePredicate.ANY, null);
		}
		
		// TODO: Possibly make a advancement for brewing all the oils
		public boolean matches(OilEffect oilEffectIn)
		{
			return oilEffect == null || oilEffect == oilEffectIn;
		}
		
		@Override
		public JsonObject serializeToJson(SerializationContext contextIn) 
		{
			JsonObject json = super.serializeToJson(contextIn);
			if(oilEffect != null && oilEffect != OilEffects.NONE.get())
				json.addProperty(JSON_OIL, RegistryManager.ACTIVE.getRegistry(OilEffects.REGISTRY_KEY).getKey(oilEffect).toString());
			
			return json;
		}
	}
}
