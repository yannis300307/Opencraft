package fr.opencraft.client

import fr.opencraft.client.input.Input
import fr.opencraft.client.render.Camera
import fr.opencraft.client.render.Display
import fr.opencraft.client.render.renderer.world.WorldRenderer
import fr.opencraft.core.GameCore
import kotlin.system.exitProcess

class GameClient {
	lateinit var display: Display
	lateinit var input: Input
	lateinit var camera: Camera

	lateinit var core: GameCore

	lateinit var worldRenderer: WorldRenderer

	var fps = 0f; private set
	var frameTime = 1f / 300f

	fun start() {
		init()

		var current = System.nanoTime() / 1000000000.0
		var elapsed: Double
		var last = current
		var timer = 0.0

		while (!display.closed) {
			current = System.nanoTime() / 1000000000.0
			elapsed = current - last
			last = current
			timer += elapsed

			if (timer >= frameTime) {
				val delta = timer.toFloat()
				timer = 0.0
				fps = 1f / delta
				update(if (frameTime <= 0) delta else frameTime)
				render()
			}
		}

		stop()
	}

	fun stop() {
		dispose()
		exitProcess(0)
	}

	fun init() {
		display = Display("Opencraft", 800, 600)
		input = Input(this)
		camera = Camera(this)

		core = GameCore()

		worldRenderer = WorldRenderer(core.world)
	}

	private fun dispose() {
		worldRenderer.dispose()

		display.dispose()
	}

	private fun update(delta: Float) {
		display.update()
		input.update()

		if (display.resized) camera.calculateProjection()
		camera.calculateView()

		worldRenderer.update()
	}

	private fun render() {
		display.clear()

		worldRenderer.render(camera)

		display.swapBuffers()
	}
}