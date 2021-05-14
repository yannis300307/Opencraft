package fr.opencraft.core.entity

import fr.opencraft.core.collision.AABB
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.EntityState

class PlayerEntity : Entity(0) {
	override fun getBounds(state: EntityState): AABB {
		return AABB.getEntityBounds(state.position, Vec3(0.6f, 1.8f, 0.6f))
	}

	override fun getHead(state: EntityState): Vec3 {
		return state.position + Vec3(0f, 1.5f, 0f)
	}
}