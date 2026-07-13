package me.codecraft.inkquill.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RenameScreenOpenerClient implements ScreenInterfaceManager{
    @Override
    public void open(ItemStack stack, Player player) {
        Minecraft.getInstance().gui.setScreen(
                new InkAndQuillItemScreen(stack, player)
        );
    }
}
