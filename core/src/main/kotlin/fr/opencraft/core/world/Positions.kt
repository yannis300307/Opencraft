package fr.opencraft.core.world

import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.WorldSettings.CHUNK_SIZE
import kotlin.math.*

interface Position

data class BlockPosition(val x: Int, val y: Int, val z: Int) : Position {
	constructor(x: Number, y: Number, z: Number) : this(x.toBlock(), y.toBlock(), z.toBlock())
	constructor(vec: Vec3) : this(vec.x, vec.y, vec.z)

	fun toWorld() = Vec3(x, y, z)
	fun toChunk() = toWorld().toChunk()
	fun toLocal() = toWorld().toLocal()
}

data class ChunkPosition(val x: Int, val y: Int, val z: Int) {
	constructor(x: Number, y: Number, z: Number) : this(x.toChunk(), y.toChunk(), z.toChunk())
	constructor(vec: Vec3) : this(vec.x, vec.y, vec.z)

	fun toWorld() = Vec3(x * CHUNK_SIZE, y * CHUNK_SIZE, z * CHUNK_SIZE)
	fun toLocal() = toWorld().toLocal()
}

data class LocalPosition(val x: Int, val y: Int, val z: Int) : Position {
	constructor(x: Number, y: Number, z: Number) : this(x.toLocal(), y.toLocal(), z.toLocal())
	constructor(vec: Vec3) : this(vec.x, vec.y, vec.z)

	fun toWorld(chunk: ChunkPosition) = chunk.toWorld() + Vec3(x, y, z)
}

fun Number.toBlock() = floor(toFloat()).toInt()
fun Vec3.toBlock() = BlockPosition(this)
fun Number.toChunk() = (toFloat() / CHUNK_SIZE).toBlock()
fun Vec3.toChunk() = ChunkPosition(this)
fun Number.toLocal() = (toBlock() - (toChunk() * CHUNK_SIZE))
fun Vec3.toLocal() = LocalPosition(this)