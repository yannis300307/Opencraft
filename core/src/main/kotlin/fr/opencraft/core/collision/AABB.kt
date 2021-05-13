package fr.opencraft.core.collision

import fr.opencraft.core.math.Vec3
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
			val sx = sign(dx)
			return CollisionData(
				this,
				other,
				Vec3(center.x + (half.x * sx), center.y, center.z),
				Vec3(sx, 0, 0),
				Vec3(px * sx, 0f, 0f)
			)
		} else if (py < px && py < pz) {
			val sy = sign(dy)
			return CollisionData(
				this,
				other,
				Vec3(center.x, center.y + (half.y * sy), center.z),
				Vec3(0f, sy, 0f),
				Vec3(0f, py * sy, 0f)
			)
		} else {
			val sz = sign(dz)
			return CollisionData(
				this,
				other,
				Vec3(center.x, center.y, center.z + (half.z * sz)),
				Vec3(0f, 0f, sz),
				Vec3(0f, 0f, pz * sz)
			)
		}
	}

	companion object {
		fun getEntityBounds(feet: Vec3, size: Vec3): AABB {
			return AABB(feet - Vec3(size.x / 2f, 0f, size.z / 2f), size)
		}
	}
}
