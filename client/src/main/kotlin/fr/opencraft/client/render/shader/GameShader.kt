package fr.opencraft.client.render.shader

import fr.opencraft.client.render.Camera
import fr.opencraft.core.math.Mat4

abstract class GameShader(name: String) : Shader(name) {
	private val projectionMatrixLocation = getUniformLocation("projectionMatrix")
	private val viewMatrixLocation = getUniformLocation("viewMatrix")

	fun setProjection(mat: Mat4) {
		loadMat4(projectionMatrixLocation, mat)
	}

	fun setView(mat: Mat4) {
		loadMat4(viewMatrixLocation, mat)
	}

	fun bind(camera: Camera) {
		bind()
		setProjection(camera.projection)
		setView(camera.view)
	}
}