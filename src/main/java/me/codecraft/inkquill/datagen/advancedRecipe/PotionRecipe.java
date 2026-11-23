package me.codecraft.inkquill.datagen.advancedRecipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class PotionRecipe extends NormalCraftingRecipe {

    /// One slot = list of alternatives (OR). JSON accepts a single template or a list.
    private static final Codec<List<ItemStackTemplate>> SLOT_CODEC = Codec.withAlternative(
            ItemStackTemplate.CODEC.listOf(1, Integer.MAX_VALUE),
            ItemStackTemplate.CODEC, List::of);

    private static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStackTemplate>> SLOT_STREAM_CODEC =
            ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list());

    public static final MapCodec<PotionRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                            SLOT_CODEC.listOf().fieldOf("input").forGetter(o -> o.inputs),
                            ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result)
                    )
                    .apply(i, PotionRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PotionRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC, o -> o.commonInfo,
            CraftingRecipe.CraftingBookInfo.STREAM_CODEC, o -> o.bookInfo,
            SLOT_STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.inputs,
            ItemStackTemplate.STREAM_CODEC, o -> o.result,
            PotionRecipe::new
    );

    final List<List<ItemStackTemplate>> inputs;
    final ItemStackTemplate result;
    public static final RecipeSerializer<PotionRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public PotionRecipe(CommonInfo commonInfo, CraftingBookInfo bookInfo,
                        List<List<ItemStackTemplate>> inputs, ItemStackTemplate result) {
        super(commonInfo, bookInfo);
        this.inputs = inputs;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack s : input.items()) if (!s.isEmpty()) stacks.add(s);
        if (stacks.size() != inputs.size()) return false;
        return match(0, stacks, new boolean[stacks.size()]);
    }

    /// backtracking so an "or" slot can't steal a stack another slot needs
    private boolean match(int slot, List<ItemStack> stacks, boolean[] used) {
        if (slot == inputs.size()) return true;
        for (int i = 0; i < stacks.size(); i++) {
            if (used[i]) continue;
            for (ItemStackTemplate t : inputs.get(slot)) {
                if (ItemStack.isSameItemSameComponents(stacks.get(i), t.create())) {
                    used[i] = true;
                    if (match(slot + 1, stacks, used)) return true;
                    used[i] = false;
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return result.create();
    }

    @Override
    public RecipeSerializer<? extends NormalCraftingRecipe> getSerializer() {
        return SERIALIZER;
    }

    private static Ingredient toIngredient(ItemStackTemplate t) {
        return t.components().isEmpty()
                ? Ingredient.of(t.item().value())
                : DefaultCustomIngredients.components(Ingredient.of(t.item().value()), t.components());
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(inputs.stream().map(slot -> {
            Ingredient[] alts = slot.stream().map(PotionRecipe::toIngredient).toArray(Ingredient[]::new);
            return alts.length == 1 ? alts[0] : DefaultCustomIngredients.any(alts);
        }).toList());
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new ShapelessCraftingRecipeDisplay(
                inputs.stream().map(slot -> {
                    List<SlotDisplay> alts = slot.stream()
                            .<SlotDisplay>map(SlotDisplay.ItemStackSlotDisplay::new).toList();
                    return alts.size() == 1 ? alts.get(0) : (SlotDisplay) new SlotDisplay.Composite(alts);
                }).toList(),
                new SlotDisplay.ItemStackSlotDisplay(result),
                new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
    }
}