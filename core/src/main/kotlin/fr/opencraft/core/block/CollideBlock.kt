package fr.opencraft.core.block

import fr.opencraft.core.collision.AABB
import fr.opencraft.core.world.BlockState

abstract class CollideBlock(type: Byte) : Block(type) {
	abstract fun getBounds(state: BlockState): AABB
}