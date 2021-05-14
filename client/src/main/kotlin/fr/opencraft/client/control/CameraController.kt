package fr.opencraft.client.control

import fr.opencraft.client.GameClient
import fr.opencraft.client.GameState
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.WorldSettings.CHUNK_SIZE

class CameraController(val client: GameClient) {
	private val camera get() = client.camera

	fun update(delta: Float) {
		if (client.state == GameState.LOADING_TERRAIN) {
			camera.position = Vec3(0f, 100f, 0f)
			camera.rotation = Vec3(90f, 0f, 0f)
		}

		if (client.state == GameState.GAME) {
			camera.position = client.player?.head ?: Vec3()
			camera.rotation = client.player?.rotation ?: Vec3()
		}
	}
}
