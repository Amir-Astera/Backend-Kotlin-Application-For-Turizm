import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.github.johnrengelman.processes") version "0.5.0"

	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
	kotlin("plugin.jpa") version "1.5.21"
}

group = "dev.december.jeter-backend"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	maven {
		url = uri("https://jitpack.io")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.3")
	implementation("com.google.firebase:firebase-admin:9.1.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation(project(":shared"))

	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.bootJar {
	archiveFileName.set("${project.properties["name"]}.jar")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}