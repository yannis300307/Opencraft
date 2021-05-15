package fr.opencraft.client.render.renderer.world

import fr.opencraft.client.render.Camera
import fr.opencraft.client.render.registry.RenderRegistry
import fr.opencraft.client.render.shader.ChunkShader
import fr.opencraft.client.render.shader.EntityShader
import fr.opencraft.core.world.ChunkPosition
import fr.opencraft.core.world.World
import org.lwjgl.opengl.GL11.*

class WorldRenderer(val world: World) {
	private val chunkRenderers = HashMap<ChunkPosition, ChunkRenderer>()
	private val chunkShader = ChunkShader()
	private val entityShader = EntityShader()

	init {
		glEnable(GL_DEPTH_TEST)
		glEnable(GL_CULL_FACE)
		glCullFace(GL_FRONT)
		glEnable(GL_TEXTURE_2D)
		glClearColor(0f, 0f, 1f, 1f)
	}

	fun dispose() {
		chunkRenderers.forEach { (_, renderer) -> renderer.dispose() }
		chunkRenderers.clear()
		chunkShader.dispose()
		entityShader.dispose()
	}

	fun update() {
		// Removed Chunks
		while (world.removedChunks.isNotEmpty()) {
			val position = world.removedChunks.poll()

			chunkRenderers[position]?.dispose() ?: continue
			chunkRenderers.remove(position)
			//println("Chunk Removed ($position)")
		}

		// Created Chunks
		while (world.createdChunks.isNotEmpty()) {
			val chunk = world.createdChunks.poll()

			val renderer = ChunkRenderer(world, chunk)
			chunkRenderers[chunk.position] = renderer
			//println("Chunk Created (${chunk.position})")
		}

		// Updated
		val position = world.updatedChunks.poll()
		if (position != null) {
			chunkRenderers[position]?.update() ?: return
			//println("Chunk Updated ($position)")
		}
	}

	fun render(camera: Camera) {
		// Render Chunks
		chunkShader.bind(camera)
		chunkRenderers.forEach { (_, renderer) -> renderer.render() }
		chunkShader.unbind()

		// Render Entities
		entityShader.bind(camera)
		world.entities.forEach { (_, entity) -> RenderRegistry.getEntityRenderer(entity.type)?.render(entity) }
		entityShader.unbind()
	}
}