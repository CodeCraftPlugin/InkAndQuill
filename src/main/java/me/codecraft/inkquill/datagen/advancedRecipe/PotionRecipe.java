package me.codecraft.inkquill.datagen.advancedRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class PotionRecipe extends NormalCraftingRecipe {


    public static final MapCodec<PotionRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                            Ingredient.CODEC.fieldOf("item").forGetter(o -> o.item),
                            ItemStackTemplate.CODEC.fieldOf("potion").forGetter(o -> o.potion),
                            Ingredient.CODEC.fieldOf("result").forGetter(o -> o.result)
                            )
                    .apply(i, PotionRecipe::new)
    );


    public static final StreamCodec<RegistryFriendlyByteBuf, PotionRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC,
            o -> o.commonInfo,
            CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
            o -> o.bookInfo,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.item,
            ItemStackTemplate.STREAM_CODEC,
            o -> o.potion,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.result,
            PotionRecipe::new
    );


    ItemStackTemplate potion;
    Ingredient item;
    Ingredient result;
    public static final RecipeSerializer<PotionRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public PotionRecipe(CommonInfo commonInfo, CraftingBookInfo bookInfo, Ingredient item, ItemStackTemplate potion,Ingredient result) {
        super(commonInfo, bookInfo);
        this.result = result;
        this.item = item;
        this.potion = potion;
    }


    @Override
    public boolean matches(CraftingInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return null;
    }

    @Override
    public RecipeSerializer<? extends NormalCraftingRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return null;
    }
}
