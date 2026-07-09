package me.codecraft.inkquill.gui;

import me.codecraft.inkquill.InkAndQuill;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;


@Environment(EnvType.CLIENT)
public class InkAndQuillItemScreen extends Screen {


    private static final Identifier DONE_BUTTON = InkAndQuill.id("ink_and_quill_button");
    private static final Identifier DONE_BUTTON_HOVER = InkAndQuill.id("ink_and_quill_button_hover");

    private static final Identifier CLOSE_BUTTON = InkAndQuill.id("close_ink_and_quill_button");
    private static final Identifier CLOSE_BUTTON_HOVER = InkAndQuill.id("close_ink_and_quill_button_hover");

    private static final Identifier GUI_IMAGE= InkAndQuill.id("textures/gui/ink_and_quill.png");
    /** The X size of the gui window in pixels. */
    private final int imageWidth = 176;
    /** The Y size of the gui window in pixels. */
    private final int imageHeight = 76;

    private final ItemStack itemToRename;
    private final InteractionHand hand;

    SpriteIconButton doneButton;
    SpriteIconButton closeButton;




    private EditBox renameBox;

    @Override
    public boolean keyPressed(final KeyEvent event) {
        if (this.getFocused() == this.renameBox && event.isConfirmation()) {
            this.onRename();
            return true;
        } else {
            return super.keyPressed(event);
        }
    }



    public InkAndQuillItemScreen(ItemStack itemToRename,  InteractionHand handWithQuill) {
        super(Component.translatable("gui.ink_and_quill"));
        this.itemToRename = itemToRename;
        this.hand = handWithQuill;
    }

    private void onRename() {
        System.out.println("Renamed");
        DataComponentPatch data  = DataComponentPatch.builder().set(DataComponents.CUSTOM_NAME,Component.literal(this.renameBox.getValue())).build();
        itemToRename.applyComponents(data);
        this.minecraft.gui.setScreen(null);
    }

    @Override
    protected void init() {
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        this.renameBox = new EditBox(this.font, left + 31, top + 24, 136, 12, Component.translatable("ink_and_quill.title"));
        this.renameBox.setCanLoseFocus(false);
        this.renameBox.setTextColor(-1);
        this.renameBox.setTextColorUneditable(-1);
        this.renameBox.setBordered(false);
        this.renameBox.setMaxLength(50);
        DataComponentMap itemName = itemToRename.getComponents();
        this.renameBox.setValue(itemName.getOrDefault(DataComponents.CUSTOM_NAME,itemName.getOrDefault(DataComponents.ITEM_NAME,Component.literal("Unknown Item"))).getString());
        WidgetSprites doneWidgetSprites = new WidgetSprites(DONE_BUTTON,DONE_BUTTON_HOVER);
        WidgetSprites closeWidgetSprites = new WidgetSprites(CLOSE_BUTTON,CLOSE_BUTTON_HOVER);
        doneButton = SpriteIconButton.builder(Component.literal("Rename"),button -> {this.onRename();},true)
                .sprite(doneWidgetSprites,22,22).size(22,22).build();
        doneButton.setPosition(left+7, top+47);
        closeButton = SpriteIconButton.builder(Component.literal("Rename"),button -> {this.minecraft.gui.setScreen(null);},true)
                .sprite(closeWidgetSprites,22,22).size(22,22).build();
        closeButton.setPosition(left+35, top+47);
        this.addRenderableWidget(doneButton);
        this.addRenderableWidget(closeButton);
        this.addRenderableWidget(renameBox);


    }


    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;

        Identifier itemToRenameId = itemToRename.getItem().builtInRegistryHolder().key().identifier().withSuffix(".png").withPrefix("textures/item/");
//        itemToRenameId
//        System.out.println(GUI_IMAGE);
        graphics.blit(RenderPipelines.GUI_TEXTURED,GUI_IMAGE,left,top,0,0,176,76, 256,256);
        graphics.blit(RenderPipelines.GUI_TEXTURED,itemToRenameId,left+8,top+20,0,0,16,16,16,16);
        graphics.text(minecraft.font, Component.literal("Ink and Quill Rename"),left+5, top+5, ARGB.opaque(0x000000), false);
        this.doneButton.extractRenderState(graphics,mouseX,mouseY,a);
        this.closeButton.extractRenderState(graphics,mouseX,mouseY,a);
        this.renameBox.extractRenderState(graphics,mouseX,mouseY,a);

    }
}

