package fr.opencraft.core.block

import fr.opencraft.core.registry.GameRegistry

object Blocks {
	lateinit var AIR: Block
	lateinit var STONE: Block
	lateinit var DIRT: Block
	lateinit var GRASS: Block

	fun init() {
		AIR = GameRegistry.registerBlock(AirBlock())
		STONE = GameRegistry.registerBlock(FullBlock(1))
		DIRT = GameRegistry.registerBlock(FullBlock(2))
		GRASS = GameRegistry.registerBlock(FullBlock(3))
	}
}