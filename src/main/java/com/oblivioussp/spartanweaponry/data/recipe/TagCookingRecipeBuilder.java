package com.oblivioussp.spartanweaponry.data.recipe;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oblivioussp.spartanweaponry.init.ModRecipeSerializers;
import com.oblivioussp.spartanweaponry.item.crafting.ITagCookingRecipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.registries.ForgeRegistries;

/**
 * Mostly a copy of {@linkplain SimpleCookingRecipeBuilder} changed to accomodate tag results
 * @author ObliviousSpartan
 *
 */
public class TagCookingRecipeBuilder implements RecipeBuilder 
{
	private final RecipeCategory recipeCategory;
	private final CookingBookCategory cookingBookCategory;
	private final ImmutableMap<String, Item> ingredientMap;
	private final TagKey<Item> resultTag;
	private final float experience;
	private final int cookingTime;
	private final ImmutableList.Builder<String> disabledTypesBuilder;
	private final RecipeSerializer<? extends ITagCookingRecipe> serializer;
	private final Advancement.Builder advancementBuilder = Advancement.Builder.recipeAdvancement();
	@Nullable
	private String group;
	
	private TagCookingRecipeBuilder(RecipeCategory recipeCategoryIn, CookingBookCategory cookingBookCategoryIn, ImmutableMap<String, Item> ingredientMapIn, TagKey<Item> resultTagIn, float experienceIn, int cookingTimeIn, RecipeSerializer<? extends ITagCookingRecipe> serializerIn)
	{
		recipeCategory = recipeCategoryIn;
		cookingBookCategory = cookingBookCategoryIn;
		ingredientMap = ingredientMapIn;
		resultTag = resultTagIn;
		experience = experienceIn;
		cookingTime = cookingTimeIn;
		serializer = serializerIn;
		disabledTypesBuilder = new ImmutableList.Builder<>();
	}
	
	public static TagCookingRecipeBuilder smelting(ImmutableMap<String, Item> ingredientMapIn, RecipeCategory recipeCategoryIn, TagKey<Item> resultTagIn, float experienceIn, int cookingTimeIn)
	{
		return new TagCookingRecipeBuilder(recipeCategoryIn, CookingBookCategory.MISC, ingredientMapIn, resultTagIn, experienceIn, cookingTimeIn, ModRecipeSerializers.TAGGED_SMELTING.get());
	}
	
	public static TagCookingRecipeBuilder blasting(ImmutableMap<String, Item> ingredientMapIn, RecipeCategory recipeCategoryIn, TagKey<Item> resultTagIn, float experienceIn, int cookingTimeIn)
	{
		return new TagCookingRecipeBuilder(recipeCategoryIn, CookingBookCategory.MISC, ingredientMapIn, resultTagIn, experienceIn, cookingTimeIn, ModRecipeSerializers.TAGGED_BLASTING.get());
	}

	@Override
	public TagCookingRecipeBuilder unlockedBy(String nameIn, CriterionTriggerInstance criterionIn)
	{
		advancementBuilder.addCriterion(nameIn, criterionIn);
		return this;
	}

	@Override
	public TagCookingRecipeBuilder group(String groupIn)
	{
		group = groupIn;
		return this;
	}
	
	public TagCookingRecipeBuilder addDisabledTypes(String... disableTypes)
	{
		disabledTypesBuilder.add(disableTypes);
		return this;
	}

	@Override
	public Item getResult() 
	{
		return Items.BARRIER;
	}

	@Override
	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation idIn) 
	{
//		validate(idIn);
		advancementBuilder.parent(ROOT_RECIPE_ADVANCEMENT).
		    addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(idIn)).
		    rewards(AdvancementRewards.Builder.recipe(idIn)).
		    requirements(RequirementsStrategy.OR);
		consumer.accept(new Result(idIn, group == null ? "" : group, cookingBookCategory, ingredientMap, resultTag, experience, cookingTime, disabledTypesBuilder.build(), advancementBuilder, idIn.withPrefix("recipes/" + recipeCategory.getFolderName() + "/"), serializer));
	}
	
/*	private void validate(ResourceLocation idIn)
	{
		if(advancementBuilder.getCriteria().isEmpty())
			throw new IllegalStateException("Cannot obtain recipe " + idIn);
	}*/

	static class Result implements FinishedRecipe
	{
		private final ResourceLocation id;
		private final String group;
		private final CookingBookCategory cookingBookCategory;
		private final ImmutableMap<String, Item> ingredientMap;
		private final TagKey<Item> resultTag;
		private final float experience;
		private final int cookingTime;
		private final ImmutableList<String> disabledTypes;
		private final Advancement.Builder advancementBuilder;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<? extends ITagCookingRecipe> serializer;
		
		public Result(ResourceLocation idIn, String groupIn, CookingBookCategory cookingBookCategoryIn, ImmutableMap<String, Item> ingredientMapIn, TagKey<Item> resultTagIn, float experienceIn,
				int cookingTimeIn, ImmutableList<String> disabledTypesIn, Advancement.Builder advancementBuilderIn, ResourceLocation advancementIdIn, RecipeSerializer<? extends ITagCookingRecipe> serializerIn)
		{
			id = idIn;
			group = groupIn;
			cookingBookCategory = cookingBookCategoryIn;
			ingredientMap = ingredientMapIn;
			resultTag = resultTagIn;
			experience = experienceIn;
			cookingTime = cookingTimeIn;
			disabledTypes = disabledTypesIn;
			advancementBuilder = advancementBuilderIn;
			advancementId = advancementIdIn;
			serializer = serializerIn;
		}

		@Override
		public void serializeRecipeData(JsonObject json) 
		{
			if(!group.isEmpty())
				json.addProperty("group", group);
			
			json.addProperty("category", cookingBookCategory.getSerializedName());
//			json.add("ingredient", ingredient.toJson());
			JsonArray ingredientArray = new JsonArray();
			ingredientMap.forEach((disableType, item) -> 
			{
				JsonObject entryObject = new JsonObject();
				entryObject.addProperty("item", ForgeRegistries.ITEMS.getKey(item).toString());
				entryObject.addProperty("disabled_type", disableType);
				ingredientArray.add(entryObject);
			});
			json.add("ingredient", ingredientArray);
			json.addProperty("result_tag", resultTag.location().toString());
			json.addProperty("experience", experience);
			json.addProperty("cookingtime", cookingTime);
			if(!disabledTypes.isEmpty())
			{
				JsonArray disabledTypesArray = new JsonArray();
				for(String type : disabledTypes)
					disabledTypesArray.add(type);
				json.add("disabled_types", disabledTypesArray);
			}
		}

		@Override
		public ResourceLocation getId()
		{
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() 
		{
			return serializer;
		}

		@Override
		public JsonObject serializeAdvancement() 
		{
			return advancementBuilder.serializeToJson();
		}

		@Override
		public ResourceLocation getAdvancementId() 
		{
			return advancementId;
		}
		
	}
}
