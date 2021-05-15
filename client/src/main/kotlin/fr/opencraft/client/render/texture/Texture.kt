package fr.opencraft.client.render.texture

import fr.opencraft.client.render.Buffers
import fr.opencraft.core.util.FileUtil
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


open class Texture(name: String) {
	private val texture = Buffers.createTexture()

	init {
		val image: BufferedImage = ImageIO.read(FileUtil.resource("textures/$name.png"))
		val width = image.width
		val height = image.height
		val pixels = IntArray(width * height)
		image.getRGB(0, 0, width, height, pixels, 0, width)

		val data = IntArray(pixels.size)
		for (i in pixels.indices) {
			val a = pixels[i] and 0xff00000 shr 24
			val r = pixels[i] and 0xff0000 shr 16
			val g = pixels[i] and 0xff00 shr 8
			val b = pixels[i] and 0xff
			data[i] = (a shl 24) or (b shl 16) or (g shl 8) or r
		}

		glBindTexture(GL_TEXTURE_2D, texture)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

		val buffer = MemoryUtil.memAllocInt(data.size)
		buffer.put(data)
		buffer.flip()

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);


		MemoryUtil.memFree(buffer)
	}

	fun dispose() {
		Buffers.deleteTexture(texture)
	}

	fun bind() {
		glBindTexture(GL_TEXTURE_2D, texture)
	}

	fun unbind() {
		glBindTexture(GL_TEXTURE_2D, 0)
	}
}