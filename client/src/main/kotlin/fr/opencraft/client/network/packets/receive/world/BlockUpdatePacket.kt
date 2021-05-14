package fr.opencraft.client.network.packets.receive.world

import fr.opencraft.client.GameClient
import fr.opencraft.client.network.ReceivePacket
import fr.opencraft.core.registry.GameRegistry
import fr.opencraft.core.util.DataInput
import fr.opencraft.core.world.BlockPosition

class BlockUpdatePacket : ReceivePacket() {
	override fun process(client: GameClient, data: DataInput) {
		val position = BlockPosition(
			data.readInt(),
			data.readInt(),
			data.readInt()
		)
		val type = data.read()

		println("Chunk Update (Position = $position)")
		val block = GameRegistry.getBlock(type) ?: throw IllegalArgumentException("Unknown Block ($type)")
		client.core.world.getBlock(position)?.type = block
	}
}