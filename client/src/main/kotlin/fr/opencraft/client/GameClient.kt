package fr.opencraft.client

import fr.opencraft.client.input.Input
import fr.opencraft.client.input.Keys
import fr.opencraft.client.network.Network
import fr.opencraft.client.network.packets.send.connect.ConnectionPacket
import fr.opencraft.client.network.packets.send.connect.DisconnectPacket
import fr.opencraft.client.render.Camera
import fr.opencraft.client.render.Display
import fr.opencraft.client.render.renderer.world.WorldRenderer
import fr.opencraft.core.GameCore
import java.net.InetAddress
import kotlin.system.exitProcess

class GameClient {
	lateinit var core: GameCore

	lateinit var display: Display
	lateinit var input: Input
	lateinit var camera: Camera
	lateinit var network: Network

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
		core = GameCore()

		display = Display("Opencraft", 800, 600)
		input = Input(this)
		camera = Camera(this)
		network = Network(this)

		worldRenderer = WorldRenderer(core.world)
	}

	private fun dispose() {
		network.disconnect()

		worldRenderer.dispose()

		display.dispose()
	}

	private fun update(delta: Float) {
		display.update()
		input.update()

		// TEMP Connection System
		if (input.isKeyPressed(Keys.KEY_C)) {
			core.world.removeAllEntities()
			core.world.removeAllChunks()
			network.connect(InetAddress.getByName("86.206.92.249"), 9322)
			network.sendTcp(ConnectionPacket("Michel", 3, network.udpPort))
		}
		if (input.isKeyPressed(Keys.KEY_V)) {
			network.disconnect()
			network.sendTcp(DisconnectPacket())
			core.world.removeAllEntities()
			core.world.removeAllChunks()
		}

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