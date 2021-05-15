package fr.opencraft.core.world

import fr.opencraft.core.block.Block
import fr.opencraft.core.block.BlockFace
import fr.opencraft.core.entity.Entity
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.WorldSettings.CHUNK_SIZE
import fr.opencraft.core.world.WorldSettings.FRICTION
import fr.opencraft.core.world.WorldSettings.GRAVITY
import kotlin.math.max
import kotlin.math.min

class BlockState(internal var block: Block, val chunk: Chunk, val position: LocalPosition) {
	var type: Block
		get() = block
		set(value) = run {
			block = value
			chunk.world.updateChunk(chunk.position)

			// Update Surroundings
			if (position.x == 0) chunk.world.updateChunk(ChunkPosition(position.x-1, position.y, position.z))
			if (position.x == CHUNK_SIZE-1) chunk.world.updateChunk(ChunkPosition(position.x+1, position.y, position.z))
			if (position.y == 0) chunk.world.updateChunk(ChunkPosition(position.x, position.y-1, position.z))
			if (position.y == CHUNK_SIZE-1) chunk.world.updateChunk(ChunkPosition(position.x, position.y+1, position.z))
			if (position.z == 0) chunk.world.updateChunk(ChunkPosition(position.x, position.y, position.z-1))
			if (position.z == CHUNK_SIZE-1) chunk.world.updateChunk(ChunkPosition(position.x, position.y, position.z+1))
		}

	fun isFullFace(face: BlockFace) = type.isFullFace(this, face)
}

class EntityState(
	val type: Entity,
	val world: World,
	val identifier: Int,
	var position: Vec3 = Vec3(),
	var rotation: Vec3 = Vec3(),
	var velocity: Vec3 = Vec3()
) {
	val bounds get() = type.getBounds(this)
	val head get() = type.getHead(this)
	var grounded = false; private set

	fun teleport(position: Vec3) {
		this.position = position.copy()
	}

	fun move(movement: Vec3) {
		position += Vec3(movement.x, 0f, 0f)
		var collision = bounds.getCollisionData(world)
		if (collision != null) {
			position.x += collision.delta.x
			if (collision.delta.y > 0.0001f) velocity.x = 0f
		}

		grounded = false
		position += Vec3(0f, movement.y, 0f)
		collision = bounds.getCollisionData(world)
		if (collision != null) {
			position.y += collision.delta.y
			if (collision.delta.y > 0.0001f) {
				velocity.y = 0f
				grounded = true
			}
		}

		position += Vec3(0f, 0f, movement.z)
		collision = bounds.getCollisionData(world)
		if (collision != null) {
			position.z += collision.delta.z
			if (collision.delta.y > 0.00001f) velocity.z = 0f
		}
	}

	fun rotate(rotation: Vec3) {
		this.rotation += rotation
		this.rotation.x = max(-90f, min(90f, this.rotation.x))
	}

	fun applyForce(force: Vec3, delta: Float) {
		velocity += force * delta
	}

	fun applyImpulse(impulse: Vec3) {
		velocity += impulse
	}

	fun update(delta: Float) {
		// Gravity
		velocity -= Vec3(0f, GRAVITY, 0f) * delta

		// Friction
		velocity *= Vec3(1f) / ((FRICTION * delta) + 1f)

		// Update Position
		move(velocity * delta)
	}
}