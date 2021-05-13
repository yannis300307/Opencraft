import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.5.0"
}

group = "fr.tipragot"
version = "1.0"

val lwjglVersion = "3.2.3"
val lwjglNatives = "natives-windows"

dependencies {
	implementation(project(":core"))
	implementation(kotlin("stdlib-jdk8"))

	implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
	implementation("org.lwjgl", "lwjgl")
	implementation("org.lwjgl", "lwjgl-glfw")
	implementation("org.lwjgl", "lwjgl-openal")
	implementation("org.lwjgl", "lwjgl-opengl")
	implementation("org.lwjgl", "lwjgl", classifier = lwjglNatives)
	implementation("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
	implementation("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
	implementation("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
}

repositories {
	mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}

tasks.jar {
	manifest {
		attributes["Main-Class"] = "fr.opencraft.client.MainKt"
	}
	configurations["compileClasspath"].forEach { file: File ->
		from(zipTree(file.absoluteFile))
	}
}