package com.dreamtea;

import com.dreamtea.data.LiquidRecipeListener;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;

import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LavaRecipes implements ModInitializer {
	public static final String MOD_ID = "lava-recipes";


	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier LIQUID_RECIPE_RELOAD_ID =  id("liquid_recipe");


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(
				LIQUID_RECIPE_RELOAD_ID,
				new LiquidRecipeListener()
		);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
