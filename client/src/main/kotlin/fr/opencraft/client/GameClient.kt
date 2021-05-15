package fr.opencraft.client

import fr.opencraft.client.control.CameraController
import fr.opencraft.client.control.PlayerController
import fr.opencraft.client.input.Input
import fr.opencraft.client.input.Keys
import fr.opencraft.client.network.Network
import fr.opencraft.client.network.packets.send.connect.*
import fr.opencraft.client.render.Buffers
import fr.opencraft.client.render.Camera
import fr.opencraft.client.render.Display
import fr.opencraft.client.render.renderer.block.BlockRenderer
import fr.opencraft.client.render.renderer.block.BlockRenderers
import fr.opencraft.client.render.renderer.entity.EntityRenderers
import fr.opencraft.client.render.renderer.world.WorldRenderer
import fr.opencraft.client.render.texture.Texture
import fr.opencraft.client.render.texture.Textures
import fr.opencraft.core.GameCore
import fr.opencraft.core.world.EntityState
import java.net.InetAddress
import javax.swing.JOptionPane
import kotlin.system.exitProcess

class GameClient {
	lateinit var core: GameCore
	var state = GameState.DISCONNECTED
	var player: EntityState? = null

	lateinit var display: Display
	lateinit var input: Input
	lateinit var network: Network
	lateinit var playerController: PlayerController
	lateinit var camera: Camera
	lateinit var cameraController: CameraController

	lateinit var worldRenderer: WorldRenderer

	var fps = 0f; private set
	var frameTime = 1f / 60f

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

	private fun init() {
		core = GameCore()

		display = Display("Opencraft", 800, 600)
		input = Input(this)
		network = Network(this)
		playerController = PlayerController(this)
		camera = Camera(this)
		cameraController = CameraController(this)

		Textures.init()
		BlockRenderers.init()
		EntityRenderers.init()
		worldRenderer = WorldRenderer(core.world)
	}

	private fun dispose() {
		network.disconnect()

		worldRenderer.dispose()

		Buffers.clean()
		display.dispose()
	}

	private fun update(delta: Float) {
		display.update()
		input.update()

		// TEMP Connection System
		if (input.isKeyPressed(Keys.KEY_C)) {
			core.world.removeAllEntities()
			core.world.removeAllChunks()
			network.connect(InetAddress.getByName("86.206.159.21"), 9322)
			//network.connect(InetAddress.getByName("localhost"), 9322)
			network.sendTcp(ConnectionPacket(JOptionPane.showInputDialog("Quel est ton nom ?"), 3, network.udpPort))
		}
		if (input.isKeyPressed(Keys.KEY_V)) {
			network.disconnect()
			network.sendTcp(DisconnectPacket())
			core.world.removeAllEntities()
			core.world.removeAllChunks()
		}

		if (state == GameState.GAME) {
			playerController.update(delta)
		} else if (input.mouseGrab) input.mouseGrab = false

		cameraController.update(delta)
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