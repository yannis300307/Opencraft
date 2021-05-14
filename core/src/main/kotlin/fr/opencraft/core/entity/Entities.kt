package fr.opencraft.core.entity

import fr.opencraft.core.registry.GameRegistry

object Entities {
	lateinit var PLAYER: Entity

	fun init() {
		PLAYER = GameRegistry.registerEntity(PlayerEntity())
	}
}