import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.overl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = uri("https://github.com/psiegman/mvn-repo/raw/master/releases"))
}

dependencies {
    implementation("nl.siegmann.epublib:epublib-core:3.1")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}