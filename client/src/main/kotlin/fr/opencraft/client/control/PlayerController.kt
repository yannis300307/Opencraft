package fr.opencraft.client.control

import fr.opencraft.client.GameClient
import fr.opencraft.client.input.Keys
import fr.opencraft.client.network.packets.send.world.PlayerMovementPacket
import fr.opencraft.core.math.Euler
import fr.opencraft.core.math.Vec3

class PlayerController(val client: GameClient) {
	val player get() = client.player!!

	var mouseSensibility = 0.05f
	var speed = 40f
	var jumpForce = 8f

	var lastRemotePosition = Vec3()
	var lastRemoteRotation = Vec3()

	fun update(delta: Float) {
		if (client.input.mouseGrab) {
			rotationInput()
			movementInput(delta)
			player.update(delta)
			remoteMovement()
		} else {
			client.input.mouseGrab = true
		}
	}

	private fun rotationInput() {
		player.rotate(Vec3(client.input.mouseMove.y, client.input.mouseMove.x, 0f) * mouseSensibility)
	}

	private fun movementInput(delta: Float) {
		var movement = Vec3()

		if (client.input.isKey(Keys.KEY_Z)) movement += Euler.getForwardIgnoreY(player.rotation)
		if (client.input.isKey(Keys.KEY_S)) movement -= Euler.getForwardIgnoreY(player.rotation)
		if (client.input.isKey(Keys.KEY_D)) movement += Euler.getRightIgnoreY(player.rotation)
		if (client.input.isKey(Keys.KEY_Q)) movement -= Euler.getRightIgnoreY(player.rotation)
		if (player.grounded && client.input.isKey(Keys.KEY_SPACE)) player.applyImpulse(Vec3(0f, jumpForce, 0f))

		if (movement.length != 0f) player.applyForce(movement.normalize * speed, delta)
	}

	private fun remoteMovement() {
		if ((player.position - lastRemotePosition).length >= MIN_MOVEMENT_TO_REMOTE ||
			(player.rotation - lastRemoteRotation).length >= MIN_ROTATION_TO_REMOTE) {
			lastRemotePosition = player.position.copy()
			lastRemoteRotation = player.rotation.copy()
			client.network.sendUdp(PlayerMovementPacket(player.position, player.rotation))
		}
	}

	companion object {
		const val MIN_MOVEMENT_TO_REMOTE = 0.5f
		const val MIN_ROTATION_TO_REMOTE = 10f
	}
}