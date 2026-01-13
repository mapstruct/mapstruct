plugins {
    application
    kotlin("jvm") version "2.2.21"
    id("com.google.devtools.ksp") version "2.3.3"
}

group = "org.mapstruct.ksp.sample"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()  // For MapStruct core dependency (still needed for core module)
}

application {
    mainClass.set("org.example.MainKt")
}

dependencies {
    // MapStruct core annotations
    implementation("org.mapstruct:mapstruct:1.7.0-SNAPSHOT")

    // Use the compiled ksp-processor from the Maven build
    // This allows debugging without publishing to mavenLocal
    // Point directly to both ksp-processor and processor (base) classes
    ksp(files("../target/classes"))

    // Also need the base processor classes that ksp-processor depends on
    ksp("org.mapstruct:mapstruct-processor:1.7.0-SNAPSHOT")

    // Kotlin stdlib
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}
