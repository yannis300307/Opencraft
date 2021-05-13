import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.5.0"
}

group = "fr.tipragot"
version = "1.0"

dependencies {
	implementation(kotlin("stdlib-jdk8"))
}

repositories {
	mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}