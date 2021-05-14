package fr.opencraft.client.network.packets.receive.chat

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.util.DataInput

class ReceiveChatPacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val message = data.readString()

		println("Chat (Message = $message)")
	}
}