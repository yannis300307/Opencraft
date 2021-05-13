package fr.opencraft.core.world

import fr.opencraft.core.entity.Entity
import fr.opencraft.core.math.Vec3
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ConcurrentSkipListSet

class World {
	private val chunks = ConcurrentHashMap<ChunkPosition, Chunk>()
	val createdChunks = ConcurrentLinkedQueue<Chunk>()
	val removedChunks = ConcurrentLinkedQueue<ChunkPosition>()
	val updatedChunks = ConcurrentLinkedQueue<ChunkPosition>()
	private val entities = ConcurrentHashMap<Int, EntityState>()

	fun getChunk(position: ChunkPosition) = chunks[position]

	fun createChunk(position: ChunkPosition): Chunk {
		var chunk = getChunk(position)
		if (chunk == null) {
			chunk = Chunk(this, position)
			createdChunks.add(chunk)
			chunks[position] = chunk
		}
		return chunk
	}

	fun removeChunk(position: ChunkPosition) {
		if (chunks.remove(position) != null) removedChunks.add(position)
	}

	fun updateChunk(position: ChunkPosition) {
		if (updatedChunks.contains(position)) return
		updatedChunks.add(position)
	}

	fun getBlock(position: BlockPosition): BlockState? {
		val chunk = getChunk(position.toChunk()) ?: return null
		return chunk.getBlock(position.toLocal())
	}

	fun getEntity(identifier: Int) = entities[identifier]

	fun spawnEntity(identifier: Int, entity: Entity, position: Vec3 = Vec3(), rotation: Vec3 = Vec3()): EntityState {
		if (entities.containsKey(identifier)) throw IllegalArgumentException("Identifier ($identifier) was already used")
		val state = EntityState(entity, this, identifier, position, rotation)
		entities[identifier] = state
		return state
	}

	fun removeEntity(identifier: Int) {
		entities.remove(identifier)
	}
}