package fr.opencraft.client.network.packets.receive.connect

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.util.DataInput
import kotlin.concurrent.thread

class KickPacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val message = data.readString()

		println("Kicked (Message = $message)")
		thread { client.network.disconnect() }
	}
}