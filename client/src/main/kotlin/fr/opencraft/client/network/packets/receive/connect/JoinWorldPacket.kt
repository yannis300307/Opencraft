package fr.opencraft.client.network.packets.receive.connect

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.util.DataInput

class JoinWorldPacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val position = Vec3(
			data.readFloat(),
			data.readFloat(),
			data.readFloat()
		)
		val rotation = Vec3(
			data.readFloat(),
			data.readFloat(),
			0f
		)

		println("Join World (Position = $position, Rotation = $rotation)")
		// TODO: Join World Packet
	}
}