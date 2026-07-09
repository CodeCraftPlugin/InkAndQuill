package me.codecraft.inkquill.datagen.providers;

import me.codecraft.inkquill.items.InkAndQuillItem;
import me.codecraft.inkquill.items.InkAndQuillItemsRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class InkAndQuillModelProvider extends FabricModelProvider {
    public InkAndQuillModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {


        itemModelGenerators.declareCustomModelItem(InkAndQuillItemsRegistry.INK_AND_QUILL);
        itemModelGenerators.declareCustomModelItem(InkAndQuillItemsRegistry.BOTTLE_OF_INK);

    }
}
