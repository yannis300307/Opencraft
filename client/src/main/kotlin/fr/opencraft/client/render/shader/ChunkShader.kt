package fr.opencraft.client.render.shader

class ChunkShader : GameShader("chunk") {
	override fun bindAttributeLocations() {
		bindAttribLocation(0, "blockPosition")
		bindAttribLocation(1, "blockColor")
	}
}
