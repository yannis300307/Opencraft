package fr.opencraft.client.render.renderer.block

import fr.opencraft.client.render.texture.SheetTile
import fr.opencraft.core.block.Block
import fr.opencraft.core.block.BlockFace
import fr.opencraft.core.math.Vec3
import fr.opencraft.core.world.BlockPosition
import fr.opencraft.core.world.BlockState
import java.nio.FloatBuffer

class FullBlockRenderer(type: Block, private val tile: SheetTile) : BlockRenderer(type) {
	override fun render(state: BlockState, buffer: FloatBuffer): Int {
		val pos = state.position.toWorld(state.chunk.position)
		var size = 0

		val downBlock = state.chunk.world.getBlock(BlockPosition(pos.x, pos.y-1, pos.z))
		if (downBlock == null || !downBlock.isFullFace(BlockFace.UP)) {
			buffer.put(getDownData(pos, tile))
			size += 4
		}

		val upBlock = state.chunk.world.getBlock(BlockPosition(pos.x, pos.y+1, pos.z))
		if (upBlock == null || !upBlock.isFullFace(BlockFace.DOWN)) {
			buffer.put(getUpData(pos, tile))
			size += 4
		}

		val leftBlock = state.chunk.world.getBlock(BlockPosition(pos.x-1, pos.y, pos.z))
		if (leftBlock == null || !leftBlock.isFullFace(BlockFace.EAST)) {
			buffer.put(getLeftData(pos, tile))
			size += 4
		}

		val rightBlock = state.chunk.world.getBlock(BlockPosition(pos.x+1, pos.y, pos.z))
		if (rightBlock == null || !rightBlock.isFullFace(BlockFace.WEST)) {
			buffer.put(getRightData(pos, tile))
			size += 4
		}

		val backBlock = state.chunk.world.getBlock(BlockPosition(pos.x, pos.y, pos.z-1))
		if (backBlock == null || !backBlock.isFullFace(BlockFace.NORTH)) {
			buffer.put(getBackData(pos, tile))
			size += 4
		}

		val frontBlock = state.chunk.world.getBlock(BlockPosition(pos.x, pos.y, pos.z+1))
		if (frontBlock == null || !frontBlock.isFullFace(BlockFace.SOUTH)) {
			buffer.put(getFrontData(pos, tile))
			size += 4
		}

		return size
	}

	companion object {
		fun getDownData(p: Vec3, t: SheetTile) : FloatArray {
			return floatArrayOf(
				p.x, p.y, p.z,			t.min.x, t.min.y,
				p.x, p.y, p.z+1,		t.min.x, t.max.y,
				p.x+1, p.y, p.z+1,		t.max.x, t.max.y,
				p.x+1, p.y, p.z,		t.max.x, t.min.y
			)
		}

		fun getUpData(p: Vec3, t: SheetTile) : FloatArray {
			return floatArrayOf(
				p.x, p.y+1, p.z,		t.min.x, t.min.y,
				p.x+1, p.y+1, p.z,		t.max.x, t.min.y,
				p.x+1, p.y+1, p.z+1,	t.max.x, t.max.y,
				p.x, p.y+1, p.z+1,		t.min.x, t.max.y
			)
		}

		fun getLeftData(p: Vec3, t: SheetTile) : FloatArray {
			return floatArrayOf(
				p.x, p.y, p.z,			t.min.x, t.min.y,
				p.x, p.y+1, p.z,		t.max.x, t.min.y,
				p.x, p.y+1, p.z+1,		t.max.x, t.max.y,
				p.x, p.y, p.z+1,		t.min.x, t.max.y
			)
		}

		fun getRightData(p: Vec3, t: SheetTile) : FloatArray {
			return floatArrayOf(
				p.x+1, p.y, p.z,		t.min.x, t.min.y,
				p.x+1, p.y, p.z+1,		t.min.x, t.max.y,
				p.x+1, p.y+1, p.z+1,	t.max.x, t.max.y,
				p.x+1, p.y+1, p.z,		t.max.x, t.min.y
			)
		}

		fun getBackData(p: Vec3, t: SheetTile) : FloatArray {
			return floatArrayOf(
				p.x, p.y, p.z,			t.min.x, t.min.y,
				p.x+1, p.y, p.z,		t.max.x, t.min.y,
				p.x+1, p.y+1, p.z,		t.max.x, t.max.y,
				p.x, p.y+1, p.z,		t.min.x, t.max.y
			)
		}

		fun getFrontData(p: Vec3, t: SheetTile) : FloatArray {
			return floatArrayOf(
				p.x, p.y, p.z+1,		t.min.x, t.min.y,
				p.x, p.y+1, p.z+1,		t.min.x, t.max.y,
				p.x+1, p.y+1, p.z+1,	t.max.x, t.max.y,
				p.x+1, p.y, p.z+1,		t.max.x, t.min.y
			)
		}
	}
}
