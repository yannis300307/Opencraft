package fr.opencraft.core.util

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.lang.Exception

object FileUtil {
	fun read(stream: InputStream): String? {
		val reader = BufferedReader(stream.reader())
		return try {
			val content = reader.readText()
			reader.close()
			content
		} catch (e: Exception) {
			null
		}
	}

	fun read(file: File) : String? {
		return try {
			read(FileInputStream(file))
		} catch (e: Exception) {
			null
		}
	}

	fun read(path: String) : String? {
		return try {
			javaClass.getResource("/$path")?.readText()
		} catch (e: Exception) {
			null
		}
	}
}