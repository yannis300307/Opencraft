package fr.opencraft.core.block

import fr.opencraft.core.world.BlockState

abstract class Block(val type: Byte) {
	abstract fun isFullFace(state: BlockState, face: BlockFace): Boolean
}

enum class BlockFace {
	NORTH,
	SOUTH,
	EAST,
	WEST,
	UP,
	DOWN
}
