package com.oblivioussp.spartanweaponry.init;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.crafting.condition.TypeDisabledCondition;
import com.oblivioussp.spartanweaponry.item.crafting.ApplyOilRecipe;
import com.oblivioussp.spartanweaponry.item.crafting.QuiverUpgradeRecipe;
import com.oblivioussp.spartanweaponry.item.crafting.TagBlastingRecipe;
import com.oblivioussp.spartanweaponry.item.crafting.TagCookingRecipeSerializer;
import com.oblivioussp.spartanweaponry.item.crafting.TagSmeltingRecipe;
import com.oblivioussp.spartanweaponry.item.crafting.TippedProjectileBaseRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.common.crafting.CraftingHelper;
import net.neoforged.registries.DeferredRegister;
import net.neoforged.registries.ForgeRegistries;
import net.neoforged.registries.RegistryObject;

public class ModRecipeSerializers
{
	public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.RECIPE_SERIALIZERS, ModSpartanWeaponry.ID);
	
	public static final RegistryObject<TippedProjectileBaseRecipe.Serializer> TIPPED_PROJECTILE_BASE = REGISTRY.register("tipped_projectile", () -> new TippedProjectileBaseRecipe.Serializer());
	public static final RegistryObject<QuiverUpgradeRecipe.Serializer> QUIVER_UPGRADE_SMITHING = REGISTRY.register("quiver_upgrade_smithing", () -> new QuiverUpgradeRecipe.Serializer());
	public static final RegistryObject<SimpleCraftingRecipeSerializer<ApplyOilRecipe>> APPLY_OIL = REGISTRY.register("apply_oil", () -> new SimpleCraftingRecipeSerializer<>(ApplyOilRecipe::new));
	
	public static final RegistryObject<RecipeSerializer<TagSmeltingRecipe>> TAGGED_SMELTING = REGISTRY.register("tag_smelting", () -> new TagCookingRecipeSerializer<>(TagSmeltingRecipe::new, 200));
	public static final RegistryObject<RecipeSerializer<TagBlastingRecipe>> TAGGED_BLASTING = REGISTRY.register("tag_blasting", () -> new TagCookingRecipeSerializer<>(TagBlastingRecipe::new, 100));
	
	public static void registerRecipeConditions()
	{
		CraftingHelper.register(TypeDisabledCondition.Serializer.INSTANCE);
	}
}
