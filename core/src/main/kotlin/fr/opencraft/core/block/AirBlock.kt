package fr.opencraft.core.block

import fr.opencraft.core.world.BlockState

class AirBlock : Block(0) {
	override fun isFullFace(state: BlockState, face: BlockFace): Boolean {
		return false
	}
}