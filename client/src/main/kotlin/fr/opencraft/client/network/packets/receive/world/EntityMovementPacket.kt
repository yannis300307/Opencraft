package fr.opencraft.client.network.packets.receive.world

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.util.DataInput

class EntityMovementPacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val identifier = data.readInt()
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

		println("Entity Movement (Identifier = $identifier, Position = $position, Rotation = $rotation)")
		client.core.world.getEntity(identifier)?.position = position
		client.core.world.getEntity(identifier)?.rotation = rotation
	}
}