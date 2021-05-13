package fr.opencraft.client.render

import fr.opencraft.client.GameClient
import fr.opencraft.core.math.*

class Camera(private val client: GameClient) {
	var position = Vec3()
	var rotation = Vec3()
	var fov = 130f
	var near = 0.01f
	var far = 1000f

	lateinit var projection: Mat4
	lateinit var view: Mat4

	init {
		calculateProjection()
		calculateView()
	}

	fun calculateProjection() {
		projection = Mat4.perspective(
			fov,
			client.display.width.toFloat() / client.display.height.toFloat(),
			near,
			far
		)
	}

	fun calculateView() {
		view =  Mat4.rotation(rotation.x, Vec3(1f, 0f, 0f)) *
				Mat4.rotation(rotation.y, Vec3(0f, 1f, 0f)) *
				Mat4.rotation(rotation.z, Vec3(0f, 0f, 1f)) *
				Mat4.translation(position * -1f)
	}
}