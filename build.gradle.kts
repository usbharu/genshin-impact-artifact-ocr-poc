plugins {
    kotlin("jvm") version "1.9.23"
}

group = "dev.usbharu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("net.sourceforge.tess4j:tess4j:5.11.0")
    implementation("com.twelvemonkeys.imageio:imageio-webp:3.10.1")
    implementation("org.bytedeco:javacv-platform:1.5.10")
    implementation("org.apache.lucene:lucene-spellchecker:3.3.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}