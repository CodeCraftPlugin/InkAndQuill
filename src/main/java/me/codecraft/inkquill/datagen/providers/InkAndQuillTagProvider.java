package me.codecraft.inkquill.datagen.providers;

import me.codecraft.inkquill.InkAndQuill;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class InkAndQuillTagProvider extends FabricTagsProvider.ItemTagsProvider  {

    public static final TagKey<Item> INK_AND_QUILL_RENAMEABLE = TagKey.create(Registries.ITEM, InkAndQuill.id("ink_and_quill_renameable"));

    public InkAndQuillTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(INK_AND_QUILL_RENAMEABLE)
                .add(ItemIds.NAME_TAG)
                .add(ItemIds.TRIDENT)
                .addOptionalTag(ItemTags.AXES)
                .addOptionalTag(ItemTags.PICKAXES)
                .addOptionalTag(ItemTags.SWORDS)
                .addOptionalTag(ItemTags.HOES)
                .addOptionalTag(ItemTags.SHOVELS)
                .addOptionalTag(ItemTags.SPEARS)
                .addOptionalTag(ItemTags.ARMOR_ENCHANTABLE);
    }

}
