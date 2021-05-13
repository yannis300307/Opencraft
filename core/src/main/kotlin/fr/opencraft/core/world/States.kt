package fr.opencraft.core.world

import fr.opencraft.core.block.Block
import fr.opencraft.core.entity.Entity
import fr.opencraft.core.math.Vec3

class BlockState(internal var block: Block, val chunk: Chunk, val position: LocalPosition) {
	var type: Block
		get() = block
		set(value) = run {
			block = value
			chunk.world.updateChunk(chunk.position)
		}
}

class EntityState(val entity: Entity, val world: World, val identifier: Int, val position: Vec3 = Vec3(), val rotation: Vec3 = Vec3())