package me.codecraft.inkquill;

import me.codecraft.inkquill.datagen.advancedRecipe.PotionRecipe;
import me.codecraft.inkquill.gui.ScreenInterfaceManager;
import me.codecraft.inkquill.items.InkAndQuillItem;
import me.codecraft.inkquill.items.InkAndQuillItemsRegistry;
import me.codecraft.inkquill.server.ItemRenameServerBoundPayload;
import me.codecraft.inkquill.server.RenameScreenOpenerServer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InkAndQuill implements ModInitializer {
	public static final String MOD_ID = "inkquill";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ScreenInterfaceManager SCREEN_OPENER = new RenameScreenOpenerServer();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

//		Registry.register(BuiltInRegistries.ITEM, ResourceKey.create(Registries.ITEM,id("test")),new Item(new Item.Properties()));

		LOGGER.info("Hello Fabric world!");
		//Server
		PayloadTypeRegistry.serverboundPlay().register(ItemRenameServerBoundPayload.TYPE, ItemRenameServerBoundPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(ItemRenameServerBoundPayload.TYPE,(payload, context) -> {
			System.out.println("Recived the Payload ");

			System.out.println(payload.itemtoRenme());
			System.out.println(payload.name());
			ItemStack itemStack = payload.itemtoRenme();
			String name = payload.name();
			ItemStack main = itemStack.copy();
			main.applyComponents(DataComponentPatch.builder().set(DataComponents.CUSTOM_NAME,Component.literal(name)).build());
			context.player().setItemInHand(InteractionHand.OFF_HAND,main);
			context.player().setItemInHand(InteractionHand.MAIN_HAND, InkAndQuillItem.damage(context.player().getItemInHand(InteractionHand.MAIN_HAND)).create());

		});
		//Item
		InkAndQuillItemsRegistry.registerItems();
		//Serializer
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,id("custom_potion"), PotionRecipe.SERIALIZER);

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
