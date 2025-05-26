package com.oblivioussp.spartanweaponry.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.client.gui.overlay.ForgeGui;

public interface ICrosshairOverlay 
{
	void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight, ItemStack stack);
}
