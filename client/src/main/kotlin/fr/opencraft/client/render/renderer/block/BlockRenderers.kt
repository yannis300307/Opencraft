package fr.opencraft.client.render.renderer.block

import fr.opencraft.client.render.registry.RenderRegistry
import fr.opencraft.client.render.texture.SheetTile
import fr.opencraft.client.render.texture.Textures
import fr.opencraft.core.block.Blocks

object BlockRenderers {
	lateinit var STONE_RENDERER: BlockRenderer
	lateinit var DIRT_RENDERER: BlockRenderer
	lateinit var GRASS_RENDERER: BlockRenderer

	fun init() {
		STONE_RENDERER = RenderRegistry.registerBlockRenderer(FullBlockRenderer(Blocks.STONE, SheetTile(Textures.BLOCKS, 0, 0)))
		DIRT_RENDERER = RenderRegistry.registerBlockRenderer(FullBlockRenderer(Blocks.DIRT, SheetTile(Textures.BLOCKS, 1, 0)))
		GRASS_RENDERER = RenderRegistry.registerBlockRenderer(FullBlockRenderer(Blocks.GRASS, SheetTile(Textures.BLOCKS, 2, 0)))
	}
}