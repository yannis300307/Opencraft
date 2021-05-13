package fr.opencraft.core.math

import kotlin.math.*

data class Vec2(var x: Float, var y: Float) {
	val length get() = sqrt(x*x + y*y)
	val normalize get() = this / length

	constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())
	constructor(vec: Vec2) : this(vec.x, vec.y)
	constructor(value: Number) : this(value, value)
	constructor() : this(0f)

	operator fun compareTo(vec: Vec2) = length.compareTo(vec.length)
	operator fun plus(v: Vec2) = Vec2(x + v.x, y + v.y)
	operator fun plus(v: Number) = Vec2(x + v.toFloat(), y + v.toFloat())
	operator fun minus(v: Vec2) = Vec2(x - v.x, y - v.y)
	operator fun minus(v: Number) = Vec2(x - v.toFloat(), y - v.toFloat())
	operator fun times(v: Vec2) = Vec2(x * v.x, y * v.y)
	operator fun times(v: Number) = Vec2(x * v.toFloat(), y * v.toFloat())
	operator fun div(v: Vec2) = Vec2(x / v.x, y / v.y)
	operator fun div(v: Number) = Vec2(x / v.toFloat(), y / v.toFloat())
	operator fun rem(v: Vec2) = Vec2(x % v.x, y % v.y)
	operator fun rem(v: Number) = Vec2(x % v.toFloat(), y % v.toFloat())
	operator fun not() = this * -1

	companion object {
		fun min(a: Vec2, b: Vec2): Vec2 {
			return Vec2(min(a.x, b.x), min(a.y, b.y))
		}

		fun max(a: Vec2, b: Vec2): Vec2 {
			return Vec2(max(a.x, b.x), max(a.y, b.y))
		}
	}
}