package fr.opencraft.client.render.renderer.world

import fr.opencraft.core.math.Vec3
import java.nio.FloatBuffer

object BlockData {
	fun addBlockData(
		buffer: FloatBuffer,
		pos: Vec3,
		color: Vec3,

		down: Boolean,
		up: Boolean,
		left: Boolean,
		right: Boolean,
		back: Boolean,
		front: Boolean
	): Int {
		var size = 0

		if (!down) {
			buffer.put(getDownData(pos, color))
			++size
		}
		if (!up) {
			buffer.put(getUpData(pos, color))
			++size
		}
		if (!left) {
			buffer.put(getLeftData(pos, color * 0.9f))
			++size
		}
		if (!right) {
			buffer.put(getRightData(pos, color * 0.9f))
			++size
		}
		if (!back) {
			buffer.put(getBackData(pos, color * 0.8f))
			++size
		}
		if (!front) {
			buffer.put(getFrontData(pos, color * 0.8f))
			++size
		}

		return size
	}

	private fun getDownData(p: Vec3, c: Vec3) : FloatArray {
		return floatArrayOf(
			p.x, p.y, p.z,			c.x, c.y, c.z,
			p.x, p.y, p.z+1,		c.x, c.y, c.z,
			p.x+1, p.y, p.z+1,		c.x, c.y, c.z,
			p.x+1, p.y, p.z,		c.x, c.y, c.z
		)
	}

	private fun getUpData(p: Vec3, c: Vec3) : FloatArray {
		return floatArrayOf(
			p.x, p.y+1, p.z,		c.x, c.y, c.z,
			p.x+1, p.y+1, p.z,		c.x, c.y, c.z,
			p.x+1, p.y+1, p.z+1,	c.x, c.y, c.z,
			p.x, p.y+1, p.z+1,		c.x, c.y, c.z
		)
	}

	private fun getLeftData(p: Vec3, c: Vec3) : FloatArray {
		return floatArrayOf(
			p.x, p.y, p.z,			c.x, c.y, c.z,
			p.x, p.y+1, p.z,		c.x, c.y, c.z,
			p.x, p.y+1, p.z+1,		c.x, c.y, c.z,
			p.x, p.y, p.z+1,		c.x, c.y, c.z
		)
	}

	private fun getRightData(p: Vec3, c: Vec3) : FloatArray {
		return floatArrayOf(
			p.x+1, p.y, p.z,		c.x, c.y, c.z,
			p.x+1, p.y, p.z+1,		c.x, c.y, c.z,
			p.x+1, p.y+1, p.z+1,	c.x, c.y, c.z,
			p.x+1, p.y+1, p.z,		c.x, c.y, c.z
		)
	}

	private fun getBackData(p: Vec3, c: Vec3) : FloatArray {
		return floatArrayOf(
			p.x, p.y, p.z,			c.x, c.y, c.z,
			p.x+1, p.y, p.z,		c.x, c.y, c.z,
			p.x+1, p.y+1, p.z,		c.x, c.y, c.z,
			p.x, p.y+1, p.z,		c.x, c.y, c.z
		)
	}

	private fun getFrontData(p: Vec3, c: Vec3) : FloatArray {
		return floatArrayOf(
			p.x, p.y, p.z+1,		c.x, c.y, c.z,
			p.x, p.y+1, p.z+1,		c.x, c.y, c.z,
			p.x+1, p.y+1, p.z+1,	c.x, c.y, c.z,
			p.x+1, p.y, p.z+1,		c.x, c.y, c.z
		)
	}
}