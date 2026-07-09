package me.codecraft.inkquill.items;

import me.codecraft.inkquill.InkAndQuill;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class InkAndQuillItemsRegistry {


    public static final Item BOTTLE_OF_INK = register("bottle_of_ink",Item::new,new Item.Properties());
    public static final Item INK_AND_QUILL = register("ink_and_quill", InkAndQuillItem::new,new Item.Properties().durability(16).stacksTo(1).fireResistant());

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, InkAndQuill.id(name));
        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));
        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void registerItems() {
        InkAndQuill.LOGGER.info("Registering Items");
    }
}
