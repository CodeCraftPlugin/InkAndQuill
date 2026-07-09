package me.codecraft.inkquill;

import me.codecraft.inkquill.datagen.advancedRecipe.PotionRecipe;
import me.codecraft.inkquill.items.InkAndQuillItemsRegistry;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InkAndQuill implements ModInitializer {
	public static final String MOD_ID = "inkquill";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

//		Registry.register(BuiltInRegistries.ITEM, ResourceKey.create(Registries.ITEM,id("test")),new Item(new Item.Properties()));

		LOGGER.info("Hello Fabric world!");

		InkAndQuillItemsRegistry.registerItems();

		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,id("custom_potion"), PotionRecipe.SERIALIZER);

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
