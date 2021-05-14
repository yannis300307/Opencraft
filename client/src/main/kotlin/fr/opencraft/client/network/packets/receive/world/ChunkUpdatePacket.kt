package fr.opencraft.client.network.packets.receive.world

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.util.DataInput
import fr.opencraft.core.world.ChunkPosition

class ChunkUpdatePacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val position = ChunkPosition(
			data.readInt(),
			data.readInt(),
			data.readInt()
		)
		val blocks = data.readArray()

		println("Chunk Update (Position = $position)")
		client.core.world.createChunk(position).setBlocks(blocks)
	}
}