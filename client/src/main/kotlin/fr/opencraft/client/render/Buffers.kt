package fr.opencraft.client.render

import org.lwjgl.opengl.GL30.*

object Buffers {
	private val vaoList = HashSet<Int>()
	private val vboList = HashSet<Int>()
	private val programs = HashSet<Int>()

	fun createVertexArray() : Int {
		val vao = glGenVertexArrays()
		if (vao == 0) throw RuntimeException("Unable to create a vao")
		vaoList.add(vao)
		return vao
	}

	fun deleteVertexArray(id: Int) {
		vaoList.remove(id)
		glDeleteVertexArrays(id)
	}

	fun createVertexBuffer() : Int {
		val vbo = glGenBuffers()
		if (vbo == 0) throw RuntimeException("Unable to create a vbo")
		vboList.add(vbo)
		return vbo
	}

	fun deleteVertexBuffer(id: Int) {
		vboList.remove(id)
		glDeleteBuffers(id)
	}

	fun createProgram() : Int {
		val program = glCreateProgram()
		if (program == 0) throw RuntimeException("Unable to create a program")
		programs.add(program)
		return program
	}

	fun deleteProgram(id: Int) {
		programs.remove(id)
		glDeleteProgram(id)
	}

	fun clean() {
		for (vao in vaoList) {
			glDeleteVertexArrays(vao)
		}
		vaoList.clear()

		for (vbo in vboList) {
			glDeleteBuffers(vbo)
		}
		vboList.clear()

		for (program in programs) {
			glDeleteProgram(program)
		}
		programs.clear()
	}
}