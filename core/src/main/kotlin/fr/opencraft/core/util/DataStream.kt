package fr.opencraft.core.util

import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import javax.xml.crypto.Data

class DataInput(val stream: InputStream) {
	fun read(): Byte {
		return stream.read().toByte()
	}

	fun readInt(): Int {
		return ByteBuffer.wrap(byteArrayOf(
			read(),
			read(),
			read(),
			read()
		)).int
	}

	fun readFloat(): Float {
		return Float.fromBits(readInt())
	}

	fun readArray(): ByteArray {
		val array = ByteArray(readInt())
		for (i in array.indices) {
			array[i] = read()
		}
		return array
	}

	fun readString(): String {
		return String(readArray(), Charsets.UTF_8)
	}
}

class DataOutput(val stream: OutputStream) {
	fun write(value: Byte): DataOutput {
		stream.write(value.toInt())
		return this
	}

	fun writeInt(value: Int): DataOutput {
		write((value ushr 24).toByte())
		write((value ushr 16).toByte())
		write((value ushr 8).toByte())
		write(value.toByte())
		return this
	}

	fun writeFloat(value: Float): DataOutput {
		return writeInt(value.toBits())
	}

	fun writeArray(value: ByteArray): DataOutput {
		writeInt(value.size)
		stream.write(value)
		return this
	}

	fun writeString(value: String): DataOutput {
		return writeArray(value.toByteArray(Charsets.UTF_8))
	}
}