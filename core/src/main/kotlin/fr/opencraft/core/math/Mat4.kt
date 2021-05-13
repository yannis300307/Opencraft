package fr.opencraft.core.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

data class Mat4(val data: FloatArray = FloatArray(16) { 0f }) {
	init {
		this[0, 0] = 1f
		this[1, 1] = 1f
		this[2, 2] = 1f
		this[3, 3] = 1f
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Mat4
		if (!data.contentEquals(other.data)) return false

		return true
	}

	override fun hashCode() = data.contentHashCode()

	operator fun get(x: Int, y: Int) = data[y * 4 + x]

	operator fun set(x: Int, y: Int, value: Float) {
		data[y * 4 + x] = value
	}

	operator fun not(): Mat4 = TODO("Invert matrix here")

	operator fun times(other: Mat4): Mat4 {
		val result = Mat4()

		for (x in 0 until 4) {
			for (y in 0 until 4) {
				result[x, y] =  this[x, 0] * other[0, y] +
								this[x, 1] * other[1, y] +
								this[x, 2] * other[2, y] +
								this[x, 3] * other[3, y]
			}
		}

		return result
	}

	fun translate(translation: Vec3): Mat4 {
		return this * translation(translation)
	}

	fun rotate(angle: Float, axis: Vec3): Mat4 {
		return this * rotation(angle, axis)
	}

	fun scale(scalar: Vec3): Mat4 {
		return this * scalar(scalar)
	}

	companion object {
		fun translation(translation: Vec3): Mat4 {
			val result = Mat4()

			result[0, 3] = translation.x
			result[1, 3] = translation.y
			result[2, 3] = translation.z

			return result
		}

		fun rotation(angle: Float, axis: Vec3): Mat4 {
			val result = Mat4()

			val cos = cos(Math.toRadians(angle.toDouble())).toFloat()
			val sin = sin(Math.toRadians(angle.toDouble())).toFloat()
			val c = 1 - cos

			result[0, 0] = cos + axis.x * axis.x * c
			result[0, 1] = axis.x * axis.y * c - axis.z * sin
			result[0, 2] = axis.x * axis.z * c + axis.y * sin
			result[1, 0] = axis.y * axis.x * c + axis.z * sin
			result[1, 1] = cos + axis.y * axis.y * c
			result[1, 2] = axis.y * axis.z * c - axis.x * sin
			result[2, 0] = axis.z * axis.x * c - axis.y * sin
			result[2, 1] = axis.z * axis.y * c + axis.x * sin
			result[2, 2] = cos + axis.z * axis.z * c

			return result
		}

		fun scalar(scalar: Vec3): Mat4 {
			val result = Mat4()

			result[0, 0] = scalar.x
			result[1, 1] = scalar.y
			result[2, 2] = scalar.z

			return result
		}

		fun perspective(fov: Float, aspect: Float, near: Float, far: Float): Mat4 {
			val result = Mat4()

			val tanFOV = tan(Math.toRadians((fov / 2).toDouble())).toFloat()
			val range = far - near

			result[0, 0] = 1.0f / (aspect * tanFOV)
			result[1, 1] = 1.0f / tanFOV
			result[2, 2] = -((far + near) / range)
			result[3, 2] = -1.0f
			result[2, 3] = -(2f * far * near / range)
			result[3, 3] = 0.0f

			return result
		}
	}
}