plugins {
	java
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.platzi"
version = "0.0.1-SNAPSHOT"
description = "Proyecto para crear una API con spring-boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    runtimeOnly("org.postgresql:postgresql:42.7.8")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.1")

    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
