package fr.opencraft.client.network.packets.send.connect

import fr.opencraft.client.network.SendPacket
import fr.opencraft.core.util.DataOutput

class DisconnectPacket : SendPacket(1) {
	override fun write(data: DataOutput) {}
}