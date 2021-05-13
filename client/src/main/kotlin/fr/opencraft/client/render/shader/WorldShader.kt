package fr.opencraft.client.render.shader

class WorldShader : GameShader("world") {
	override fun bindAttributeLocations() {
		bindAttribLocation(0, "blockPosition")
		bindAttribLocation(1, "blockColor")
	}
}
