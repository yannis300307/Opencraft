package fr.opencraft.core.entity

import fr.opencraft.core.registry.GameRegistry

object Entities {
	val PLAYER = GameRegistry.registerEntity(PlayerEntity())
}