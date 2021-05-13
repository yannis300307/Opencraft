package fr.opencraft.client.input

import fr.opencraft.client.GameClient
import fr.opencraft.core.math.Vec2
import org.lwjgl.glfw.GLFW.*

class Input(val client: GameClient) {
	val keys = HashSet<Int>()
	val pressedKeys = HashSet<Int>()
	val releasedKeys = HashSet<Int>()

	val buttons = HashSet<Int>()
	val pressedButtons = HashSet<Int>()
	val releasedButtons = HashSet<Int>()

	var mouse = Vec2(); private set
	var mouseMove = Vec2(); private set

	var mouseGrab: Boolean
		get() = glfwGetInputMode(client.display.window, GLFW_CURSOR) == GLFW_CURSOR_DISABLED
		set(grab) = glfwSetInputMode(client.display.window, GLFW_CURSOR, if (grab) GLFW_CURSOR_DISABLED else GLFW_CURSOR_NORMAL)

	init {
		glfwSetKeyCallback(client.display.window) { _, key, _, action, _ ->
			run {
				if (action == GLFW_PRESS) {
					keys += key
					pressedKeys += key
				} else if (action == GLFW_RELEASE) {
					keys -= key
					releasedKeys += key
				}
			}
		}

		glfwSetMouseButtonCallback(client.display.window) { _, button, action, _ ->
			run {
				if (action == GLFW_PRESS) {
					buttons += button
					pressedButtons += button
				} else if (action == GLFW_RELEASE) {
					buttons -= button
					releasedButtons += button
				}
			}
		}

		glfwSetCursorPosCallback(client.display.window) { _, x, y ->
			run {
				mouse.x = x.toFloat()
				mouse.y = y.toFloat()
			}
		}
	}

	fun update() {
		pressedKeys.clear()
		releasedKeys.clear()

		pressedButtons.clear()
		releasedButtons.clear()

		val lastMouse = mouse.copy()
		glfwPollEvents()
		mouseMove = mouse - lastMouse
	}

	fun isKey(key: Int) : Boolean {
		return keys.contains(key)
	}

	fun isKeyPressed(key: Int) : Boolean {
		return pressedKeys.contains(key)
	}

	fun isKeyReleased(key: Int) : Boolean {
		return releasedKeys.contains(key)
	}

	fun isButton(button: Int) : Boolean {
		return buttons.contains(button)
	}

	fun isButtonPressed(button: Int) : Boolean {
		return pressedButtons.contains(button)
	}

	fun isButtonReleased(button: Int) : Boolean {
		return releasedButtons.contains(button)
	}
}