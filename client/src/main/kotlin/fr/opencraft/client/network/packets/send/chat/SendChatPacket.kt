package fr.opencraft.client.network.packets.send.chat

import fr.opencraft.client.network.SendPacket
import fr.opencraft.core.util.DataOutput

class SendChatPacket(val message: String) : SendPacket(3) {
	override fun write(data: DataOutput) {
		data.writeString(message)
	}
}