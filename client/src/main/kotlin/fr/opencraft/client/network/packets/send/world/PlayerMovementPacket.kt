package fr.opencraft.client.network.packets.send.world

import fr.opencraft.client.network.SendPacket
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.util.DataOutput

class PlayerMovementPacket(val position: Vec3, val rotation: Vec3) : SendPacket(2) {
	override fun write(data: DataOutput) {
		data.writeFloat(position.x)
		data.writeFloat(position.y)
		data.writeFloat(position.z)
		data.writeFloat(rotation.x)
		data.writeFloat(rotation.y)
	}
}