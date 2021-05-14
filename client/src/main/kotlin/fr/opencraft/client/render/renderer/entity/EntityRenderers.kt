package fr.opencraft.client.render.renderer.entity

import fr.opencraft.client.render.registry.RenderRegistry

object EntityRenderers {
	lateinit var PLAYER_RENDERER: EntityRenderer

	fun init() {
		PLAYER_RENDERER = RenderRegistry.registerEntityRenderer(PlayerEntityRenderer())
	}
}