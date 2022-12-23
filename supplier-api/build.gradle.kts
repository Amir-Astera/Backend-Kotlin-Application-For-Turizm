import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.1.0"
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
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.3")
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.5.10")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.5.10")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.github.ProjectMapK:KMapper:0.36")
	implementation("com.github.ProjectMapK:Shared:0.20")
	implementation("com.google.code.gson:gson:2.8.7")
	implementation("org.apache.poi:poi:4.1.2")
	implementation("org.apache.poi:poi-ooxml:4.1.2")
	implementation("com.google.firebase:firebase-admin:9.1.1")
	implementation("org.apache.tika:tika-core:2.0.0")
	implementation(project(":shared"))
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	runtimeOnly("org.postgresql:postgresql")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
