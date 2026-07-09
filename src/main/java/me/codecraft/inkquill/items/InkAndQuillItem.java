package me.codecraft.inkquill.items;

import me.codecraft.inkquill.datagen.providers.InkAndQuillTagProvider;
import me.codecraft.inkquill.gui.InkAndQuillItemScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class InkAndQuillItem extends Item {

    public InkAndQuillItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack offHandItem  = player.getItemInHand(InteractionHand.OFF_HAND);
        if (level.isClientSide()){
            if (offHandItem.is(InkAndQuillTagProvider.INK_AND_QUILL_RENAMEABLE)){
                Screen currentScreen = Minecraft.getInstance().gui.screen();
                Minecraft.getInstance().gui.setScreen(
                        new InkAndQuillItemScreen(offHandItem, InteractionHand.MAIN_HAND)
                );
            }


        }


        return super.use(level, player, hand);
    }

    @Override
    public @Nullable ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        return damage(stack);
    }



    public static ItemStackTemplate damage(ItemStack inkAndQuillItem) {
        if (inkAndQuillItem.getDamageValue() < inkAndQuillItem.getMaxDamage() - 1) {
            System.out.println("InkandQuillItem");
            inkAndQuillItem.setDamageValue(inkAndQuillItem.getDamageValue() + 1);
            return new ItemStackTemplate(inkAndQuillItem.getItem(),inkAndQuillItem.getComponentsPatch());
        }
        return new ItemStackTemplate(Items.GLASS_BOTTLE);
    }

//    public static boolean handsCheck(Player player, InteractionHand handWithQuill) {
//        return player.getItemInHand(handWithQuill).is(InkAndQuillItemsRegistry.INK_AND_QUILL.builtInRegistryHolder())
//                && player.getItemInHand(InteractionHand.OFF_HAND).is(InkAndQuillTagProvider.INK_AND_QUILL_RENAMEABLE);
//    }


}
