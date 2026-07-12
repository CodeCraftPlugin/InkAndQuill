package me.codecraft.inkquill.server;

import io.netty.buffer.ByteBuf;
import me.codecraft.inkquill.InkAndQuill;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.lang.model.element.TypeElement;

public record ItemRenameServerBoundPayload(ItemStack itemtoRenme, String name) implements CustomPacketPayload {

    public static final Identifier ITEM_RENAME_PAYLOAD_ID = InkAndQuill.id("give_glowing_effect");
    public static final CustomPacketPayload.Type<ItemRenameServerBoundPayload> TYPE = new CustomPacketPayload.Type<>(ITEM_RENAME_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemRenameServerBoundPayload> CODEC = StreamCodec.composite(ItemStack.STREAM_CODEC, ItemRenameServerBoundPayload::itemtoRenme,ByteBufCodecs.STRING_UTF8,ItemRenameServerBoundPayload::name,ItemRenameServerBoundPayload::new);
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
