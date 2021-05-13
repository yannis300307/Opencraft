package fr.opencraft.core.block

import fr.opencraft.core.registry.GameRegistry

object Blocks {
	val AIR		= GameRegistry.registerBlock(AirBlock())
	val STONE	= GameRegistry.registerBlock(FullBlock(1))
	val DIRT	= GameRegistry.registerBlock(FullBlock(2))
	val GRASS	= GameRegistry.registerBlock(FullBlock(3))
}