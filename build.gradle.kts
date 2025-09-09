plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "se.brankoov"
version = "0.0.1-SNAPSHOT"
description = "A RESTful web service for managing todos using Spring Boot and MongoDB"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-data-mongodb") // <— byter till icke-reaktiv
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-actuator")

		developmentOnly("org.springframework.boot:spring-boot-devtools")


		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		testImplementation("org.springframework.boot:spring-boot-starter-test")

		// Tar bort reaktivt test för tillfället
		// testImplementation("io.projectreactor:reactor-test")

		// Låt bli Security tills vidare (annars skyddas allt med Basic Auth)
		implementation("org.springframework.boot:spring-boot-starter-security")
		testImplementation("org.springframework.security:spring-security-test")

		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
