package com.oblivioussp.spartanweaponry.init;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.effect.BasicMobEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.registries.DeferredRegister;
import net.neoforged.registries.ForgeRegistries;
import net.neoforged.registries.RegistryObject;

public class ModMobEffects 
{
	public static DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ModSpartanWeaponry.ID);
	public static RegistryObject<MobEffect> ENDER_DISRPUTION = REGISTRY.register("ender_disruption", () -> new BasicMobEffect(MobEffectCategory.HARMFUL, 0x408080));
}
