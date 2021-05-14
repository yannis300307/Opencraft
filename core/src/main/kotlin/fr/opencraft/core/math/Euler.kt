package fr.opencraft.core.math

import java.lang.Math.toRadians
import kotlin.math.*

object Euler {
	fun getForward(rotation: Vec3): Vec3 {
		val pitch = toRadians(rotation.x.toDouble()).toFloat()
		val yaw = toRadians(rotation.y - 90.0).toFloat()
		return Vec3(
			-cos(pitch) * cos(yaw),
			sin(pitch),
			-cos(pitch) * sin(yaw)
		) * -1f
	}

	fun getForwardIgnoreY(rotation: Vec3): Vec3 {
		val angle = toRadians(rotation.y - 90.0).toFloat()
		return Vec3(cos(angle), 0f, sin(angle))
	}

	fun getRightIgnoreY(rotation: Vec3): Vec3 {
		val angle = toRadians(rotation.y.toDouble()).toFloat()
		return Vec3(cos(angle), 0f, sin(angle))
	}
}
