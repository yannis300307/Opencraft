package fr.opencraft.client.render.renderer.world

import fr.opencraft.client.render.Camera
import fr.opencraft.client.render.shader.WorldShader
import fr.opencraft.core.world.ChunkPosition
import fr.opencraft.core.world.World
import org.lwjgl.opengl.GL11.*

class WorldRenderer(val world: World) {
	private val chunkRenderers = HashMap<ChunkPosition, ChunkRenderer>()
	private val shader = WorldShader()

	init {
		glEnable(GL_DEPTH_TEST)
		glEnable(GL_CULL_FACE)
		glCullFace(GL_FRONT)
	}

	fun dispose() {
		chunkRenderers.forEach { (_, renderer) -> renderer.dispose() }
		chunkRenderers.clear()
		shader.dispose()
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
		shader.bind(camera)
		chunkRenderers.forEach { (_, renderer) -> renderer.render() }
		shader.unbind()
	}
}