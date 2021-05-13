package fr.opencraft.core.registry

import fr.opencraft.core.block.Block
import fr.opencraft.core.entity.Entity
import java.util.concurrent.ConcurrentHashMap

object GameRegistry {
	private val blocks = ConcurrentHashMap<Byte, Block>()
	private val entities = ConcurrentHashMap<Byte, Entity>()

	fun getBlock(type: Byte) = blocks[type]

	fun registerBlock(block: Block): Block {
		if (blocks.containsKey(block.type)) throw IllegalArgumentException("Block (${block.type}) is already registered")
		blocks[block.type] = block
		return block
	}

	fun getEntity(type: Byte) = entities[type]

	fun registerEntity(entity: Entity): Entity {
		if (entities.containsKey(entity.type)) throw IllegalArgumentException("Entity (${entity.type}) is already registered")
		entities[entity.type] = entity
		return entity
	}
}