package fr.opencraft.core.math

import kotlin.math.*

data class Vec3(var x: Float, var y: Float, var z: Float) {
	val length get() = sqrt(x*x + y*y + z*z)
	val normalize get() = this / length

	constructor(x: Number, y: Number, z: Number) : this(x.toFloat(), y.toFloat(), z.toFloat())
	constructor(vec: Vec3) : this(vec.x, vec.y, vec.z)
	constructor(value: Number) : this(value, value, value)
	constructor() : this(0f)

	operator fun compareTo(vec: Vec3) = length.compareTo(vec.length)
	operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
	operator fun plus(v: Number) = Vec3(x + v.toFloat(), y + v.toFloat(), z + v.toFloat())
	operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
	operator fun minus(v: Number) = Vec3(x - v.toFloat(), y - v.toFloat(), z - v.toFloat())
	operator fun times(v: Vec3) = Vec3(x * v.x, y * v.y, z * v.z)
	operator fun times(v: Number) = Vec3(x * v.toFloat(), y * v.toFloat(), z * v.toFloat())
	operator fun div(v: Vec3) = Vec3(x / v.x, y / v.y, z / v.z)
	operator fun div(v: Number) = Vec3(x / v.toFloat(), y / v.toFloat(), z / v.toFloat())
	operator fun rem(v: Vec3) = Vec3(x % v.x, y % v.y, z % v.z)
	operator fun rem(v: Number) = Vec3(x % v.toFloat(), y % v.toFloat(), z % v.toFloat())
	operator fun not() = this * -1

	companion object {
		fun min(a: Vec3, b: Vec3): Vec3 {
			return Vec3(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z))
		}

		fun max(a: Vec3, b: Vec3): Vec3 {
			return Vec3(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z))
		}
	}
}