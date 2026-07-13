package me.codecraft.inkquill.server;

import me.codecraft.inkquill.InkAndQuill;
import me.codecraft.inkquill.gui.ScreenInterfaceManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RenameScreenOpenerServer implements ScreenInterfaceManager {
    @Override
    public void open(ItemStack stack, Player player) {
        InkAndQuill.LOGGER.info("Server cannot open a Screen, You are calling the wrong method");
    }
}
