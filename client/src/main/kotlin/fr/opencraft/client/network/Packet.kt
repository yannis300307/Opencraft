package fr.opencraft.client.network

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.packets.receive.chat.ReceiveChatPacket
import fr.opencraft.client.network.packets.receive.connect.JoinWorldPacket
import fr.opencraft.client.network.packets.receive.connect.KickPacket
import fr.opencraft.client.network.packets.receive.connect.LoginSuccessPacket
import fr.opencraft.client.network.packets.receive.world.*
import fr.opencraft.core.util.*
import java.lang.IllegalArgumentException

abstract class ReceivePacket {
	abstract fun process(client: GameClient, data: DataInput)

	companion object {
		const val PACKET_SIZE = 8192

		fun getPacket(identifier: Byte): ReceivePacket = when (identifier) {
			0.toByte() -> LoginSuccessPacket()
			1.toByte() -> JoinWorldPacket()
			2.toByte() -> KickPacket()
			3.toByte() -> ReceiveChatPacket()
			4.toByte() -> ChunkUpdatePacket()
			5.toByte() -> BlockUpdatePacket()
			6.toByte() -> SpawnEntityPacket()
			7.toByte() -> RemoveEntityPacket()
			8.toByte() -> EntityMovementPacket()
			else -> throw IllegalArgumentException("Unknown Packet ($identifier)")
		}
	}
}

abstract class SendPacket(val writeID: Byte) {
	abstract fun write(data: DataOutput)
}