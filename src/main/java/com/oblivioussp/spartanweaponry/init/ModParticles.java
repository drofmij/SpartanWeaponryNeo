package com.oblivioussp.spartanweaponry.init;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.client.particle.DamageModifiedParticle;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class ModParticles 
{
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.PARTICLE_TYPES, ModSpartanWeaponry.ID);
	
	public static final RegistryObject<SimpleParticleType> DAMAGE_BOOSTED = REGISTRY.register("damage_boosted", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> DAMAGE_REDUCED = REGISTRY.register("damage_reduced", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> OIL_DAMAGE_BOOSTED = REGISTRY.register("oil_damage_boosted", () -> new SimpleParticleType(false));
	// TODO: Also consider adding boomerang trail particle and possibly for other throwing weapons too
	
	@SubscribeEvent
	public static void registerFactories(RegisterParticleProvidersEvent ev)
	{
		ev.registerSpriteSet(DAMAGE_BOOSTED.get(), DamageModifiedParticle.DamageBoostedProvider::new);
		ev.registerSpriteSet(DAMAGE_REDUCED.get(), DamageModifiedParticle.DamageReducedProvider::new);
		ev.registerSpriteSet(OIL_DAMAGE_BOOSTED.get(), DamageModifiedParticle.OilDamageBoostedProvider::new);
	}
}
