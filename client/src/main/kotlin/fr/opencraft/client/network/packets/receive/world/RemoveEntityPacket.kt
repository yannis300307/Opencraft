package fr.opencraft.client.network.packets.receive.world

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.util.DataInput

class RemoveEntityPacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val identifier = data.readInt()

		println("Remove Entity (Identifier = $identifier)")
		client.core.world.removeEntity(identifier)
	}
}