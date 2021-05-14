package fr.opencraft.client.render.renderer.entity

import fr.opencraft.core.entity.Entity
import fr.opencraft.core.world.EntityState

abstract class EntityRenderer(val type: Entity) {
	abstract fun render(state: EntityState)
}
