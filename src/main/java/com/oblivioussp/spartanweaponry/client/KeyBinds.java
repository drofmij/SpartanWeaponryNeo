package com.oblivioussp.spartanweaponry.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants.Type;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.client.event.RegisterKeyMappingsEvent;
import net.neoforged.client.settings.KeyConflictContext;
import net.neoforged.eventbus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class KeyBinds
{
	public static KeyMapping KEY_ACCESS_QUIVER = new KeyMapping("key." + ModSpartanWeaponry.ID + ".access_quiver", KeyConflictContext.IN_GAME, Type.KEYSYM, GLFW.GLFW_KEY_I, "key." + ModSpartanWeaponry.ID + ".category.title");

	@SubscribeEvent
	public static void registerKeyBinds(RegisterKeyMappingsEvent ev)
	{
		ev.register(KeyBinds.KEY_ACCESS_QUIVER);
	}
}
