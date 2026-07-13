package me.codecraft.inkquill;

import me.codecraft.inkquill.gui.RenameScreenOpenerClient;
import net.fabricmc.api.ClientModInitializer;

public class InkAndQuillClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        InkAndQuill.SCREEN_OPENER  = new RenameScreenOpenerClient();
    }
}
