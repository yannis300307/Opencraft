package fr.opencraft.client.render

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL

class Display(title: String, width: Int, height: Int) {
	val window: Long

	val closed get() = glfwWindowShouldClose(window)
	var resized = false; private set
	var width = 0; private set
	var height = 0; private set

	init {
		// Init glfw
		if (!glfwInit()) throw IllegalStateException("Unable to initialize GLFW")

		// Window parameters
		glfwDefaultWindowHints()
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

		// Create window
		window = glfwCreateWindow(width, height, title, 0, 0)
		if (window == 0L) throw RuntimeException("Unable to create the window")

		// Center screen
		val screen = glfwGetVideoMode(glfwGetPrimaryMonitor())
		if (screen != null) {
			glfwSetWindowPos(window, (screen.width() - width) / 2, (screen.height() - height) / 2)
		}

		// Make context
		glfwMakeContextCurrent(window)
		GL.createCapabilities()

		// Size callback
		glfwSetWindowSizeCallback(window) { _, w, h ->
			run {
				this.resized = true
				this.width = w
				this.height = h
				glViewport(0, 0, w, h)
			}
		}

		// Show window
		glfwShowWindow(window)
	}

	fun dispose() {
		Callbacks.glfwFreeCallbacks(window)
		glfwDestroyWindow(window)
		glfwTerminate()
	}

	fun close() {
		glfwSetWindowShouldClose(window, true)
	}

	fun clear() {
		glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
	}

	fun update() {
		resized = false
	}

	fun swapBuffers() {
		glfwSwapBuffers(window)
	}
}