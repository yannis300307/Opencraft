package fr.opencraft.client.render.renderer.world

import fr.opencraft.client.render.Buffers
import fr.opencraft.client.render.registry.RenderRegistry
import fr.opencraft.core.block.Blocks
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.*
import fr.opencraft.core.world.WorldSettings.CHUNK_BLOCK_COUNT
import fr.opencraft.core.world.WorldSettings.CHUNK_SIZE
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*

class ChunkRenderer(val world: World, val chunk: Chunk) {
	private val vao = Buffers.createVertexArray()
	private val vbo = Buffers.createVertexBuffer()
	private var vertexCount = 0

	fun dispose() {
		Buffers.deleteVertexArray(vao)
		Buffers.deleteVertexBuffer(vbo)
	}

	fun update() {
		updateFloatBuffer()
		updateVertexBuffer()
	}

	private fun updateFloatBuffer() {
		vertexCount = 0
		buffer.clear()
		for (x in 0 until CHUNK_SIZE) {
			for (y in 0 until CHUNK_SIZE) {
				for (z in 0 until CHUNK_SIZE) {
					val position = LocalPosition(x, y, z)
					val block = chunk.getBlock(position)
					if (block.type == Blocks.AIR) continue

					val size = RenderRegistry.getBlockRenderer(block.type)?.render(block, buffer)
					if (size != null) vertexCount += size
				}
			}
		}
		buffer.flip()
	}

	private fun updateVertexBuffer() {
		glBindVertexArray(vao)
		glEnableVertexAttribArray(0)
		glEnableVertexAttribArray(1)
		glBindBuffer(GL_ARRAY_BUFFER, vbo)
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0L)
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 12L)
		glBindVertexArray(0)
	}

	fun render() {
		glBindVertexArray(vao)
		glDrawArrays(GL_QUADS, 0, vertexCount)
		glBindVertexArray(0)
	}

	companion object {
		private val buffer = BufferUtils.createFloatBuffer(CHUNK_BLOCK_COUNT * 6 * 4 * (3 + 4))
	}
}
