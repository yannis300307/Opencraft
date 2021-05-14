package fr.opencraft.client.network.packets.send.connect

import fr.opencraft.client.network.SendPacket
import fr.opencraft.core.util.DataOutput

class ConnectionPacket(val playerName: String, val viewDistance: Int, val udpPort: Int) : SendPacket(0) {
	override fun write(data: DataOutput) {
		data.writeString(playerName)
		data.writeInt(viewDistance)
		data.writeInt(udpPort)
	}
}