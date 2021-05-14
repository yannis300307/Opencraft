package fr.opencraft.core.world

import fr.opencraft.core.block.Block
import fr.opencraft.core.block.Blocks
import fr.opencraft.core.math.Vec3

object WorldSettings {
	const val CHUNK_SIZE = 16
	const val CHUNK_BLOCK_COUNT = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE
	val DEFAULT_BLOCK: Block = Blocks.AIR
	const val GRAVITY = 20f
	val FRICTION = Vec3(10f, 1f, 10f)
}