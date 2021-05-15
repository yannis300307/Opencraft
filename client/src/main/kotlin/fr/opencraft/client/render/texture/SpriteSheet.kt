package fr.opencraft.client.render.texture

import fr.opencraft.core.math.Vec2

class SpriteSheet(name: String, val countX: Int, val countY: Int) : Texture(name) {
	fun getX(textureX: Int) = (1f / countX) * textureX
	fun getY(textureY: Int) = (1f / countY) * textureY
}

data class SheetTile(val sheet: SpriteSheet, val tileX: Int, val tileY: Int) {
	val min = Vec2((1f / sheet.countX) * tileX, (1f / sheet.countY) * tileY)
	val max = min + Vec2(1f / sheet.countX, 1f / sheet.countY)
}