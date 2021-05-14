package fr.opencraft.core

import fr.opencraft.core.block.Blocks
import fr.opencraft.core.entity.Entities
import fr.opencraft.core.world.World

class GameCore {
	val world = World()

	init {
		// Define Blocks and Entities
		Blocks.toString()
		Entities.toString()
	}
}