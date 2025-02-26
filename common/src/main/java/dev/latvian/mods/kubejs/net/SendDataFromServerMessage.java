package dev.latvian.mods.kubejs.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.latvian.mods.kubejs.KubeJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

public class SendDataFromServerMessage extends BaseS2CMessage {
	private final String channel;
	private final CompoundTag data;

	public SendDataFromServerMessage(String c, @Nullable CompoundTag d) {
		channel = c;
		data = d;
	}

	SendDataFromServerMessage(FriendlyByteBuf buf) {
		channel = buf.readUtf(120);
		data = buf.readNbt();
	}

	@Override
	public MessageType getType() {
		return KubeJSNet.SEND_DATA_FROM_SERVER;
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeUtf(channel, 120);
		buf.writeNbt(data);
	}

	@Override
	public void handle(PacketContext context) {
		if (!channel.isEmpty()) {
			KubeJS.PROXY.handleDataFromServerPacket(channel, data);
		}
	}
}