package fr.opencraft.client.render.renderer.block

import fr.opencraft.core.block.Block
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.BlockState
import java.nio.FloatBuffer

abstract class BlockRenderer(val type: Block) {
	abstract fun render(state: BlockState, buffer: FloatBuffer): Int
}