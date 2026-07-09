package me.codecraft.inkquill;

import me.codecraft.inkquill.datagen.providers.CraftingRecipeProvider;
import me.codecraft.inkquill.datagen.providers.InkAndQuillModelProvider;
import me.codecraft.inkquill.datagen.providers.InkAndQuillTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class InkAndQuillDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(CraftingRecipeProvider::new);
		pack.addProvider(InkAndQuillTagProvider::new);
		pack.addProvider(InkAndQuillModelProvider::new);



	}
}
