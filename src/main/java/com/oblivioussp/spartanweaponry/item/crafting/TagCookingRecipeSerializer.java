package com.oblivioussp.spartanweaponry.item.crafting;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.oblivioussp.spartanweaponry.api.crafting.condition.TypeDisabledCondition;
import com.oblivioussp.spartanweaponry.util.Log;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.common.crafting.conditions.ICondition.IContext;
import net.neoforged.registries.ForgeRegistries;

public class TagCookingRecipeSerializer<T extends ITagCookingRecipe> implements RecipeSerializer<T> 
{
	private final RecipeFactory<T> factory;
	private final int defaultCookingTime;
	
	public TagCookingRecipeSerializer(RecipeFactory<T> factoryIn, int defaultCookingTimeIn)
	{
		factory = factoryIn;
		defaultCookingTime = defaultCookingTimeIn;
	}

	@SuppressWarnings("deprecation")
	@Override
	public T fromJson(ResourceLocation idIn, JsonObject jsonIn, IContext context)
	{
		JsonArray disabledTypesArray = GsonHelper.getAsJsonArray(jsonIn, "disabled_types", new JsonArray());
		for(JsonElement disabledType : disabledTypesArray)
		{
			String type = disabledType.getAsString();
			if(TypeDisabledCondition.disabledRecipeTypes.contains(type))
				return null;
		}
		ResourceLocation resultLocation = new ResourceLocation(GsonHelper.getAsString(jsonIn, "result_tag"));
		TagKey<Item> tagResult = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), resultLocation);
		if(context.getTag(tagResult).isEmpty())
		{
			Log.info("Empty result tag:\"" + resultLocation + "\" Skipping...");
			return null;
		}
		String group = GsonHelper.getAsString(jsonIn, "group", "");
		CookingBookCategory category = CookingBookCategory.CODEC.byName(GsonHelper.getAsString(jsonIn, "category", ""), CookingBookCategory.MISC);
		JsonElement ingredientJson = GsonHelper.isArrayNode(jsonIn, "ingredient") ? checkJsonArray(jsonIn, "ingredient") : GsonHelper.getAsJsonObject(jsonIn, "ingredient");
		Ingredient ingredientInput = Ingredient.fromJson(ingredientJson);
		float experience = GsonHelper.getAsFloat(jsonIn, "experience", 0.0f);
		int cookingTime = GsonHelper.getAsInt(jsonIn, "cookingtime", defaultCookingTime);
		return factory.create(idIn, group, category, ingredientInput, Ingredient.of(tagResult), experience, cookingTime);
	}
	
	public JsonElement checkJsonArray(JsonObject jsonIn, String nameIn)
	{
		JsonArray array = GsonHelper.getAsJsonArray(jsonIn, nameIn);
		JsonArray result = new JsonArray();
		
		for(JsonElement element : array)
		{
			JsonObject object = element.getAsJsonObject();
			String disableType = GsonHelper.getAsString(object, "disabled_type");
			if(!TypeDisabledCondition.disabledRecipeTypes.contains(disableType))
			{
				object.remove("disabled_type");
				result.add(object);
			}
		}
		
		return (JsonElement)result;
	}

	@Override
	public @Nullable T fromNetwork(ResourceLocation idIn, FriendlyByteBuf bufferIn) 
	{
		String group = bufferIn.readUtf();
		CookingBookCategory category = bufferIn.readEnum(CookingBookCategory.class);
		Ingredient ingredientInput = Ingredient.fromNetwork(bufferIn);
		Ingredient ingredientOutput = Ingredient.fromNetwork(bufferIn);
		float experience = bufferIn.readFloat();
		int cookingTime = bufferIn.readVarInt();
		return factory.create(idIn, group, category, ingredientInput, ingredientOutput, experience, cookingTime);
	}

	@Override
	public void toNetwork(FriendlyByteBuf bufferIn, T recipeIn)
	{
		bufferIn.writeUtf(recipeIn.getGroup());
		bufferIn.writeEnum(recipeIn.getCategory());
		recipeIn.getInputIngredient().toNetwork(bufferIn);
		recipeIn.getResultIngredient().toNetwork(bufferIn);
		bufferIn.writeFloat(recipeIn.getExperienceDrop());
		bufferIn.writeVarInt(recipeIn.getCookTime());
	}

	@Override
	public T fromJson(ResourceLocation idIn, JsonObject jsonIn) 
	{
		return null;
	}

	@FunctionalInterface
	public interface RecipeFactory<T extends ITagCookingRecipe>
	{
		T create(ResourceLocation idIn, String groupIn, CookingBookCategory categoryIn, Ingredient inputIngredientIn, Ingredient outputIn, float experienceIn, int cookTimeIn);
	}
}
