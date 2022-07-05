import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.7.0"

plugins {
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.tlannigan"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("dev.jorel:commandapi-shade:8.4.1")

    testImplementation(kotlin("test"))

    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    compileOnly("dev.jorel:commandapi-core:8.4.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<ShadowJar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
    relocate("dev.jorel.commandapi", "com.tlannigan.dimensionwarp.commandapi")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("8"))
    }
}