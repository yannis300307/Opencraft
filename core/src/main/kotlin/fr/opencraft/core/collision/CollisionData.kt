package fr.opencraft.core.collision

import fr.opencraft.core.math.*

data class CollisionData(
	val collider: AABB,
	val other: AABB,
	val position: Vec3,
	val normal: Vec3,
	val delta: Vec3
)
