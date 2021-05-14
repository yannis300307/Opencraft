package fr.opencraft.client.network.packets.receive.connect

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.util.DataInput

class KickPacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val message = data.readString()

		println("Kicked (Message = $message)")
		client.network.disconnect()
	}
}