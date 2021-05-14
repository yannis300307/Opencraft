package fr.opencraft.core.collision

import fr.opencraft.core.block.CollideBlock
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.BlockPosition
import fr.opencraft.core.world.World
import fr.opencraft.core.world.toBlock
import kotlin.math.abs
import kotlin.math.sign

data class AABB(val pos: Vec3, val size: Vec3) {
	val half get() = size / 2f
	val center get() = pos + half

	fun getCollisionData(other: AABB): CollisionData? {
		// Calculate Penetration X
		val dx = other.center.x - center.x
		val px = (other.half.x + half.x) - abs(dx)
		if (px <= 0) return null

		// Calculate Penetration Y
		val dy = other.center.y - center.y
		val py = (other.half.y + half.y) - abs(dy)
		if (py <= 0) return null

		// Calculate Penetration Z
		val dz = other.center.z - center.z
		val pz = (other.half.z + half.z) - abs(dz)
		if (pz <= 0) return null

		// Calculate Collision
		if (px < py && px < pz) {
			val sx = -sign(dx)
			return CollisionData(
				Vec3(sx, 0, 0),
				Vec3(px * sx, 0f, 0f)
			)
		} else if (py < px && py < pz) {
			val sy = -sign(dy)
			return CollisionData(
				Vec3(0f, sy, 0f),
				Vec3(0f, py * sy, 0f)
			)
		} else {
			val sz = -sign(dz)
			return CollisionData(
				Vec3(0f, 0f, sz),
				Vec3(0f, 0f, pz * sz)
			)
		}
	}

	fun getCollisionData(world: World): CollisionData? {
		val min = (pos).toBlock()
		val max = (pos + size).toBlock()

		var delta = Vec3()

		for (x in min.x..max.x) {
			for (y in min.y..max.y) {
				for (z in min.z..max.z) {
					val state = world.getBlock(BlockPosition(x, y, z)) ?: continue
					val block = state.type
					if (block is CollideBlock) {
						val other = block.getBounds(state)
						val collision = getCollisionData(other)
						if (collision != null) {
							delta.x = if (abs(collision.delta.x) >= abs(delta.x)) collision.delta.x else delta.x
							delta.y = if (abs(collision.delta.y) >= abs(delta.y)) collision.delta.y else delta.y
							delta.z = if (abs(collision.delta.z) >= abs(delta.z)) collision.delta.z else delta.z
						}
					}
				}
			}
		}

		if (delta.length == 0f) return null
		return CollisionData(Vec3(), delta)
	}

	companion object {
		fun getEntityBounds(feet: Vec3, size: Vec3): AABB {
			return AABB(feet - Vec3(size.x / 2f, 0f, size.z / 2f), size)
		}
	}
}
