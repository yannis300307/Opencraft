package fr.opencraft.client.render.registry

import fr.opencraft.client.render.renderer.entity.EntityRenderer
import fr.opencraft.core.entity.Entity
import java.util.concurrent.ConcurrentHashMap

object RenderRegistry {
	private val entityRenderers = ConcurrentHashMap<Entity, EntityRenderer>()

	fun getEntityRenderer(entity: Entity) = entityRenderers[entity]

	fun registerEntityRenderer(renderer: EntityRenderer): EntityRenderer {
		if (entityRenderers.containsKey(renderer.type)) throw IllegalArgumentException("Entity Renderer (${renderer.type}) is already registered")
		entityRenderers[renderer.type] = renderer
		return renderer
	}
}