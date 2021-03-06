package fr.opencraft.core.entity

import fr.opencraft.core.collision.AABB
import fr.opencraft.core.world.EntityState

abstract class Entity(val type: Byte) {
	abstract fun getBounds(state: EntityState): AABB
}