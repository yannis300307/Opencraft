package fr.opencraft.client.render.registry

import fr.opencraft.client.render.renderer.block.BlockRenderer
import fr.opencraft.client.render.renderer.entity.EntityRenderer
import fr.opencraft.core.block.Block
import fr.opencraft.core.entity.Entity
import java.util.concurrent.ConcurrentHashMap

object RenderRegistry {
	private val blockRenderers = ConcurrentHashMap<Block, BlockRenderer>()
	private val entityRenderers = ConcurrentHashMap<Entity, EntityRenderer>()

	fun getBlockRenderer(block: Block) = blockRenderers[block]

	fun registerBlockRenderer(renderer: BlockRenderer): BlockRenderer {
		if (blockRenderers.containsKey(renderer.type)) throw IllegalArgumentException("Block Renderer (${renderer.type}) is already registered")
		blockRenderers[renderer.type] = renderer
		return renderer
	}

	fun getEntityRenderer(entity: Entity) = entityRenderers[entity]

	fun registerEntityRenderer(renderer: EntityRenderer): EntityRenderer {
		if (entityRenderers.containsKey(renderer.type)) throw IllegalArgumentException("Entity Renderer (${renderer.type}) is already registered")
		entityRenderers[renderer.type] = renderer
		return renderer
	}
}