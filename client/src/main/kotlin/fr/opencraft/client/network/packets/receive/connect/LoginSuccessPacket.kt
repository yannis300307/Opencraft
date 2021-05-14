package fr.opencraft.client.network.packets.receive.connect

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.util.DataInput

class LoginSuccessPacket: ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val playerID = data.readInt()
		val chunkToLoad = data.readInt()

		println("Login Success (ID = $playerID, Chunks = $chunkToLoad)")
		// TODO: Login Success Packet
	}
}