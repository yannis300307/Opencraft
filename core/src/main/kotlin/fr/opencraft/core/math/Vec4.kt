package fr.opencraft.core.math

import kotlin.math.*

data class Vec4(var x: Float, var y: Float, var z: Float, var w: Float) {
	val length get() = sqrt(x*x + y*y + z*z + w*w)
	val normalize get() = this / length

	constructor(x: Number, y: Number, z: Number, w: Number) : this(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())
	constructor(vec: Vec4) : this(vec.x, vec.y, vec.z, vec.w)
	constructor(value: Number) : this(value, value, value, value)
	constructor() : this(0f)

	operator fun compareTo(vec: Vec4) = length.compareTo(vec.length)
	operator fun plus(v: Vec4) = Vec4(x + v.x, y + v.y, z + v.z, w + v.w)
	operator fun plus(v: Number) = Vec4(x + v.toFloat(), y + v.toFloat(), z + v.toFloat(), w + v.toFloat())
	operator fun minus(v: Vec4) = Vec4(x - v.x, y - v.y, z - v.z, w - v.w)
	operator fun minus(v: Number) = Vec4(x - v.toFloat(), y - v.toFloat(), z - v.toFloat(), w - v.toFloat())
	operator fun times(v: Vec4) = Vec4(x * v.x, y * v.y, z * v.z, w * v.w)
	operator fun times(v: Number) = Vec4(x * v.toFloat(), y * v.toFloat(), z * v.toFloat(), w * v.toFloat())
	operator fun div(v: Vec4) = Vec4(x / v.x, y / v.y, z / v.z, w / v.w)
	operator fun div(v: Number) = Vec4(x / v.toFloat(), y / v.toFloat(), z / v.toFloat(), w / v.toFloat())
	operator fun rem(v: Vec4) = Vec4(x % v.x, y % v.y, z % v.z, w % v.w)
	operator fun rem(v: Number) = Vec4(x % v.toFloat(), y % v.toFloat(), z % v.toFloat(), w % v.toFloat())
	operator fun not() = this * -1

	companion object {
		fun min(a: Vec4, b: Vec4): Vec4 {
			return Vec4(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z), min(a.w, b.w))
		}

		fun max(a: Vec4, b: Vec4): Vec4 {
			return Vec4(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z), max(a.w, b.w))
		}
	}
}