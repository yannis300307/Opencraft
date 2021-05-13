package fr.opencraft.core.block

import fr.opencraft.core.collision.AABB
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.BlockState

class FullBlock(type: Byte) : CollideBlock(type) {
	override fun getBounds(state: BlockState): AABB {
		return AABB(state.position.toWorld(state.chunk.position), Vec3(1))
	}
}