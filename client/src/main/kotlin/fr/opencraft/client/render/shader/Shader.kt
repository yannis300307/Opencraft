package fr.opencraft.client.render.shader

import fr.opencraft.client.render.Buffers
import fr.opencraft.core.math.*
import fr.opencraft.core.util.FileUtil
import org.lwjgl.opengl.GL30.*

abstract class Shader(name: String) {
	private val program = Buffers.createProgram()

	init {
		val vertexPath = "shaders/$name.vert"
		val vertexSource = FileUtil.read(vertexPath) ?: throw RuntimeException("Vertex shader source doesn't exist : $vertexPath")
		createShader(GL_VERTEX_SHADER, vertexSource)

		val fragmentPath = "shaders/$name.frag"
		val fragmentSource = FileUtil.read(fragmentPath) ?: throw RuntimeException("Fragment shader source doesn't exist : $fragmentPath")
		createShader(GL_FRAGMENT_SHADER, fragmentSource)

		this.bindAttributeLocations()

		glLinkProgram(program)
		if (glGetProgrami(program, GL_LINK_STATUS) != 1) throw RuntimeException(glGetProgramInfoLog(program))

		glValidateProgram(program)
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) throw RuntimeException(glGetProgramInfoLog(program))
	}

	private fun createShader(type: Int, source: String) {
		val shader = glCreateShader(type)
		if (shader == 0) throw RuntimeException("Unable to create a shader")

		glShaderSource(shader, source)
		glCompileShader(shader)
		if (glGetShaderi(shader, GL_COMPILE_STATUS) != 1) throw RuntimeException(glGetShaderInfoLog(shader))
		glAttachShader(program, shader)
		glDeleteShader(shader)
	}

	protected fun getUniformLocation(name: String): Int {
		return glGetUniformLocation(program, name)
	}

	abstract fun bindAttributeLocations()

	protected fun bindAttribLocation(location: Int, name: String) {
		glBindAttribLocation(program, location, name)
	}

	fun dispose() {
		Buffers.deleteProgram(program)
	}

	fun bind() {
		glUseProgram(program)
	}

	fun unbind() {
		glUseProgram(0)
	}

	protected fun loadFloat(location: Int, value: Float) {
		glUniform1f(location, value)
	}

	protected fun loadVec2(location: Int, value: Vec2) {
		glUniform2f(location, value.x, value.y)
	}

	protected fun loadVec3(location: Int, value: Vec3) {
		glUniform3f(location, value.x, value.y, value.z)
	}

	protected fun loadVec4(location: Int, value: Vec4) {
		glUniform4f(location, value.x, value.y, value.z, value.w)
	}

	protected fun loadMat4(location: Int, value: Mat4) {
		glUniformMatrix4fv(location, false, value.data)
	}
}