package fr.opencraft.client.network.packets.receive.world

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.registry.GameRegistry
import fr.opencraft.core.util.DataInput
import java.lang.IllegalArgumentException

class SpawnEntityPacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val identifier = data.readInt()
		val type = data.read()
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

		println("Spawn Entity (Identifier = $identifier, Type = $type, Position = $position, Rotation = $rotation)")
		val entity = GameRegistry.getEntity(type) ?: throw IllegalArgumentException("Unknown Entity ($type)")
		client.core.world.spawnEntity(identifier, entity)
	}
}