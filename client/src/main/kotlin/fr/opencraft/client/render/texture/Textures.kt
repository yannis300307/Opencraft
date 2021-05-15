package fr.opencraft.client.render.texture

object Textures {
	lateinit var BLOCKS: SpriteSheet

	fun init() {
		BLOCKS = SpriteSheet("blocks", 8, 8)
	}
}