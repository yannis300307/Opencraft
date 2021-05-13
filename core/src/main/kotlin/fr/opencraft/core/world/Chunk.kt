package fr.opencraft.core.world

import fr.opencraft.core.registry.GameRegistry
import fr.opencraft.core.world.WorldSettings.CHUNK_BLOCK_COUNT
import fr.opencraft.core.world.WorldSettings.CHUNK_SIZE
import fr.opencraft.core.world.WorldSettings.DEFAULT_BLOCK

class Chunk(val world: World, val position: ChunkPosition) {
	private val blocks = Array(CHUNK_SIZE) { x -> Array(CHUNK_SIZE) { y -> Array(CHUNK_SIZE) { z ->
		BlockState(DEFAULT_BLOCK, this, LocalPosition(x, y, z))
	} } }

	fun getBlock(position: LocalPosition): BlockState = blocks[position.x][position.y][position.z]

	fun setBlocks(blocks: ByteArray) {
		if (blocks.size < CHUNK_BLOCK_COUNT)
			throw IllegalArgumentException("Blocks array is to small: ${blocks.size} (needed $CHUNK_BLOCK_COUNT)")

		for (x in 0 until CHUNK_SIZE) {
			for (y in 0 until CHUNK_SIZE) {
				for (z in 0 until CHUNK_SIZE) {
					val type = blocks[(z * CHUNK_SIZE * CHUNK_SIZE) + (y * CHUNK_SIZE) + x]
					this.blocks[x][y][z].block = GameRegistry.getBlock(type) ?: DEFAULT_BLOCK
				}
			}
		}

		world.updateChunk(position)
	}
}