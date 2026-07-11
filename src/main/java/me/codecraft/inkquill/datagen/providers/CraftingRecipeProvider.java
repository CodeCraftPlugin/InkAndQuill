package me.codecraft.inkquill.datagen.providers;

import me.codecraft.inkquill.InkAndQuill;
import me.codecraft.inkquill.items.InkAndQuillItemsRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.CustomCraftingRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CraftingRecipeProvider extends FabricRecipeProvider {

    public CraftingRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {

        HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
        return new RecipeProvider(registries,output) {
            @Override
            public void buildRecipes() {


//                shapeless(RecipeCategory.BREWING, Items.DYE.black(),4)
//                        .requires(Items.CHARCOAL)
//                        .unlockedBy(getHasName(Items.CHARCOAL),has( Items.CHARCOAL) )
//                        .save(output);

                DataComponentPatch data  = DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS,new PotionContents(Potions.WATER)).build();
                Ingredient potion =  Ingredient.of(Items.POTION);
                Ingredient potionIng = DefaultCustomIngredients.components(potion,data);
//                CustomCraftingRecipeBuilder.customCrafting(RecipeCategory.BREWING,(commonInfo, craftingBookInfo) ->
//                        new PotionRecipe(commonInfo,craftingBookInfo,
//                                Ingredient.of(Items.DYE.black())
//                                ,new ItemStackTemplate(Items.POTION,1)
//                                ,Ingredient.of(InkQuillItems.BOTTLE_OF_INK)))
//                        .unlockedBy("has_black_dye",has(Items.DYE.black()))
//                        .save(output, InkQuill.id("bottle_of_ink").toString());

                /// Bottle of Ink
                CustomCraftingRecipeBuilder.customCrafting(RecipeCategory.BREWING,(commonInfo, craftingBookInfo) ->
                                new ShapelessRecipe(commonInfo,craftingBookInfo
                                        ,new ItemStackTemplate(InkAndQuillItemsRegistry.BOTTLE_OF_INK,1),
                                        List.of(potionIng,Ingredient.of(Items.DYE.black()))))
                        .unlockedBy("has_black_dye",has(Items.DYE.black()))
                        .save(output, InkAndQuill.id("bottle_of_ink").toString());

                CustomCraftingRecipeBuilder.customCrafting(RecipeCategory.BREWING,(commonInfo, craftingBookInfo) ->
                                new ShapelessRecipe(commonInfo,craftingBookInfo
                                        ,new ItemStackTemplate(InkAndQuillItemsRegistry.BOTTLE_OF_INK,1),
                                        List.of(potionIng,Ingredient.of(Items.CHARCOAL))))
                        .unlockedBy("has_charcoal",has(Items.CHARCOAL))
                        .save(output, InkAndQuill.id("ink_and_quill_alt").toString());

                shapeless(RecipeCategory.BREWING,InkAndQuillItemsRegistry.BOTTLE_OF_INK).requires(Items.GLASS_BOTTLE).requires(Items.INK_SAC)
                        .unlockedBy(getHasName(Items.GLASS_BOTTLE),has(Items.GLASS_BOTTLE)).unlockedBy(getHasName(Items.INK_SAC),has(Items.INK_SAC)).save(output,"bottle_of_ink_alt_2");
                /// Ink and Quill
                shapeless(RecipeCategory.MISC, InkAndQuillItemsRegistry.INK_AND_QUILL)
                        .requires(Items.FEATHER)
                        .requires(InkAndQuillItemsRegistry.BOTTLE_OF_INK)
                        .unlockedBy(getHasName(Items.FEATHER),has(Items.FEATHER))
                        .unlockedBy(getHasName(InkAndQuillItemsRegistry.BOTTLE_OF_INK),has(InkAndQuillItemsRegistry.BOTTLE_OF_INK))
                        .save(output);

                shaped(RecipeCategory.TRANSPORTATION,Items.MAP)
                        .pattern("XXX")
                        .pattern("XPX")
                        .pattern("XXX").define('X',Items.PAPER).define('P', InkAndQuillItemsRegistry.INK_AND_QUILL)
                        .group("maps")
                        .unlockedBy("has_paper",has(Items.PAPER))
                        .unlockedBy("has_ink_and_quill",has(InkAndQuillItemsRegistry.INK_AND_QUILL))
                        .save(output);


            }
        };
    }

    @Override
    public String getName() {
        return "Recipes Provider";
    }
}
